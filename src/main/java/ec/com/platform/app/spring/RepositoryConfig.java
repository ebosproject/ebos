package ec.com.platform.app.spring;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import ec.com.platform.fwk.crud.CrudService;
import ec.com.platform.fwk.crud.FinderSQLService;
import ec.com.platform.fwk.crud.FinderService;
import ec.com.platform.fwk.crud.impl.CrudServiceImpl;
import ec.com.platform.fwk.crud.impl.FinderSQLServiceImpl;
import ec.com.platform.fwk.crud.impl.FinderServiceImpl;
import ec.com.platform.fwk.crud.support.BaseDaoSupport;
import ec.com.platform.util.Constantes;


/**
 * Spring Application Java Configuration
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-06
 */
@Configuration
public class RepositoryConfig {
	
	@Value("${hibernate.show_sql}") private String hibernateShowSql;
    @Value("${hibernate.dialect}") private String hibernateDialect;
    @Value("${hibernate.c3p0.minPoolSize}") private String hibernateMinPoolSize;
    @Value("${hibernate.c3p0.maxPoolSize}") private String hibernateMaxPoolSize;
    @Value("${hibernate.c3p0.timeout}") private String hibernateTimeout;
    @Value("${hibernate.c3p0.max_statement}") private String hibernateMaxStatement;
    @Value("${hibernate.c3p0.testConnectionOnCheckout}") private String hibernateTestConnectionOnCheckout;
    @Value("${hibernate.jdbc.batch_size}") private String hibernateBatchSize;
    
    @Value("${platform.jndi}") private String platformJndi;
    
    @Value("${transaction.create}") private String transactionCreate;
    @Value("${transaction.update}") private String transactionUpdate;
	@Value("${transaction.delete}") private String transactionDelete;
	@Value("${transaction.saveOrUpdate}") private String transactionSaveOrUpdate;
	@Value("${transaction.load}") private String transactionLoad;
	@Value("${transaction.bulkUpdate}") private String transactionBulkUpdate;
	
	@Value("${transaction.findByFuncionNoTx}") private String transactionFindByFuncionNoTx;
	@Value("${transaction.findByProcedimientoNoTx}") private String transactionFindByProcedimiento;
	@Value("${transaction.all}") private String transactionAll;
	@Value("${transaction.maxRegistros}") private int transactionMaxRegistros;
    
	@Value("${transaction.requiredAll}") private String transactionRequiredAll;
	@Value("${transaction.findAll}") private String transactionFindAll;
	@Value("${transaction.getAll}") private String transactionGetAll;
	@Value("${transaction.isAll}") private String transactionIsAll;
	@Value("${transaction.obtenerAll}") private String transactionObtenerAll;
	@Value("${transaction.buildAll}") private String transactionBuildAll;
	
	@Bean
	public DataSource dataSource() throws NamingException{ //TODO (epa): Cambiar mensaje por resource bundle
		try{
			return (DataSource) new InitialContext().lookup(platformJndi);
		} catch(Exception ex){
			System.err.println("Error en creacion de DataSource: "+ex.getMessage());
			return null;
		}		
	}
	

	@Bean @Lazy(true)
	public LobHandler defaultLobHandler(){
		return new DefaultLobHandler();
	}
	
	/**
	 * Configuracion de los diferentes paquetes de modulos del sistema
	 * 
	 * @return sessionFactory
	 * @throws NamingException
	 */
	@Bean
	public AnnotationSessionFactoryBean sessionFactory() throws NamingException{
		AnnotationSessionFactoryBean asfb = new AnnotationSessionFactoryBean();
		
		asfb.setPackagesToScan(new String[]{
				Constantes.DOMAIN_NAME+".app.model",
				Constantes.DOMAIN_NAME+".seguridad.model",
				Constantes.DOMAIN_NAME+".administracion.model"
				});
		
		asfb.setHibernateProperties(hibernateProperties());
		asfb.setDataSource(dataSource());
		asfb.setLobHandler(defaultLobHandler());
		
		return asfb;
	}
	
	@Bean 
	public Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql", hibernateShowSql);
		properties.setProperty("hibernate.dialect", hibernateDialect);
		properties.setProperty("hibernate.c3p0.minPoolSize", hibernateMaxPoolSize);
		properties.setProperty("hibernate.c3p0.maxPoolSize", hibernateMinPoolSize);
		properties.setProperty("hibernate.c3p0.timeout", hibernateTimeout);
		properties.setProperty("hibernate.c3p0.max_statement", hibernateMaxStatement);
		properties.setProperty("hibernate.c3p0.testConnectionOnCheckout", hibernateTestConnectionOnCheckout);
		properties.setProperty("hibernate.jdbc.batch_size", hibernateBatchSize);
		return properties;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
		return new HibernateTransactionManager(sessionFactory);
	}
	
	/**
	 * CrudServiceBean
	 * 
	 * @param sessionFactory
	 * @return
	 * @throws NamingException
	 */
	@Bean
	@Autowired 
	public CrudService crudService(SessionFactory sessionFactory) throws NamingException{
		return new CrudServiceImpl(sessionFactory, transactionCrudAttributes());
	}
	
	/**
	 * FinderSQLServiceBean
	 * 
	 * @return
	 * @throws NamingException
	 */
	@Bean
	public FinderSQLService finderSQLService() throws NamingException{		
		return new FinderSQLServiceImpl(dataSource(), transactionFinderAttributes());
	}

	
	/**
	 * FinderServiceBean
	 * 
	 * @param sessionFactory
	 * @return
	 * @throws NamingException
	 */
	@Bean
	@Autowired
	public FinderService finderService(SessionFactory sessionFactory) throws NamingException{
		return new FinderServiceImpl(sessionFactory, transactionMaxRegistros, finderSQLService(), transactionFinderAttributes());
	}
	
	
	@Bean
	public Properties transactionCrudAttributes(){
		Properties properties = new Properties();
		properties.setProperty("create", transactionCreate);
		properties.setProperty("delete", transactionDelete);
		properties.setProperty("update", transactionUpdate);
		properties.setProperty("load", transactionLoad);
		properties.setProperty("saveOrUpdate", transactionSaveOrUpdate);
		properties.setProperty("bulkUpdate", transactionBulkUpdate);
		return properties;
	}
	
	@Bean
	public Properties transactionFinderAttributes(){
		Properties properties = new Properties();
		properties.setProperty("findByFuncionNoTx", transactionFindByFuncionNoTx);
		properties.setProperty("findByProcedimientoNoTx", transactionFindByProcedimiento);
		properties.setProperty("*", transactionAll);
		return properties;
	}
	
	@Bean 
	@Autowired
	public BaseDaoSupport baseDaoSupport(CrudService crudService, FinderService finderService) throws NamingException{
		return new BaseDaoSupport(crudService, finderService);
	}
	
	@Bean
	public Properties transactionModulesAttributes(){
		Properties properties = new Properties();
		properties.setProperty("*", transactionRequiredAll);
		properties.setProperty("find*", transactionFindAll);
		properties.setProperty("get*", transactionGetAll);
		properties.setProperty("is*", transactionIsAll);
		properties.setProperty("obtener*", transactionObtenerAll);
		properties.setProperty("build*", transactionBuildAll);
		return properties;
	}
	
	/**
	 * Configuracion de las caches haciendo uso del fichero de propiedades tipico de ehcache
	 */
	@Bean 
	public EhCacheManagerFactoryBean ehcache(){
		EhCacheManagerFactoryBean ehcache = new EhCacheManagerFactoryBean();
		ehcache.setConfigLocation(new ClassPathResource("ehcache.xml"));
		return ehcache;
	}
	
	/**
	 * Manager de la cache haciendo uso del soporte por defecto de ehcache
	 */
	@Bean
	public EhCacheCacheManager cacheManager(){
		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
		cacheManager.setCacheManager(ehcache().getObject());
		return cacheManager;
	}
}
