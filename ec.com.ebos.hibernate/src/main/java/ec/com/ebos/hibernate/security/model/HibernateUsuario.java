package ec.com.ebos.hibernate.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;

import ec.com.ebos.core.admin.model.Bundle;
import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.master.model.EmpresaPersona;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.security.model.Security;
import ec.com.ebos.core.security.model.Usuario;
import ec.com.ebos.core.security.model.UsuarioRol;
import ec.com.ebos.hibernate.master.model.HibernateEmpresaPersona;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;
import ec.com.ebos.hibernate.security.model.field.UsuarioRol_;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateUsuario.TABLE_NAME, schema = Security.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Auditable
public class HibernateUsuario extends HibernateSecurity implements Usuario {

	private static final long serialVersionUID = 5615088107461153660L;

	protected static final String TABLE_NAME = "USUARIO";
	private static final String SEQUENCE = Security.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateEmpresaPersona.class)
	@JoinColumn(name = "id_empresapersona", nullable = false)
    private EmpresaPersona empresaPersona;
    
    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;
    
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    @Column(name = "localidad", length = 5)
    @Type(type = Bundle.Localidad.TYPE)
    private Bundle.Localidad localidad;
    
    @Column(name = "tema", length = 50)
    private String tema;
    
    @Column(name = "maxoptions")
    private int maxOptions;

    @OneToMany(mappedBy = UsuarioRol_.usuario, fetch = FetchType.LAZY, targetEntity = HibernateUsuarioRol.class)
    private Set<UsuarioRol> usuarioRolList = new HashSet<UsuarioRol>(0);

    @Transient
    private String newpassword;
    
    @Transient
    private String confpassword;

    
}
