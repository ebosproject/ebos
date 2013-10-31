package ec.com.ebos.security.core.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ec.com.ebos.admin.model.Objeto;
import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.aspect.core.exception.ExceptionAspectHandlerException;
import ec.com.ebos.master.model.field.EmpresaPersona_;
import ec.com.ebos.master.model.field.Persona_;
import ec.com.ebos.master.session.SessionBean;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.core.process.RootPImpl;
import ec.com.ebos.root.model.hibernate.HibernateAuditoria;
import ec.com.ebos.root.model.hibernate.HibernateEntidad;
import ec.com.ebos.root.model.hibernate.field.Auditoria_;
import ec.com.ebos.security.exception.SecurityException;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.field.RolOpcion_;
import ec.com.ebos.security.model.field.Rol_;
import ec.com.ebos.security.model.field.UsuarioRol_;
import ec.com.ebos.security.model.field.Usuario_;
import ec.com.ebos.security.model.hibernate.HibernateRol;
import ec.com.ebos.security.model.hibernate.HibernateRolOpcion;
import ec.com.ebos.security.model.hibernate.HibernateUsuario;
import ec.com.ebos.security.model.hibernate.HibernateUsuarioRol;
import ec.com.ebos.util.CryptoUtils;
import ec.com.ebos.util.EntityUtils;
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

	public void putError(ExceptionAspectHandlerException e){
		super.putError(e);
	}
	
	
    //
    //Usuario
    //
    @Override
    public HibernateUsuario createUsuario() {
        HibernateUsuario usuario = new HibernateUsuario();
        usuario.setEstado(Usuario.Estado.INACTIVO);
        return usuario;
    }

    @Override
    public HibernateUsuario getUsuario(Long id) {
        return get(id, HibernateUsuario.class);
    }

    @Override
    public HibernateUsuario saveUsuario(HibernateUsuario usuario) {

        if (isUsuarioValido(usuario)) {
            Date date = new Date();
            HibernateEntidad.Estado estadoActual = usuario.getEstado();
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
    public void deleteUsuario(HibernateUsuario usuario) { // TODO (epa): Usar definitivamente resource bundle para mensajes
        Long id = usuario.getId();
        delete(usuario);
        putSuccess("usuario.success.eliminar", id);
    }

    @Override
    public List<HibernateUsuario> findUsuarioList(HibernateUsuario usuario, Pagination pagination) {        
        return findByCriteria(buildUsuarioCriteria(usuario), pagination);
    }
    
    private GenericCriteria<HibernateUsuario> buildUsuarioCriteria(HibernateUsuario usuario){
        GenericCriteria<HibernateUsuario> criteria = GenericCriteria.forClass(HibernateUsuario.class);
        
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
    public void saveUsuarioRolList(List<HibernateUsuarioRol> usuarioRolList) {
        if (usuarioRolList != null) {
            Date fecha = new Date();
            for (HibernateUsuarioRol usuarioRol : usuarioRolList) {
                usuarioRol.setModificado(fecha);
                update(usuarioRol);
            }
        }
    }

    @Override
    public void generateUsuarioRol(HibernateUsuario usuario, Rol rol) {
        HibernateUsuarioRol usuarioRol = new HibernateUsuarioRol();
        usuarioRol.setEstado(HibernateEntidad.Estado.ACTIVO);
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        Date fecha = new Date();
        usuarioRol.setCreado(fecha);
        usuarioRol.setModificado(fecha);
        
        saveOrMerge(usuarioRol);
    }

    @Override
    public List<HibernateUsuarioRol> getUsuarioRolList(HibernateUsuario usuario) {
        GenericCriteria<HibernateUsuarioRol> query = GenericCriteria.forClass(HibernateUsuarioRol.class);
        query.addEquals(UsuarioRol_.estado, HibernateEntidad.Estado.ACTIVO);
        query.addEquals(UsuarioRol_.usuario, usuario);

        return findByCriteria(query);
    }

    @Override
    public void deleteUsuarioRolList(List<HibernateUsuarioRol> usuarioRolList) {
    	deleteAll(usuarioRolList);
    	putSuccess("Rol(es) removido(s) correctamente");
    }

    private void buildSendMailCuentaUsuario(HibernateUsuario usuario) {      
        String sender = null;
        String subject = "eBos: Creacion Cuenta  " + usuario.getUsername(); //TODO (epa): Usar bundle
        String content = "Se creo correctamente cuenta para Usuario: " + usuario.getUsername()
                + " con Contraseña: " + usuario.getPassword() + " el " + usuario.getCreado();
        String to = usuario.getEmpresaPersona().getPersona().getMail();
        utilP.sendMail(subject, content, sender, to, null, null);            
    }

    private boolean isUsuarioValido(HibernateUsuario usuario) {
        String username = usuario.getUsername();
        
        GenericCriteria<HibernateUsuario> query = GenericCriteria.forClass(HibernateUsuario.class);                        
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
    public List<HibernateRol> findRolList(HibernateRol rol, Pagination pagination) {
        GenericCriteria<HibernateRol> criteria = GenericCriteria.forClass(HibernateRol.class);
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
    public HibernateRol createRol() {
        HibernateRol rol = new HibernateRol();
        rol.setEstado(Usuario.Estado.INACTIVO);
        return rol;
    }

    @Override
    public HibernateRol saveRol(HibernateRol rol) {
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
    public void eliminarRol(HibernateRol rol) {
        Long id = rol.getId();
    	delete(rol);
        putSuccess("rol.success.eliminar", id);        
    }

    @Override
    public List<HibernateRolOpcion> getRolOpcionList(Rol rol) {
        GenericCriteria<HibernateRolOpcion> query = GenericCriteria.forClass(HibernateRolOpcion.class);

        query.addEquals(RolOpcion_.estado, HibernateEntidad.Estado.ACTIVO);
        query.addEquals(RolOpcion_.rol, rol);

        return findByCriteria(query);
    }

    @Override
    public void generateRolOpcion(Rol rol, Opcion opcion) {
        HibernateRolOpcion rolOpcion = new HibernateRolOpcion();
        
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
    public void saveRolOpcionList(List<HibernateRolOpcion> rolOpcionList) { 
        if (rolOpcionList != null) {
        	HibernateUsuario usuario = getSessionBean().getUsuario();
            Date fecha = new Date();
            for (HibernateRolOpcion rolOpcion : rolOpcionList) {
            	rolOpcion.setModificador(usuario);
                rolOpcion.setModificado(fecha);
                update(rolOpcion);
            }
        }
    }

    @Override
    public RolOpcion saveRolOpcion(HibernateRolOpcion rolOpcion) {
        return update(rolOpcion);
    }

    @Override
    public void deleteRolOpcionList(List<HibernateRolOpcion> rolOpcionList) {
        deleteAll(rolOpcionList);
        putSuccess("Opcion(es) removido(s) correctamente");
    }

    //TODO (epa): Optimizar consulta
    private List<HibernateRolOpcion> obtenerRolOpcionList(HibernateUsuario usuario) {

        GenericCriteria<HibernateUsuarioRol> criteria = GenericCriteria.forClass(HibernateUsuarioRol.class);
        criteria.addEquals(UsuarioRol_.usuario, usuario);
        criteria.addEquals(UsuarioRol_.estado, HibernateEntidad.Estado.ACTIVO);
        List<HibernateUsuarioRol> usuarioRolList = findByCriteria(criteria);

        List<HibernateRolOpcion> rolOpcionList = new ArrayList<HibernateRolOpcion>();
        
        if (usuarioRolList != null) {
            for (HibernateUsuarioRol usuarioRol : usuarioRolList) {
                GenericCriteria<HibernateRolOpcion> rolOpcCriteria = GenericCriteria.forClass(HibernateRolOpcion.class);
                rolOpcCriteria.addEquals(RolOpcion_.estado, HibernateEntidad.Estado.ACTIVO);
                rolOpcCriteria.addEquals(RolOpcion_.rol, usuarioRol.getRol());
                List<HibernateRolOpcion> tmp = findByCriteria(rolOpcCriteria);
                if (tmp != null) {
                	for (HibernateRolOpcion rolOpcion : tmp) {
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

        HibernateUsuario usuario = findFirstByCriteria(criteria);        
        if (usuario != null) {        	
        	sessionBean.setUsuario(usuario);
        } else {
            putError("sesion.error.usuarioPassIncorrecta");
            return false;
        }
        List<HibernateRolOpcion> rolOpcionList = obtenerRolOpcionList(usuario);
        sessionBean.setRolOpcionList(rolOpcionList);
        return true;
    }
    
    @Override
    public void changePassword(HibernateUsuario usuario){
        HibernateUsuario oldUsuario = getUsuario(usuario.getId());
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
    public void saveUserPreferences(HibernateUsuario usuario){
    	HibernateUsuario oldUsuario = getUsuario(usuario.getId());
    	//Definir preferencias a guardar    	
    	oldUsuario.setTema(usuario.getTema());
    	oldUsuario.setLocalidad(usuario.getLocalidad());
    	update(oldUsuario);    	
    	putSuccess("usuario.success.guardarPreferencias");
    }
            
}
