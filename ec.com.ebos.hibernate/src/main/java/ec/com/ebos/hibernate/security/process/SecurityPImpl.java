package ec.com.ebos.hibernate.security.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ec.com.ebos.core.admin.model.Objeto;
import ec.com.ebos.core.admin.model.Opcion;
import ec.com.ebos.core.aspect.exception.ExceptionAspectHandlerException;
import ec.com.ebos.core.master.session.SessionBean;
import ec.com.ebos.core.orm.crud.GenericCriteria;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.root.model.Entidad.Estado;
import ec.com.ebos.core.security.exception.SecurityException;
import ec.com.ebos.core.security.model.Rol;
import ec.com.ebos.core.security.model.RolOpcion;
import ec.com.ebos.core.security.model.Usuario;
import ec.com.ebos.core.security.model.UsuarioRol;
import ec.com.ebos.core.security.process.SecurityP;
import ec.com.ebos.core.util.CryptoUtils;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.StringUtils;
import ec.com.ebos.core.util.process.UtilP;
import ec.com.ebos.hibernate.master.model.field.EmpresaPersona_;
import ec.com.ebos.hibernate.master.model.field.Persona_;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;
import ec.com.ebos.hibernate.root.model.HibernateEntidad;
import ec.com.ebos.hibernate.root.model.field.Auditoria_;
import ec.com.ebos.hibernate.root.process.RootPImpl;
import ec.com.ebos.hibernate.security.model.HibernateRol;
import ec.com.ebos.hibernate.security.model.HibernateRolOpcion;
import ec.com.ebos.hibernate.security.model.HibernateUsuario;
import ec.com.ebos.hibernate.security.model.HibernateUsuarioRol;
import ec.com.ebos.hibernate.security.model.field.RolOpcion_;
import ec.com.ebos.hibernate.security.model.field.Rol_;
import ec.com.ebos.hibernate.security.model.field.UsuarioRol_;
import ec.com.ebos.hibernate.security.model.field.Usuario_;

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

	public void putError(ExceptionAspectHandlerException e){
		super.putError(e);
	}
	
	
    //
    //Usuario
    //
    @Override
    public Usuario createUsuario() {
        Usuario usuario = new HibernateUsuario();
        usuario.setEstado(Estado.INACTIVO);
        return usuario;
    }

    @Override
    public HibernateUsuario getUsuario(Long id) {
        return get(id, HibernateUsuario.class);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {

        if (isUsuarioValido(usuario)) {
            Date date = new Date();
            Entidad.Estado estadoActual = usuario.getEstado();
            
            if (EntityUtils.isPersistent(usuario)) {
                usuario.setModificado(date);
            } else {
                usuario.setPassword(CryptoUtils.computeHashSHA256(usuario.getPassword()));
                usuario.setCreado(date);
                usuario.setModificado(date);
                usuario.setEstado(HibernateEntidad.Estado.ACTIVO);
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
        criteria.addEquals(Usuario_.estado, HibernateEntidad.Estado.ACTIVO);
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
        UsuarioRol usuarioRol = new HibernateUsuarioRol();
        usuarioRol.setEstado(Estado.ACTIVO);
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
        query.addEquals(UsuarioRol_.estado, HibernateEntidad.Estado.ACTIVO);
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
        return countByCriteria(buildUsuarioCriteria(new HibernateUsuario()));
    }

    //
    // Rol
    //
    @Override
    public List<Rol> findRolList(Rol rol, Pagination pagination) {
        GenericCriteria<Rol> criteria = GenericCriteria.forClass(Rol.class);
        criteria.addEquals(Rol_.estado, HibernateEntidad.Estado.ACTIVO);
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
        Rol rol = new HibernateRol();
        rol.setEstado(Estado.INACTIVO);
        return rol;
    }

    @Override
    public Rol saveRol(Rol rol) {
        Date date = new Date();
        if (EntityUtils.isPersistent(rol)) {
            rol.setModificado(date);
        } else {
            rol.setCreado(date);
            rol.setModificado(date);
            rol.setEstado(HibernateEntidad.Estado.ACTIVO);
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

        query.addEquals(RolOpcion_.estado, Estado.ACTIVO);
        query.addEquals(RolOpcion_.rol, rol);

        return findByCriteria(query);
    }

    @Override
    public void generateRolOpcion(Rol rol, Opcion opcion) {
        RolOpcion rolOpcion = new HibernateRolOpcion();
        
        rolOpcion.setAuditoria(new HibernateAuditoria());
        rolOpcion.setCreador(getSessionBean().getUsuario());
        rolOpcion.setCreado(new Date());
        
        rolOpcion.setRol(rol);
        rolOpcion.setOpcion(opcion);
        rolOpcion.setEstado(HibernateEntidad.Estado.ACTIVO);
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
        criteria.addEquals(UsuarioRol_.estado, HibernateEntidad.Estado.ACTIVO);
        List<UsuarioRol> usuarioRolList = findByCriteria(criteria);

        List<RolOpcion> rolOpcionList = new ArrayList<RolOpcion>();
        
        if (usuarioRolList != null) {
            for (UsuarioRol usuarioRol : usuarioRolList) {
                GenericCriteria<RolOpcion> rolOpcCriteria = GenericCriteria.forClass(RolOpcion.class);
                rolOpcCriteria.addEquals(RolOpcion_.estado, Estado.ACTIVO);
                rolOpcCriteria.addEquals(RolOpcion_.rol, usuarioRol.getRol());
                List<RolOpcion> tmp = findByCriteria(rolOpcCriteria);
                if (tmp != null) {
                	for (RolOpcion rolOpcion : tmp) {
                		Objeto obj = rolOpcion.getOpcion().getObjeto();
						String beanName = obj != null ? obj.getCodigo() : StringUtils.EMPTY;
						rolOpcion.getOpcion().setBeanName(beanName);
					}                	
                    rolOpcionList.addAll(tmp);
                }
            }
        }

        return rolOpcionList;
    }

    
    //
    // Session Usuario
    //
    
    @Override
    public boolean authLogin(SessionBean sessionBean) {
        GenericCriteria<HibernateUsuario> criteria = GenericCriteria.forClass(HibernateUsuario.class);
        criteria.addAliasedJoins(Usuario_.empresaPersona, Usuario_.empresaPersona+"."+EmpresaPersona_.persona);
        criteria.addEquals(Usuario_.estado, HibernateEntidad.Estado.ACTIVO);
        criteria.addEquals(Usuario_.username, sessionBean.getUsuario().getUsername());
        criteria.addEquals(Usuario_.password, CryptoUtils.computeHashSHA256(sessionBean.getUsuario().getPassword()));

        Usuario usuario = findFirstByCriteria(criteria);        
        if (usuario != null) {        	
        	sessionBean.setUsuario(usuario);
        } else {
            putError("sesion.error.usuarioPassIncorrecta");
            return false;
        }
        List<RolOpcion> rolOpcionList = obtenerRolOpcionList(usuario);
        sessionBean.setRolOpcionList(rolOpcionList);
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
    
    @Override
    public Auditoria getAuditoria(){
    	return super.getAuditoria();
    }
    
    
    /**
     * Guarda las preferencias del usuario
     */
    public void saveUserPreferences(Usuario usuario){
    	HibernateUsuario oldUsuario = getUsuario(usuario.getId());
    	//Definir preferencias a guardar    	
    	oldUsuario.setTema(usuario.getTema());
    	oldUsuario.setLocalidad(usuario.getLocalidad());
    	update(oldUsuario);    	
    	putSuccess("usuario.success.guardarPreferencias");
    }
            
}
