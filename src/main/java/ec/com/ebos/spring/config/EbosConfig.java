package ec.com.ebos.spring.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

/**
 * Spring Master Context Configuration - java
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-05
 * @see http://java.dzone.com/articles/springhibernate-application
 * @see http://www.sivalabs.in/2011/02/springhibernate-application-with-zero.html
 * @see http://nurkiewicz.blogspot.com/2011/01/spring-framework-without-xml-at-all.html
 * @see http://www.swiftmind.com/de/2011/06/22/spring-3-1-m2-testing-with-configuration-classes-and-profiles/
 */
@Import({RepositoryConfig.class})
@Configuration
@EnableAspectJAutoProxy
@EnableCaching
@ComponentScan({"ec.com.ebos.context",
	"ec.com.ebos.master",
	"ec.com.ebos.aspect", 
	"ec.com.ebos.security", 
	"ec.com.ebos.util",
	"ec.com.ebos.admin",
	"ec.com.ebos.conta",
	"ec.com.ebos.logis",
	"ec.com.ebos.mse"})
public class EbosConfig {
	
	@Bean
	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("ec/com/ebos/admin/resources/ebosconfig.properties"));
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}
	
}