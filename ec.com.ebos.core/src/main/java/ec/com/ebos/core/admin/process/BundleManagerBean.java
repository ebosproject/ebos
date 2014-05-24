package ec.com.ebos.core.admin.process;

import java.util.ArrayList;

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.eclipse.gemini.blueprint.context.event.OsgiBundleApplicationContextEvent;
import org.eclipse.gemini.blueprint.context.event.OsgiBundleApplicationContextListener;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.com.ebos.core.context.BeanScopes;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2014-02-15
 */
@Component(BundleManagerBean.BEAN_NAME)
@Scope(BeanScopes.SINGLETON)
public class BundleManagerBean implements BundleContextAware, OsgiBundleApplicationContextListener {
    
	private static final long serialVersionUID = 542524389247595045L;
	
	public static final String BEAN_NAME = "bundleViewBean";

	private static Bundle[] bundles;

	public static synchronized Bundle[] getBundles() {
		return bundles;
	}


	@Override
	public void onOsgiApplicationEvent(OsgiBundleApplicationContextEvent event) {
		if (BundleEvent.STARTED == event.getBundle().getState()) {

		} else if (BundleEvent.STOPPED == event.getBundle().getState()) {

		}
	}

	@Override
	public void setBundleContext(BundleContext context) {
		Bundle[] allBundles = context.getBundles();
		ArrayList<Bundle> bundleList = new ArrayList<Bundle>();

		// Hack for adding only our relevant bundles to the list
		for (Bundle bundle : allBundles) {
			String symbolicName = bundle.getSymbolicName();
			if (symbolicName.startsWith("ec.com.ebos")) {
				bundleList.add(bundle);
			}
		}
		bundles = bundleList.toArray(new Bundle[] {});
	}
    
}
