package ec.com.platform.seguridad.core.gestor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ec.com.platform.app.model.EmpresaPersona_;
import ec.com.platform.app.model.Persona_;
import ec.com.platform.app.web.jsf.mb.SesionUsuarioMB;
import ec.com.platform.fwk.crud.GenericCriteria;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.generic.core.gestor.GenericGImpl;
import ec.com.platform.generic.model.Auditoria;
import ec.com.platform.generic.model.Auditoria_;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.seguridad.exception.SeguridadException;
import ec.com.platform.seguridad.model.Objeto;
import ec.com.platform.seguridad.model.Objeto_;
import ec.com.platform.seguridad.model.Opcion;
import ec.com.platform.seguridad.model.Opcion_;
import ec.com.platform.seguridad.model.Rol;
import ec.com.platform.seguridad.model.RolOpcion;
import ec.com.platform.seguridad.model.RolOpcion_;
import ec.com.platform.seguridad.model.Rol_;
import ec.com.platform.seguridad.model.Usuario;
import ec.com.platform.seguridad.model.UsuarioRol;
import ec.com.platform.seguridad.model.UsuarioRol_;
import ec.com.platform.seguridad.model.Usuario_;
import ec.com.platform.util.Constantes;
import ec.com.platform.util.GenericUtils;
import ec.com.platform.util.StringUtils;
import ec.com.platform.util.core.gestor.UtilG;
import ec.com.platform.util.crypto.CryptoUtils;

/**
 * @author Eduardo Plua Alay
 */
@Repository("seguridadG")
public class SeguridadGImpl extends GenericGImpl<Object, SeguridadException> implements SeguridadG {

	private static final long serialVersionUID = -7535155949566180920L;
	
	@Override
    protected String getBundleName(){
    	return Constantes.DOMAIN_NAME+".seguridad.resources.seguridad";
    }
	
	/**
	 * Dependencias
	 */
    
	@Getter @Setter
    @Autowired
    @Qualifier("utilG")
    private UtilG utilG;
    
    //
    //Usuario
    //
    @Override
    public Usuario obtenerUsuarioNuevo() {
        Usuario usuario = new Usuario();
        usuario.setEstado(Usuario.Estado.INACTIVO);
        return usuario;
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return findById(id, Usuario.class);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {

        if (isUsuarioValido(usuario)) {
            Date date = new Date();
            Generic.Estado estadoActual = usuario.getEstado();
            if (GenericUtils.isPersistent(usuario)) {
                usuario.setFechaModificacion(date);
            } else {
                usuario.setPassword(CryptoUtils.computeHashSHA256(usuario.getPassword()));
                usuario.setFechaCreacion(date);
                usuario.setFechaModificacion(date);
                usuario.setEstado(Generic.Estado.ACTIVO);
            }

            usuario = saveOrUpdate(usuario);

            wrapSuccessMessage("Usuario " + usuario.getId() + " guardado correctamente");

            //Envio de correo de creacion nuevo usuario
            if (estadoActual.isInactivo()) {
                buildSendMailCuentaUsuario(usuario);
            }
        }

        return usuario;
    }

    @Override
    public void eliminarUsuario(Usuario usuario) { // TODO (epa): Usar definitivamente resource bundle para mensajes
        Long id = usuario.getId();
        delete(usuario);
        wrapSuccessMessage("Usuario " + id + " eliminado correctamente");
    }

    @Override
    public List<Usuario> obtenerUsuarioList(Usuario usuario, Paginacion paginacion) {        
        return findByCriteria(buildUsuarioCriteria(usuario), paginacion);
    }
    
    private GenericCriteria<Usuario> buildUsuarioCriteria(Usuario usuario){
        GenericCriteria<Usuario> criteria = GenericCriteria.forClass(Usuario.class);
        criteria.addAliasedJoins(Usuario_.empresaPersona, Usuario_.empresaPersona+"."+EmpresaPersona_.persona);
        criteria.addLike(Usuario_.username, usuario.getUsername());
        criteria.addLike(EmpresaPersona_.persona+"."+Persona_.nombres, usuario.getEmpresaPersona().getPersona().getNombres());
        criteria.addLike(EmpresaPersona_.persona+"."+Persona_.apellidos, usuario.getEmpresaPersona().getPersona().getApellidos());
        criteria.addEquals(Usuario_.estado, Generic.Estado.ACTIVO);
        return criteria;
    }

    @Override
    public void guardarUsuarioRolList(List<UsuarioRol> usuarioRolList) {
        if (usuarioRolList != null) {
            Date fecha = new Date();
            for (UsuarioRol usuarioRol : usuarioRolList) {
                usuarioRol.setFechaModificacion(fecha);
                update(usuarioRol);
            }
        }
    }

    @Override
    public void generarUsuarioRol(Usuario usuario, Rol rol) {
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setEstado(Generic.Estado.ACTIVO);
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        Date fecha = new Date();
        usuarioRol.setFechaCreacion(fecha);
        usuarioRol.setFechaModificacion(fecha);
        
        saveOrUpdate(usuarioRol);
    }

    @Override
    public List<UsuarioRol> obtenerUsuarioRolList(Usuario usuario) {
        GenericCriteria<UsuarioRol> query = GenericCriteria.forClass(UsuarioRol.class);
        query.addEquals(UsuarioRol_.estado, Generic.Estado.ACTIVO);
        query.addEquals(UsuarioRol_.usuario, usuario);

        return findByCriteria(query);
    }

    @Override
    public void eliminarUsuarioRolList(List<UsuarioRol> usuarioRolList) {
    	deleteAll(usuarioRolList);
    	wrapSuccessMessage("Rol(es) removido(s) correctamente");
    }

    private void buildSendMailCuentaUsuario(Usuario usuario) {      
        String sender = null;
        String subject = "Platform: Creacion Cuenta  " + usuario.getUsername();
        String content = "Se creo correctamente cuenta para Usuario: " + usuario.getUsername()
                + " con Contraseña: " + usuario.getPassword() + " el " + usuario.getFechaCreacion();
        String to = usuario.getEmpresaPersona().getPersona().getMail();
        utilG.sendMail(subject, content, sender, to, null, null);            
    }

    private boolean isUsuarioValido(Usuario usuario) {
        String username = usuario.getUsername();
        
        GenericCriteria<Usuario> query = GenericCriteria.forClass(Usuario.class);                        
        query.addNotEqualsIfNotNull(Usuario_.id, usuario.getId());
        query.addEquals(Usuario_.username, username);        

        if (findFirstByCriteria(query) != null) {
            wrapErrorMessage("Usuario: '"+username+"' ya existe");
            return false;
        }
        return true;
    }
    
    @Override
    public int obtenerUsuarioCount(){                       
        return countByCriteria(buildUsuarioCriteria(new Usuario()));
    }

    //
    // Rol
    //
    @Override
    public List<Rol> obtenerRolList(Rol rol, Paginacion paginacion) {
        GenericCriteria<Rol> criteria = GenericCriteria.forClass(Rol.class);
        criteria.addEquals(Rol_.estado, Generic.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.usuarioCreacion);
        criteria.addAliasedLeftJoins(Auditoria_.usuarioModificacion);
        if(rol != null){
        	criteria.addLike(Rol_.nombre, rol.getNombre());
            criteria.addLike(Rol_.descripcion, rol.getDescripcion());
        }
        if(paginacion != null){
        	return findByCriteria(criteria, paginacion);
        }
        return findByCriteria(criteria);
    }

    @Override
    public Rol obtenerRolNuevo() {
        Rol rol = new Rol();
        rol.setEstado(Usuario.Estado.INACTIVO);

        return rol;
    }

    @Override
    public Rol guardarRol(Rol rol) {
        Date date = new Date();
        if (GenericUtils.isPersistent(rol)) {
            rol.setFechaModificacion(date);
        } else {
            rol.setFechaCreacion(date);
            rol.setFechaModificacion(date);
            rol.setEstado(Generic.Estado.ACTIVO);
        }
        rol = saveOrUpdate(rol);
        wrapSuccessMessage("Rol " + rol.getId() + " guardado correctamente");
        return rol;
    }

    @Override
    public void eliminarRol(Rol rol) {
        Long id = rol.getId();
    	delete(rol);
        wrapSuccessMessage("Rol " + id + " eliminado correctamente");        
    }

    @Override
    public List<RolOpcion> obtenerRolOpcionList(Rol rol) {
        GenericCriteria<RolOpcion> query = GenericCriteria.forClass(RolOpcion.class);

        query.addEquals(RolOpcion_.estado, Generic.Estado.ACTIVO);
        query.addEquals(RolOpcion_.rol, rol);

        return findByCriteria(query);
    }

    @Override
    public void generarRolOpcion(Rol rol, Opcion opcion) {
        RolOpcion rolOpcion = new RolOpcion();
        
        rolOpcion.setAuditoria(new Auditoria());
        rolOpcion.setUsuarioCreacion(getSesionUsuario().getUsuario());
        rolOpcion.setFechaCreacion(new Date());
        
        rolOpcion.setRol(rol);
        rolOpcion.setOpcion(opcion);
        rolOpcion.setEstado(Generic.Estado.ACTIVO);
        rolOpcion.setCrear(false);
        rolOpcion.setEditar(false);
        rolOpcion.setEliminar(false);
        rolOpcion.setExportar(false);        
        
        saveOrUpdate(rolOpcion);
    }
  
    @Override
    public void guardarRolOpcionList(List<RolOpcion> rolOpcionList) { 
        if (rolOpcionList != null) {
        	Usuario usuario = getSesionUsuario().getUsuario();
            Date fecha = new Date();
            for (RolOpcion rolOpcion : rolOpcionList) {
            	rolOpcion.setUsuarioModificacion(usuario);
                rolOpcion.setFechaModificacion(fecha);
                update(rolOpcion);
            }
        }
    }

    @Override
    public RolOpcion guardarRolOpcion(RolOpcion rolOpcion) {
        return update(rolOpcion);
    }

    @Override
    public void eliminarRolOpcionList(List<RolOpcion> rolOpcionList) {
        deleteAll(rolOpcionList);
        wrapSuccessMessage("Opcion(es) removido(s) correctamente");
    }

    //TODO (epa): Optimizar consulta
    private List<RolOpcion> obtenerRolOpcionList(Usuario usuario) {

        GenericCriteria<UsuarioRol> criteria = GenericCriteria.forClass(UsuarioRol.class);
        criteria.addEquals(UsuarioRol_.usuario, usuario);
        criteria.addEquals(UsuarioRol_.estado, Generic.Estado.ACTIVO);
        List<UsuarioRol> usuarioRolList = findByCriteria(criteria);

        List<RolOpcion> rolOpcionList = new ArrayList<RolOpcion>();
        
        if (usuarioRolList != null) {
            for (UsuarioRol usuarioRol : usuarioRolList) {
                GenericCriteria<RolOpcion> rolOpcCriteria = GenericCriteria.forClass(RolOpcion.class);
                rolOpcCriteria.addEquals(RolOpcion_.estado, Generic.Estado.ACTIVO);
                rolOpcCriteria.addEquals(RolOpcion_.rol, usuarioRol.getRol());
                List<RolOpcion> tmp = findByCriteria(rolOpcCriteria);
                if (tmp != null) {
                	for (RolOpcion rolOpcion : tmp) {
                		Objeto obj = rolOpcion.getOpcion().getObjeto();
						String beanName = obj != null ? obj.getCodigo() : StringUtils.empty;
						rolOpcion.getOpcion().setBeanName(beanName);
					}                	
                    rolOpcionList.addAll(tmp);
                }
            }
        }

        return rolOpcionList;
    }

    //
    // Opcion
    //
    @Override
    public List<Opcion> obtenerOpcionList(Opcion opcion, Paginacion paginacion) {
        GenericCriteria<Opcion> criteria = GenericCriteria.forClass(Opcion.class);
        criteria.addEquals("estado", Generic.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.usuarioCreacion);
        criteria.addAliasedLeftJoins(Auditoria_.usuarioModificacion, Opcion_.padre);
        if (opcion != null) {
	        criteria.addAliasedLeftJoins(Opcion_.padre);
	        criteria.addLike(Opcion_.nombre, opcion.getNombre());
	        criteria.addLike(Opcion_.descripcion, opcion.getDescripcion());
	        criteria.addLike(Opcion_.etiqueta, opcion.getEtiqueta());
	        criteria.addLike(Opcion_.target, opcion.getTarget());
        }
        
        return findByCriteria(criteria, paginacion);
    }

    @Override
    public Opcion obtenerOpcionNuevo() {
        Opcion opcion = new Opcion();
        opcion.setEstado(Usuario.Estado.INACTIVO);
        return opcion;
    }

    @Override
    public Opcion guardarOpcion(Opcion opcion) {
        if (!GenericUtils.isPersistent(opcion)) {
            opcion.setEstado(Generic.Estado.ACTIVO);
        }
        opcion = saveOrUpdate(opcion);
        wrapSuccessMessage("Opcion " + opcion.getId() + " guardado correctamente");
        return opcion;
    }

    @Override
    public void eliminarOpcion(Opcion opcion) {
        Long id = opcion.getId();
        delete(opcion);
        wrapSuccessMessage("Opcion " + id + " eliminada correctamente");
    }

    @Override
    public Opcion obtenerOpcion(Long id) {
    	GenericCriteria<Opcion> criteria = GenericCriteria.forClass(Opcion.class);
    	criteria.addEquals("estado", Generic.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.usuarioCreacion);
        criteria.addAliasedLeftJoins(Auditoria_.usuarioModificacion);
        return findFirstByCriteria(criteria);
    }

    @Override
    public List<Opcion> obtenerOpcionPadreList() {
        GenericCriteria<Opcion> criteria = GenericCriteria.forClass(Opcion.class);
        criteria.addEquals(Opcion_.estado, Generic.Estado.ACTIVO);
        criteria.addIsNull(Opcion_.padre);
        criteria.addOrderAsc(Opcion_.padre);
        return findByCriteria(criteria);
    }
    
    //
    // Objeto
    //
    
    @Override
    public List<Objeto> obtenerObjetoList(Objeto objeto, Paginacion paginacion) {
        GenericCriteria<Objeto> criteria = GenericCriteria.forClass(Objeto.class);
        criteria.addEquals("estado", Generic.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.usuarioCreacion);
        criteria.addAliasedLeftJoins(Auditoria_.usuarioModificacion);
        if (objeto != null) {
	        criteria.addAliasedLeftJoins(Opcion_.padre);
	        criteria.addLike(Objeto_.descripcion, objeto.getDescripcion());
	        criteria.addLike(Objeto_.codigo, objeto.getCodigo());
	        criteria.addLike(Objeto_.estado, objeto.getEstado().getValue());
	        criteria.addLike(Objeto_.tipo, objeto.getTipo().getValue());
        }
        
        return findByCriteria(criteria, paginacion);
    }

    @Override
    public Objeto obtenerObjetoNuevo() {
        Objeto opcion = new Objeto();
        opcion.setEstado(Usuario.Estado.INACTIVO);
        return opcion;
    }

    @Override
    public Objeto guardarObjeto(Objeto objeto) {
        if (!GenericUtils.isPersistent(objeto)) {
            objeto.setEstado(Generic.Estado.ACTIVO);
        }
        objeto = saveOrUpdate(objeto);
        wrapSuccessMessage("Objeto " + objeto.getId() + " guardado correctamente");
        return objeto;
    }

    @Override
    public void eliminarObjeto(Objeto objeto) {
        Long id = objeto.getId();
    	delete(objeto);
        wrapSuccessMessage("Objeto " + id + " eliminado correctamente");
    }

    @Override
    public Objeto obtenerObjeto(Long id) {
        return findById(id, Objeto.class);
    }
    

    //
    // Session Usuario
    //
    
    @Override
    public boolean iniciarSesion(SesionUsuarioMB aThis) {
        GenericCriteria<Usuario> criteria = GenericCriteria.forClass(Usuario.class);
        criteria.addEquals(Usuario_.estado, Generic.Estado.ACTIVO);
        criteria.addEquals(Usuario_.username, aThis.getUsuario().getUsername());
        criteria.addEquals(Usuario_.password, CryptoUtils.computeHashSHA256(aThis.getUsuario().getPassword()));

        Usuario usuario = findFirstByCriteria(criteria);        
        if (usuario != null) {        	
        	aThis.setUsuario(usuario);
        } else {
            wrapErrorMessage("Usuario o contrasena incorrecta");
            return false;
        }
        List<RolOpcion> rolOpcionList = obtenerRolOpcionList(usuario);
        aThis.setRolOpcionList(rolOpcionList);
        return true;
    }
    
    @Override
    public void cambiarPassword(Usuario usuario){
        GenericCriteria<Usuario> criteria = GenericCriteria.forClass(Usuario.class);
        criteria.addEquals(Usuario_.estado, Generic.Estado.ACTIVO);
        criteria.addEquals(Usuario_.username, usuario.getUsername());
        criteria.addEquals(Usuario_.password, CryptoUtils.computeHashSHA256(usuario.getPassword()));
        
        if (findFirstByCriteria(criteria) != null) {
            if(usuario.getNewpassword().equals(usuario.getConfpassword())){
                usuario.setPassword(CryptoUtils.computeHashSHA256(usuario.getNewpassword()));
                update(usuario);
                wrapSuccessMessage("Contrasena actualizada correctamente");
            } else {
                wrapErrorMessage("Contrasena de confirmacion incorrecta"); 
            }
        } else {
           wrapErrorMessage("Contrasena actual incorrecta"); 
        }
                
        usuario.setPassword(null);
        usuario.setNewpassword(null);
        usuario.setConfpassword(null);
    }
    
    @Override
    public SesionUsuarioMB getSesionUsuario() {
    	return super.getSesionUsuario();
    }
            
}
