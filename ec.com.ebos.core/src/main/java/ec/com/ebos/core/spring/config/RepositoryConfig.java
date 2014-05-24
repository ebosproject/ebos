package ec.com.ebos.core.spring.config;

import java.util.Properties;

//import javax.naming.InitialContext;
import javax.naming.NamingException;
//import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.interceptor.TransactionInterceptor;

//import ec.com.ebos.core.aspect.process.AuditoryAspect;
//import ec.com.ebos.core.aspect.process.ExceptionAspect;
//import ec.com.ebos.core.aspect.process.SecurityAspect;
import ec.com.ebos.core.orm.crud.CrudService;
//import ec.com.ebos.core.orm.crud.FinderSQLService;
import ec.com.ebos.core.orm.crud.FinderService;
import ec.com.ebos.core.orm.crud.support.BaseDaoSupport;
import ec.com.ebos.core.util.Constantes;
//import ec.com.ebos.orm.crud.impl.CrudServiceImpl;
//import ec.com.ebos.orm.crud.impl.FinderSQLServiceImpl;
//import ec.com.ebos.orm.crud.impl.FinderServiceImpl;


/**
 * Spring Master Java Configuration
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
    @Value("${hibernate.hbm2ddl.auto}") private String hibernateHbm2ddlAuto;
    @Value("${hibernate.query.substitutions}") private String hibernateQuerySubstitutions;
    @Value("${hibernate.connection.release_mode}") private String hibernateConnectionReleaseMode;
    @Value("${connection.autocommit}") private String connectionAutocommit;
    @Value("${hibernate.connection.driver_class}") private String hibernateConnectionDriverClass;
    @Value("${hibernate.connection.url}") private String hibernateConnectionUrl;
    @Value("${hibernate.connection.username}") private String hibernateConnectionUsername;
    @Value("${hibernate.connection.password}") private String hibernateConnectionPassword;
    
    //@Value("${ebos.jndi}") private String ebosJndi;
    
	@Value("${transaction.maxRegistros}") private int transactionMaxRegistros;
	
	@Value("${transaction.requiredAll}") private String transactionRequiredAll;
	@Value("${transaction.findAll}") private String transactionFindAll;
	@Value("${transaction.getAll}") private String transactionGetAll;
	@Value("${transaction.isAll}") private String transactionIsAll;
	@Value("${transaction.createAll}") private String transactionCreateAll;
	@Value("${transaction.searchAll}") private String transactionSearchAll;
	@Value("${transaction.buildAll}") private String transactionBuildAll;
	
//	@Bean
//	public DataSource dataSource() throws NamingException{ //TODO (epa): Optimizar este metodo
//		try{
//			return (DataSource) new InitialContext().lookup(ebosJndi);
//		} catch(Exception ex){
//			ex.getStackTrace();
//			return (DataSource) new InitialContext().lookup(ebosJndi);
//		}		
//	}
	

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
				Constantes.DOMAIN_NAME+".admin.model",
				Constantes.DOMAIN_NAME+".master.model",
				Constantes.DOMAIN_NAME+".security.model",
				Constantes.DOMAIN_NAME+".conta.model",
				Constantes.DOMAIN_NAME+".logis.model",
				Constantes.DOMAIN_NAME+".mse.model",
				});
		
		asfb.setHibernateProperties(hibernateProperties());
		//asfb.setDataSource(dataSource());
		asfb.setLobHandler(defaultLobHandler());
		
		return asfb;
	}
	
	@Bean 
	@DependsOn("propertyPlaceholderConfigurer")
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
		properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        properties.setProperty("hibernate.query.substitutions", hibernateQuerySubstitutions);
        properties.setProperty("hibernate.connection.release_mode", hibernateConnectionReleaseMode);
        properties.setProperty("connection.autocommit", connectionAutocommit);
        properties.setProperty("hibernate.connection.driver_class", hibernateConnectionDriverClass);
        properties.setProperty("hibernate.connection.url", hibernateConnectionUrl);
        properties.setProperty("hibernate.connection.username", hibernateConnectionUsername);
        properties.setProperty("hibernate.connection.password", hibernateConnectionPassword);
		return properties;
	}
	
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
		return new HibernateTransactionManager(sessionFactory);
	}
	
	@Bean
	@Autowired
	public TransactionInterceptor transactionInterceptor(HibernateTransactionManager transactionManager){
		return new TransactionInterceptor(transactionManager, transactionModulesAttributes()); 
	}
	
	/**
	 * CrudServiceBean
	 * 
	 * @param sessionFactory
	 * @return
	 * @throws NamingException
	 */
//	@Bean
//	@Autowired 
//	public CrudService crudService(SessionFactory sessionFactory) throws NamingException{
//		return new CrudServiceImpl(sessionFactory);
//	}
	
	/**
	 * FinderSQLServiceBean
	 * 
	 * @return
	 * @throws NamingException
	 */
//	@Bean
//	public FinderSQLService finderSQLService() throws NamingException{		
//		return new FinderSQLServiceImpl();
//	}

	
	/**
	 * FinderServiceBean
	 * 
	 * @param sessionFactory
	 * @return
	 * @throws NamingException
	 */
//	@Bean
//	@Autowired
//	public FinderService finderService(SessionFactory sessionFactory) throws NamingException{
//		return new FinderServiceImpl(sessionFactory, transactionMaxRegistros, finderSQLService());
//	}
	

	@Bean 
	@Autowired
	public BaseDaoSupport baseDaoSupport(CrudService crudService, FinderService finderService) throws NamingException{
		return new BaseDaoSupport(crudService, finderService);
	}
	
	/**
	 * Propiedades de transaccion para todos los modulos de la plataforma
	 * 
	 * @return properties
	 */
	public Properties transactionModulesAttributes(){
		Properties properties = new Properties();
		properties.setProperty("*", transactionRequiredAll);
		properties.setProperty("find*", transactionFindAll);
		properties.setProperty("get*", transactionGetAll);
		properties.setProperty("is*", transactionIsAll);
		properties.setProperty("create*", transactionCreateAll);
		properties.setProperty("search*", transactionSearchAll);
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
	
//	/**
//	 * Manager de la cache haciendo uso del soporte por defecto de ehcache
//	 */
//	@Bean
//	public EhCacheCacheManager cacheManager(){
//		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
//		cacheManager.setCacheManager(ehcache().getObject());
//		return cacheManager;
//	}
	
	////////////////// ASPECTS /////////////////////
	
//	@Bean
//	public ExceptionAspectPImpl exceptionAspect(){
//		return new ExceptionAspectPImpl();
//	}
//	
//	@Bean
//	public SecurityAspectPImpl securityAspect(){
//		return new SecurityAspectPImpl();
//	}
//	
//	@Bean
//	public AuditoryAspectPImpl auditoryAspect(){
//		return new AuditoryAspectPImpl();
//	}
//	
}
