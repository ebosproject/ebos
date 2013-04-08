package ec.com.ebos.app.web.jsf.mb;

import ec.com.ebos.app.web.jsf.component.LayoutOptions;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import lombok.Getter;
import lombok.Setter;

/**
 * LayoutMB
 *
 * @author Eduardo Plua Alay
 * @since 2013-02-05
 */
@ApplicationScoped //@ViewScoped
@ManagedBean(eager = true, name="layoutMB")
public class LayoutMB {

	@Getter @Setter
	private LayoutOptions layoutOptionsHome;

	@Getter @Setter
	private LayoutOptions layoutOptionsContent;
	
	@PostConstruct
	protected void initialize() {
		initLayoutHome();
		initLayoutContent();
	}
	
	/**
	 * Inicializa Opciones del <pl:layout para homePage
	 */
	private void initLayoutHome(){
		layoutOptionsHome = new LayoutOptions();

		// for all panes
		LayoutOptions panes = new LayoutOptions();
		panes.addOption("resizable", true);
		panes.addOption("closable", true);
		panes.addOption("slidable", false);
		panes.addOption("spacing", 6);
		panes.addOption("resizeWithWindow", false);
		panes.addOption("resizeWhileDragging", true);
		layoutOptionsHome.setPanesOptions(panes);

		// north pane
		LayoutOptions north = new LayoutOptions();
		north.addOption("resizable", false);
		north.addOption("closable", false);
		north.addOption("size", 60);
		layoutOptionsHome.setNorthOptions(north);

		// south pane
		LayoutOptions south = new LayoutOptions();
		south.addOption("resizable", false);
		south.addOption("closable", false);
		south.addOption("size", 40);
		layoutOptionsHome.setSouthOptions(south);

		// center pane
		LayoutOptions center = new LayoutOptions();
		center.addOption("resizable", false);
		center.addOption("closable", false);
		center.addOption("resizeWhileDragging", false);
		center.addOption("minWidth", 200);
		center.addOption("minHeight", 60);
		layoutOptionsHome.setCenterOptions(center);

		// west pane
		LayoutOptions west = new LayoutOptions();
		west.addOption("size", 210);
		west.addOption("minSize", 180);
		west.addOption("maxSize", 500);
		layoutOptionsHome.setWestOptions(west);

		// east pane
		LayoutOptions east = new LayoutOptions();
		east.addOption("size", 448);
		east.addOption("minSize", 180);
		east.addOption("maxSize", 650);
		layoutOptionsHome.setEastOptions(east);

		// nested east layout
		LayoutOptions childEastOptions = new LayoutOptions();
		east.setChildOptions(childEastOptions);

		// east-center pane
		LayoutOptions eastCenter = new LayoutOptions();
		eastCenter.addOption("minHeight", 60);
		childEastOptions.setCenterOptions(eastCenter);

		// south-center pane
		LayoutOptions southCenter = new LayoutOptions();
		southCenter.addOption("size", "70%");
		southCenter.addOption("minSize", 60);
		childEastOptions.setSouthOptions(southCenter);
	}
	
	/**
	 * Inicializa Opciones del <pl:layout para contentPage
	 */
	private void initLayoutContent(){
		layoutOptionsContent = new LayoutOptions();

		// for all panes
		LayoutOptions panes = new LayoutOptions();
		panes.addOption("resizable", true);
		panes.addOption("closable", true);
		panes.addOption("slidable", false);
		panes.addOption("spacing", 6);
		panes.addOption("resizeWithWindow", false);
		panes.addOption("resizeWhileDragging", true);
		layoutOptionsContent.setPanesOptions(panes);

		// north pane
		LayoutOptions north = new LayoutOptions();
		north.addOption("resizable", false);
		north.addOption("closable", false);
		north.addOption("size", 60);
		layoutOptionsContent.setNorthOptions(north);

		// south pane
		LayoutOptions south = new LayoutOptions();
		south.addOption("resizable", false);
		south.addOption("closable", false);
		south.addOption("size", 40);
		layoutOptionsContent.setSouthOptions(south);

		// center pane
		LayoutOptions center = new LayoutOptions();
		center.addOption("resizable", false);
		center.addOption("closable", false);
		center.addOption("resizeWhileDragging", false);
		center.addOption("minWidth", 200);
		center.addOption("minHeight", 60);
		layoutOptionsContent.setCenterOptions(center);

		// west pane
		LayoutOptions west = new LayoutOptions();
		//west.addOption("size", 210);
		west.addOption("minSize", 270);
		west.addOption("maxSize", 350);
		layoutOptionsContent.setWestOptions(west);

		// east pane
		LayoutOptions east = new LayoutOptions();
		east.addOption("size", 448);
		east.addOption("minSize", 180);
		east.addOption("maxSize", 650);
		layoutOptionsContent.setEastOptions(east);

		// nested east layout
		LayoutOptions childEastOptions = new LayoutOptions();
		east.setChildOptions(childEastOptions);

		// east-center pane
		LayoutOptions eastCenter = new LayoutOptions();
		eastCenter.addOption("minHeight", 60);
		childEastOptions.setCenterOptions(eastCenter);

		// south-center pane
		LayoutOptions southCenter = new LayoutOptions();
		southCenter.addOption("size", "70%");
		southCenter.addOption("minSize", 60);
		childEastOptions.setSouthOptions(southCenter);
	}

}
