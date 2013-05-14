package ec.com.ebos.security.core.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ec.com.ebos.master.model.field.EmpresaPersona_;
import ec.com.ebos.master.model.field.Persona_;
import ec.com.ebos.master.web.jsf.bean.SessionBean;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.core.process.RootPImpl;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.model.field.Auditoria_;
import ec.com.ebos.security.exception.SecurityException;
import ec.com.ebos.security.model.Objeto;
import ec.com.ebos.security.model.Opcion;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.security.model.UsuarioRol;
import ec.com.ebos.security.model.field.Objeto_;
import ec.com.ebos.security.model.field.Opcion_;
import ec.com.ebos.security.model.field.RolOpcion_;
import ec.com.ebos.security.model.field.Rol_;
import ec.com.ebos.security.model.field.UsuarioRol_;
import ec.com.ebos.security.model.field.Usuario_;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.CryptoUtils;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.StringUtils;
import ec.com.ebos.util.core.process.UtilP;

/**
 * @author Eduardo Plua Alay
 */
@Repository(SecurityP.BEAN_NAME)
public class SecurityPImpl extends RootPImpl<Object, SecurityException> implements SecurityP {

	private static final long serialVersionUID = -7535155949566180920L;
	
	/**
	 * Dependencias
	 */
    
	@Getter @Setter
    @Autowired
    @Qualifier("utilP")
    private UtilP utilP;
	
	//
	// Put Messages
	//
	
	public void putError(String key, Object... args){
		super.putError(key, args);
	}
    
    //
    //Usuario
    //
    @Override
    public Usuario createUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEstado(Usuario.Estado.INACTIVO);
        return usuario;
    }

    @Override
    public Usuario getUsuario(Long id) {
        return get(id, Usuario.class);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {

        if (isUsuarioValido(usuario)) {
            Date date = new Date();
            Entidad.Estado estadoActual = usuario.getEstado();
            if (GenericUtils.isPersistent(usuario)) {
                usuario.setModificado(date);
            } else {
                usuario.setPassword(CryptoUtils.computeHashSHA256(usuario.getPassword()));
                usuario.setCreado(date);
                usuario.setModificado(date);
                usuario.setEstado(Entidad.Estado.ACTIVO);
            }

            usuario = saveOrMerge(usuario);

            putSuccess("usuario.success.guardar", usuario.getId());

            //Envio de correo de creacion nuevo usuario
            if (estadoActual.isInactivo()) {
                buildSendMailCuentaUsuario(usuario);
            }
        }

        return usuario;
    }

    @Override
    public void deleteUsuario(Usuario usuario) { // TODO (epa): Usar definitivamente resource bundle para mensajes
        Long id = usuario.getId();
        delete(usuario);
        putSuccess("usuario.success.eliminar", id);
    }

    @Override
    public List<Usuario> findUsuarioList(Usuario usuario, Pagination pagination) {        
        return findByCriteria(buildUsuarioCriteria(usuario), pagination);
    }
    
    private GenericCriteria<Usuario> buildUsuarioCriteria(Usuario usuario){
        GenericCriteria<Usuario> criteria = GenericCriteria.forClass(Usuario.class);
        
        criteria.addEqualsIfNotZero(Usuario_.id, usuario.getId());
		if(criteria.isChanged()){
			return criteria;
		}
		
        criteria.addAliasedJoins(Usuario_.empresaPersona, Usuario_.empresaPersona+"."+EmpresaPersona_.persona);
        criteria.addLike(Usuario_.username, usuario.getUsername());
        criteria.addLike(EmpresaPersona_.persona+"."+Persona_.nombres, usuario.getEmpresaPersona().getPersona().getNombres());
        criteria.addLike(EmpresaPersona_.persona+"."+Persona_.apellidos, usuario.getEmpresaPersona().getPersona().getApellidos());
        criteria.addEquals(Usuario_.estado, Entidad.Estado.ACTIVO);
        return criteria;
    }

    @Override
    public void saveUsuarioRolList(List<UsuarioRol> usuarioRolList) {
        if (usuarioRolList != null) {
            Date fecha = new Date();
            for (UsuarioRol usuarioRol : usuarioRolList) {
                usuarioRol.setModificado(fecha);
                update(usuarioRol);
            }
        }
    }

    @Override
    public void generateUsuarioRol(Usuario usuario, Rol rol) {
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setEstado(Entidad.Estado.ACTIVO);
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        Date fecha = new Date();
        usuarioRol.setCreado(fecha);
        usuarioRol.setModificado(fecha);
        
        saveOrMerge(usuarioRol);
    }

    @Override
    public List<UsuarioRol> getUsuarioRolList(Usuario usuario) {
        GenericCriteria<UsuarioRol> query = GenericCriteria.forClass(UsuarioRol.class);
        query.addEquals(UsuarioRol_.estado, Entidad.Estado.ACTIVO);
        query.addEquals(UsuarioRol_.usuario, usuario);

        return findByCriteria(query);
    }

    @Override
    public void deleteUsuarioRolList(List<UsuarioRol> usuarioRolList) {
    	deleteAll(usuarioRolList);
    	putSuccess("Rol(es) removido(s) correctamente");
    }

    private void buildSendMailCuentaUsuario(Usuario usuario) {      
        String sender = null;
        String subject = "eBos: Creacion Cuenta  " + usuario.getUsername(); //TODO (epa): Usar bundle
        String content = "Se creo correctamente cuenta para Usuario: " + usuario.getUsername()
                + " con Contraseña: " + usuario.getPassword() + " el " + usuario.getCreado();
        String to = usuario.getEmpresaPersona().getPersona().getMail();
        utilP.sendMail(subject, content, sender, to, null, null);            
    }

    private boolean isUsuarioValido(Usuario usuario) {
        String username = usuario.getUsername();
        
        GenericCriteria<Usuario> query = GenericCriteria.forClass(Usuario.class);                        
        query.addNotEqualsIfNotNull(Usuario_.id, usuario.getId());
        query.addEquals(Usuario_.username, username);        

        if (findFirstByCriteria(query) != null) {
            putError("Usuario: '"+username+"' ya existe");
            return false;
        }
        return true;
    }
    
    @Override
    public int getUsuarioCount(){                       
        return countByCriteria(buildUsuarioCriteria(new Usuario()));
    }

    //
    // Rol
    //
    @Override
    public List<Rol> findRolList(Rol rol, Pagination pagination) {
        GenericCriteria<Rol> criteria = GenericCriteria.forClass(Rol.class);
        criteria.addEquals(Rol_.estado, Entidad.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.creador);
        criteria.addAliasedLeftJoins(Auditoria_.modificador);
        if(rol != null){
        	criteria.addLike(Rol_.nombre, rol.getNombre());
            criteria.addLike(Rol_.descripcion, rol.getDescripcion());
        }
        if(pagination != null){
        	return findByCriteria(criteria, pagination);
        }
        return findByCriteria(criteria);
    }

    @Override
    public Rol createRol() {
        Rol rol = new Rol();
        rol.setEstado(Usuario.Estado.INACTIVO);
        return rol;
    }

    @Override
    public Rol saveRol(Rol rol) {
        Date date = new Date();
        if (GenericUtils.isPersistent(rol)) {
            rol.setModificado(date);
        } else {
            rol.setCreado(date);
            rol.setModificado(date);
            rol.setEstado(Entidad.Estado.ACTIVO);
        }
        rol = saveOrMerge(rol);
        putSuccess("rol.success.guardar",rol.getId());
        return rol;
    }

    @Override
    public void eliminarRol(Rol rol) {
        Long id = rol.getId();
    	delete(rol);
        putSuccess("rol.success.eliminar", id);        
    }

    @Override
    public List<RolOpcion> getRolOpcionList(Rol rol) {
        GenericCriteria<RolOpcion> query = GenericCriteria.forClass(RolOpcion.class);

        query.addEquals(RolOpcion_.estado, Entidad.Estado.ACTIVO);
        query.addEquals(RolOpcion_.rol, rol);

        return findByCriteria(query);
    }

    @Override
    public void generateRolOpcion(Rol rol, Opcion opcion) {
        RolOpcion rolOpcion = new RolOpcion();
        
        rolOpcion.setAuditoria(new Auditoria());
        rolOpcion.setCreador(getSessionBean().getUsuario());
        rolOpcion.setCreado(new Date());
        
        rolOpcion.setRol(rol);
        rolOpcion.setOpcion(opcion);
        rolOpcion.setEstado(Entidad.Estado.ACTIVO);
        rolOpcion.setCrear(false);
        rolOpcion.setEditar(false);
        rolOpcion.setEliminar(false);
        rolOpcion.setExportar(false);        
        
        saveOrMerge(rolOpcion);
    }
  
    @Override
    public void saveRolOpcionList(List<RolOpcion> rolOpcionList) { 
        if (rolOpcionList != null) {
        	Usuario usuario = getSessionBean().getUsuario();
            Date fecha = new Date();
            for (RolOpcion rolOpcion : rolOpcionList) {
            	rolOpcion.setModificador(usuario);
                rolOpcion.setModificado(fecha);
                update(rolOpcion);
            }
        }
    }

    @Override
    public RolOpcion saveRolOpcion(RolOpcion rolOpcion) {
        return update(rolOpcion);
    }

    @Override
    public void deleteRolOpcionList(List<RolOpcion> rolOpcionList) {
        deleteAll(rolOpcionList);
        putSuccess("Opcion(es) removido(s) correctamente");
    }

    //TODO (epa): Optimizar consulta
    private List<RolOpcion> obtenerRolOpcionList(Usuario usuario) {

        GenericCriteria<UsuarioRol> criteria = GenericCriteria.forClass(UsuarioRol.class);
        criteria.addEquals(UsuarioRol_.usuario, usuario);
        criteria.addEquals(UsuarioRol_.estado, Entidad.Estado.ACTIVO);
        List<UsuarioRol> usuarioRolList = findByCriteria(criteria);

        List<RolOpcion> rolOpcionList = new ArrayList<RolOpcion>();
        
        if (usuarioRolList != null) {
            for (UsuarioRol usuarioRol : usuarioRolList) {
                GenericCriteria<RolOpcion> rolOpcCriteria = GenericCriteria.forClass(RolOpcion.class);
                rolOpcCriteria.addEquals(RolOpcion_.estado, Entidad.Estado.ACTIVO);
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
    public List<Opcion> findOpcionList(Opcion opcion, Pagination pagination) {
        GenericCriteria<Opcion> criteria = GenericCriteria.forClass(Opcion.class);
        criteria.addEquals("estado", Entidad.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.creador);
        criteria.addAliasedLeftJoins(Auditoria_.modificador, Opcion_.padre);
        if (opcion != null) {
	        criteria.addAliasedLeftJoins(Opcion_.padre);
	        criteria.addLike(Opcion_.nombre, opcion.getNombre());
	        criteria.addLike(Opcion_.descripcion, opcion.getDescripcion());
	        criteria.addLike(Opcion_.etiqueta, opcion.getEtiqueta());
	        criteria.addLike(Opcion_.target, opcion.getTarget());
        }
        
        return findByCriteria(criteria, pagination);
    }

    @Override
    public Opcion createOpcion() {
        Opcion opcion = new Opcion();
        opcion.setEstado(Usuario.Estado.INACTIVO);
        return opcion;
    }

    @Override
    public Opcion saveOpcion(Opcion opcion) {
        if (!GenericUtils.isPersistent(opcion)) {
            opcion.setEstado(Entidad.Estado.ACTIVO);
        }
        opcion = saveOrMerge(opcion);
        putSuccess("Opcion " + opcion.getId() + " guardado correctamente");
        return opcion;
    }

    @Override
    public void deleteOpcion(Opcion opcion) {
        Long id = opcion.getId();
        delete(opcion);
        putSuccess("Opcion " + id + " eliminada correctamente");
    }

    @Override
    public Opcion getOpcion(Long id) {
    	GenericCriteria<Opcion> criteria = GenericCriteria.forClass(Opcion.class);
    	criteria.addEquals("estado", Entidad.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.creador);
        criteria.addAliasedLeftJoins(Auditoria_.modificador);
        return findFirstByCriteria(criteria);
    }

    @Override
    public List<Opcion> getOpcionPadreList() {
        GenericCriteria<Opcion> criteria = GenericCriteria.forClass(Opcion.class);
        criteria.addEquals(Opcion_.estado, Entidad.Estado.ACTIVO);
        criteria.addIsNull(Opcion_.padre);
        criteria.addOrderAsc(Opcion_.padre);
        return findByCriteria(criteria);
    }
    
    //
    // Objeto
    //
    
    @Override
    public List<Objeto> findObjetoList(Objeto objeto, Pagination pagination) {
        GenericCriteria<Objeto> criteria = GenericCriteria.forClass(Objeto.class);
        criteria.addEquals("estado", Entidad.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.creador);
        criteria.addAliasedLeftJoins(Auditoria_.modificador);
        if (objeto != null) {
	        criteria.addLike(Objeto_.descripcion, objeto.getDescripcion());
	        criteria.addLike(Objeto_.codigo, objeto.getCodigo());
	        criteria.addLike(Objeto_.estado, objeto.getEstado().getValue());
	        criteria.addLike(Objeto_.tipo, objeto.getTipo().getValue());
        }
        
        return findByCriteria(criteria, pagination);
    }

    @Override
    public Objeto createObjeto() {
        Objeto opcion = new Objeto();
        opcion.setEstado(Usuario.Estado.INACTIVO);
        return opcion;
    }

    @Override
    public Objeto saveObjeto(Objeto objeto) {
        if (!GenericUtils.isPersistent(objeto)) {
            objeto.setEstado(Entidad.Estado.ACTIVO);
        }
        objeto = saveOrMerge(objeto);
        putSuccess("Objeto " + objeto.getId() + " guardado correctamente");
        return objeto;
    }

    @Override
    public void deleteObjeto(Objeto objeto) {
        Long id = objeto.getId();
    	delete(objeto);
        putSuccess("Objeto " + id + " eliminado correctamente");
    }

    @Override
    public Objeto getObjeto(Long id) {
        return get(id, Objeto.class);
    }
    

    //
    // Session Usuario
    //
    
    @Override
    public boolean authLogin(SessionBean aThis) {
        GenericCriteria<Usuario> criteria = GenericCriteria.forClass(Usuario.class);
        criteria.addAliasedJoins(Usuario_.empresaPersona, Usuario_.empresaPersona+"."+EmpresaPersona_.persona);
        criteria.addEquals(Usuario_.estado, Entidad.Estado.ACTIVO);
        criteria.addEquals(Usuario_.username, aThis.getUsuario().getUsername());
        criteria.addEquals(Usuario_.password, CryptoUtils.computeHashSHA256(aThis.getUsuario().getPassword()));

        Usuario usuario = findFirstByCriteria(criteria);        
        if (usuario != null) {        	
        	aThis.setUsuario(usuario);
        } else {
            putError("sesion.error.usuarioPassIncorrecta");
            return false;
        }
        List<RolOpcion> rolOpcionList = obtenerRolOpcionList(usuario);
        aThis.setRolOpcionList(rolOpcionList);
        return true;
    }
    
    @Override
    public void changePassword(Usuario usuario){
        Usuario oldUsuario = getUsuario(usuario.getId());
        evict(oldUsuario);
        
        if (oldUsuario.getPassword().equals(CryptoUtils.computeHashSHA256(usuario.getPassword()))) {        	
            if(usuario.getNewpassword().equals(usuario.getConfpassword())){
                usuario.setPassword(CryptoUtils.computeHashSHA256(usuario.getNewpassword()));
                update(usuario);
                putSuccess("Contrasena actualizada correctamente");
            } else {
                putError("Contrasena de confirmacion incorrecta"); 
            }
        } else {
           putError("Contrasena actual incorrecta"); 
        }
    }
    
    @Override
    public SessionBean getSessionBean() {
    	return super.getSessionBean();
    }
    
    /**
     * Guarda las preferencias del usuario
     */
    public void saveUserPreferences(Usuario usuario){
    	Usuario oldUsuario = getUsuario(usuario.getId());
    	//Definir preferencias a guardar    	
    	oldUsuario.setTema(usuario.getTema());    	
    	update(oldUsuario);    	
    	putSuccess("usuario.success.guardarPreferencias");
    }
            
}
