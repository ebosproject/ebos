/*
 * Copyright 2011 PrimeFaces Extensions.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * $Id$
 */

package ec.com.ebos.master.web.jsf.bean.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import lombok.Getter;
import ec.com.ebos.util.Constantes;

/**
 * Informacion tecnica del sistema
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a> 
 * @since 2013/05/08
 */
@ApplicationScoped
@ManagedBean(eager = true, name = TechnicalInfo.BEAN_NAME)
public class TechnicalInfo {

	private static final Logger LOGGER = Logger.getLogger(TechnicalInfo.class.getName());
	
	public static final String BEAN_NAME = "technicalInfo";
	
	@Getter
	private String version;
	
	@Getter
	private String spring;
	
	@Getter
	private String hibernate;
	
	@Getter
	private String primeFaces;
	
	@Getter
	private String primeFacesExt;
	
	@Getter
	private String jsfImpl;
	
	@Getter
	private String server;
	
	@Getter
	private String buildTime;
	
	@Getter
	private boolean mojarra = true;

	private List<String> newComponents = new ArrayList<String>();
	private List<String> updatedComponents = new ArrayList<String>();

	@PostConstruct
	protected void initialize() {
		ResourceBundle rb;
		try {
			rb = ResourceBundle.getBundle(Constantes.DOMAIN_NAME+".admin.resources.ebosconfig");

			String strAppProps = rb.getString("application.properties");
			int lastBrace = strAppProps.indexOf("}");
			strAppProps = strAppProps.substring(1, lastBrace);

			Map<String, String> appProperties = new HashMap<String, String>();
			String[] appProps = strAppProps.split("[\\s,]+");
			for (String appProp : appProps) {
				String[] keyValue = appProp.split("=");
				if (keyValue != null && keyValue.length > 1) {
					appProperties.put(keyValue[0], keyValue[1]);
				}
			}
			
			version = appProperties.get("ebos.version");
			spring = "Spring: " + appProperties.get("spring.version");
			hibernate = "Hibernate: " + appProperties.get("hibernate.version");
			primeFaces = "PrimeFaces: " + appProperties.get("primefaces.version");
			primeFacesExt = "PrimeFaces Extensions: " + appProperties.get("primefaces-extensions.version");
			jsfImpl = "JSF: " + appProperties.get("jsf-impl") + " " + appProperties.get("jsfVersion");
			server =
			    "Server: "
			    + ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getServerInfo();

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar calendar = Calendar.getInstance();

			if (appProperties.containsKey("timestamp")) {
				calendar.setTimeInMillis(Long.valueOf(appProperties.get("timestamp")));
			}

			buildTime = "Build time: " + formatter.format(calendar.getTime());
			mojarra = appProperties.get("jsf-impl").contains("mojarra");

			//mfenoglio process new and updated components
			this.proccessNewsComponents(appProperties.get("primefaces-extensions.new-components"),
			                            appProperties.get("primefaces-extensions.updated-components"));
		} catch (MissingResourceException e) {
			LOGGER.warning("Resource bundle 'ebosconfig' was not found");
		}
	}

	public String getMenuitemIconStyleClass(final String page) {
		if (newComponents.contains(page)) {
			return "ui-icon-new-comp";
		}

		if (updatedComponents.contains(page)) {
			return "ui-icon-updated-comp";
		}

		return "ui-icon-none";
	}

	private void proccessNewsComponents(final String newComp, final String updatedComp) {
		try {
			String[] newCompArray = newComp.split(";");
			Collections.addAll(newComponents, newCompArray);

			String[] updatedCompArray = updatedComp.split(";");
			Collections.addAll(updatedComponents, updatedCompArray);
		} catch (Exception ex) {
			this.newComponents = new ArrayList<String>();
			this.updatedComponents = new ArrayList<String>();
		}
	}
}
