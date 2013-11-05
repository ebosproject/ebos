package ec.com.ebos.master.web.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ec.com.ebos.master.model.Tema;
import ec.com.ebos.master.session.SessionBean;

/**
 * 
 * @author Eduardo Plua Alay
 * @since 2013-02-27
 */
@Component
@ManagedBean(name = ThemeSwitcherBean.BEAN_NAME)
@RequestScoped
public class ThemeSwitcherBean implements Serializable{

	private static final long serialVersionUID = -8104402118225511781L;

	public static final String BEAN_NAME = "themeSwitcherBean";

	@Getter
    private Map<String, String> temas;
    
    @Getter
    private List<Tema> temasAvanzados;
    
    @Getter @Setter
    private String tema;

    @Setter
    @Autowired
    @Qualifier(SessionBean.BEAN_NAME)
    private SessionBean sessionBean;
    

    @PostConstruct
    public void init() { // TODO (epa): Leer temas desde base de datos
        tema = sessionBean.getTema();
        
        temasAvanzados = new ArrayList<Tema>();
        temasAvanzados.add(sessionBean.getInstanceTema("afterdark", "afterdark.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("afternoon", "afternoon.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("afterwork", "afterwork.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("aristo", "aristo.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("black-tie", "black-tie.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("blitzer", "blitzer.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("bluesky", "bluesky.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("bootstrap", "bootstrap.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("casablanca", "casablanca.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("cruze", "cruze.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("cupertino", "cupertino.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("dark-hive", "dark-hive.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("dot-luv", "dot-luv.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("eggplant", "eggplant.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("excite-bike", "excite-bike.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("flick", "flick.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("glass-x", "glass-x.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("home", "home.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("hot-sneaks", "hot-sneaks.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("humanity", "humanity.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("le-frog", "le-frog.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("midnight", "midnight.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("mint-choc", "mint-choc.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("overcast", "overcast.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("pepper-grinder", "pepper-grinder.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("redmond", "redmond.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("rocket", "rocket.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("sam", "sam.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("smoothness", "smoothness.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("south-street", "south-street.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("start", "start.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("sunny", "sunny.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("swanky-purse", "swanky-purse.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("trontastic", "trontastic.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("ui-darkness", "ui-darkness.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("ui-lightness", "ui-lightness.png"));
        temasAvanzados.add(sessionBean.getInstanceTema("vader", "vader.png"));
        
        temas = new TreeMap<String, String>();
        temas.put("Afterdark", "afterdark");
        temas.put("Afternoon", "afternoon");
        temas.put("Afterwork", "afterwork");
        temas.put("Aristo", "aristo");
        temas.put("Black-Tie", "black-tie");
        temas.put("Blitzer", "blitzer");
        temas.put("Bluesky", "bluesky");
        temas.put("Bootstrap", "bootstrap");
        temas.put("Casablanca", "casablanca");
        temas.put("Cupertino", "cupertino");
        temas.put("Cruze", "cruze");
        temas.put("Dark-Hive", "dark-hive");
        temas.put("Dot-Luv", "dot-luv");
        temas.put("Eggplant", "eggplant");
        temas.put("Excite-Bike", "excite-bike");
        temas.put("Flick", "flick");
        temas.put("Glass-X", "glass-x");
        temas.put("Home", "home");
        temas.put("Hot-Sneaks", "hot-sneaks");
        temas.put("Humanity", "humanity");
        temas.put("Le-Frog", "le-frog");
        temas.put("Midnight", "midnight");
        temas.put("Mint-Choc", "mint-choc");
        temas.put("Overcast", "overcast");
        temas.put("Pepper-Grinder", "pepper-grinder");
        temas.put("Redmond", "redmond");
        temas.put("Rocket", "rocket");
        temas.put("Sam", "sam");
        temas.put("Smoothness", "smoothness");
        temas.put("South-Street", "south-street");
        temas.put("Start", "start");
        temas.put("Sunny", "sunny");
        temas.put("Swanky-Purse", "swanky-purse");
        temas.put("Trontastic", "trontastic");
        temas.put("UI-Darkness", "ui-darkness");
        temas.put("UI-Lightness", "ui-lightness");
        temas.put("Vader", "vader");
    }
    
    public void saveTheme() {
        sessionBean.saveTheme(tema);
    }

}

