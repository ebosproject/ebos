package ec.com.ebos.security.model;

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

import org.hibernate.annotations.Type;

import ec.com.ebos.admin.model.Bundle;
import ec.com.ebos.admin.model.HibernateBundle;
import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.master.model.EmpresaPersona;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.hibernate.HibernateEntidad;
import ec.com.ebos.security.model.field.UsuarioRol_;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = Usuario.TABLE_NAME, schema = Security.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Auditable
public class Usuario extends Security<Usuario> {

	private static final long serialVersionUID = 5615088107461153660L;

	protected static final String TABLE_NAME = "USUARIO";
	private static final String SEQUENCE = Security.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@Embedded
	private Auditoria auditoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresapersona", nullable = false)
    private EmpresaPersona empresaPersona;
    
    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;
    
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = HibernateEntidad.Estado.TYPE)
    private HibernateEntidad.Estado estado;
    
    @Column(name = "localidad", length = 5)
    @Type(type = Bundle.Localidad.TYPE)
    private HibernateBundle.Localidad localidad;
    
    @Column(name = "tema", length = 50)
    private String tema;
    
    @Column(name = "maxoptions")
    private int maxOptions;

    @OneToMany(mappedBy = UsuarioRol_.usuario, fetch = FetchType.LAZY)
    private Set<UsuarioRol> usuarioRolList = new HashSet<UsuarioRol>(0);

    @Transient
    private String newpassword;
    
    @Transient
    private String confpassword;
    
}
