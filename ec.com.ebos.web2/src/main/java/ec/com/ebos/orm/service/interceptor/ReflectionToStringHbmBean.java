package ec.com.ebos.orm.service.interceptor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReflectionToStringHbmBean {
	private static PropertyUtilsBean pb = new PropertyUtilsBean();
	private static final Log log = LogFactory.getLog(ReflectionToStringHbmBean.class);
	
	public static String toString(Object pHbmObject) {
		String result = null;
		StringBuffer buffer = new StringBuffer(pHbmObject.toString());
		buffer.append("| { ");
		PropertyDescriptor descriptors[] = pb
				.getPropertyDescriptors(pHbmObject);
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			if("class".equals(name))continue;
			Method metodo = descriptors[i].getReadMethod(); 
			if (metodo != null)
				try {
					if(StringUtils.contains(metodo.getReturnType().getName(), "Set")) continue;
					buffer.append(name + "=" + pb.getProperty(pHbmObject, name));
					buffer.append(" , ");
				} catch (Exception e) {
					log.warn(" no se puede obtener el valor de la propiedad " + metodo + " : " + e.getMessage());
				}
		}
		buffer.append("}");
		result = buffer.toString();
		return result;
	}
}
