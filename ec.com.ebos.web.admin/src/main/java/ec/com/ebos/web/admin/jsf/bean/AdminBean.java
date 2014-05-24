package ec.com.ebos.web.admin.jsf.bean;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.com.ebos.core.context.BeanScopes;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2014-01-27
 */
@Component(AdminBean.BEAN_NAME)
@Scope(BeanScopes.SESSION)
public class AdminBean {
    
	public static final String BEAN_NAME = "adminBean";

	@Getter @Setter
	private String value = "adminBean value";
	
    
}
