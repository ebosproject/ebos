package ec.com.platform.app.spring.utils;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils  implements Serializable{

	private static final long serialVersionUID = -4421663812212450203L;
	
	private static ApplicationContext applicationContext;
    
    @Autowired
    public SpringUtils(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }
     
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return (T)applicationContext.getBean(beanName, beanClass);
    }
     
    public static <T> T getBean(Class<T> beanClass) {
        return (T)applicationContext.getBean(beanClass);
    }

}
