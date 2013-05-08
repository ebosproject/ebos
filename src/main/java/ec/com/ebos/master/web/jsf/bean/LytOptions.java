package ec.com.ebos.master.web.jsf.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.extensions.model.layout.LayoutOptions;

/**
 * Layout options para componente #LytFinder en tplFinder.xhtml
 * 
 * @author Eduardo Plua Alay
 * @since 2013-02-05
 * @update 2013-05-08
 */
@ApplicationScoped
@ManagedBean(eager = true, name = LytOptions.BEAN_NAME)
public class LytOptions implements Serializable {

	private static final long serialVersionUID = -8598274695579761829L;
	
	public static final String BEAN_NAME = "lytOptions";
	
	@Getter @Setter
	private String finder;
	
	@Getter @Setter
	private String master;

	@PostConstruct
	protected void initialize() {
		finder = buildFinderOptions();
		master = buildMasterOptions();
	}
	
	private String buildFinderOptions(){
		
		LayoutOptions layoutOptions = new LayoutOptions();

		// for all panes
		LayoutOptions panes = new LayoutOptions();
		panes.addOption("resizable", true);
		panes.addOption("closable", true);
		panes.addOption("slidable", false);
		panes.addOption("resizeWithWindow", false);
		panes.addOption("resizeWhileDragging", true);
		layoutOptions.setPanesOptions(panes);

		// north pane
		LayoutOptions north = new LayoutOptions();
		north.addOption("resizable", false);
		north.addOption("closable", false);
		north.addOption("size", 45);
		north.addOption("spacing_open", 0);
		layoutOptions.setNorthOptions(north);

		// south pane
		LayoutOptions south = new LayoutOptions();
		south.addOption("resizable", true);
		south.addOption("closable", true);
		south.addOption("size", 30);
		//south.addOption("spacing_open", 0);
		layoutOptions.setSouthOptions(south);

		// center pane
		LayoutOptions center = new LayoutOptions();
		center.addOption("resizable", false);
		center.addOption("closable", false);
		center.addOption("resizeWhileDragging", false);
		center.addOption("minWidth", 200);
		center.addOption("minHeight", 60);
		layoutOptions.setCenterOptions(center);

		// west pane
		LayoutOptions west = new LayoutOptions();
		west.addOption("size", 210);
		west.addOption("minSize", 180);
		west.addOption("maxSize", 500);
		layoutOptions.setWestOptions(west);

		// east pane
		LayoutOptions east = new LayoutOptions();
		east.addOption("size", 448);
		east.addOption("minSize", 180);
		east.addOption("maxSize", 650);
		layoutOptions.setEastOptions(east);

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

		// serialize options to JSON string (increase perf.)
		return layoutOptions.toJson();
	}
	
	private String buildMasterOptions(){
		
		LayoutOptions layoutOptions = new LayoutOptions();

		// for all panes
		LayoutOptions panes = new LayoutOptions();
		panes.addOption("resizable", true);
		panes.addOption("closable", true);
		panes.addOption("slidable", false);
		panes.addOption("resizeWithWindow", false);
		panes.addOption("resizeWhileDragging", true);
		layoutOptions.setPanesOptions(panes);

		// north pane
		LayoutOptions north = new LayoutOptions();
		north.addOption("resizable", false);
		north.addOption("closable", false);
		north.addOption("size", 45);
		north.addOption("spacing_open", 0);
		layoutOptions.setNorthOptions(north);

		// south pane
		LayoutOptions south = new LayoutOptions();
		south.addOption("resizable", true);
		south.addOption("closable", true);
		south.addOption("size", 30);
		//south.addOption("spacing_open", 0);
		layoutOptions.setSouthOptions(south);

		// center pane
		LayoutOptions center = new LayoutOptions();
		center.addOption("resizable", false);
		center.addOption("closable", false);
		center.addOption("resizeWhileDragging", false);
		center.addOption("minWidth", 200);
		center.addOption("minHeight", 60);
		layoutOptions.setCenterOptions(center);

		// west pane
		LayoutOptions west = new LayoutOptions();
		west.addOption("size", 210);
		west.addOption("minSize", 180);
		west.addOption("maxSize", 500);
		layoutOptions.setWestOptions(west);

		// east pane
		LayoutOptions east = new LayoutOptions();
		east.addOption("size", 448);
		east.addOption("minSize", 180);
		east.addOption("maxSize", 650);
		layoutOptions.setEastOptions(east);

		// nested center layout
		LayoutOptions childCenterOptions = new LayoutOptions();
		center.setChildOptions(childCenterOptions);

		// east-center pane
		LayoutOptions eastCenter = new LayoutOptions();
		eastCenter.addOption("minHeight", 60);
		childCenterOptions.setCenterOptions(eastCenter);

		// south-center pane
		LayoutOptions southCenter = new LayoutOptions();
		southCenter.addOption("size", "70%");
		southCenter.addOption("minSize", 60);
		childCenterOptions.setSouthOptions(southCenter);

		// serialize options to JSON string (increase perf.)
		return layoutOptions.toJson();
	}

}