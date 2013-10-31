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

import ec.com.ebos.master.model.hibernate.HibernateTema;
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
    private List<HibernateTema> temasAvanzados;
    
    @Getter @Setter
    private String tema;

    @Setter
    @Autowired
    @Qualifier(SessionBean.BEAN_NAME)
    private SessionBean sessionBean;
    

    @PostConstruct
    public void init() { // TODO (epa): Leer temas desde base de datos
        tema = sessionBean.getTema();
        
        temasAvanzados = new ArrayList<HibernateTema>();
        temasAvanzados.add(new HibernateTema("afterdark", "afterdark.png"));
        temasAvanzados.add(new HibernateTema("afternoon", "afternoon.png"));
        temasAvanzados.add(new HibernateTema("afterwork", "afterwork.png"));
        temasAvanzados.add(new HibernateTema("aristo", "aristo.png"));
        temasAvanzados.add(new HibernateTema("black-tie", "black-tie.png"));
        temasAvanzados.add(new HibernateTema("blitzer", "blitzer.png"));
        temasAvanzados.add(new HibernateTema("bluesky", "bluesky.png"));
        temasAvanzados.add(new HibernateTema("bootstrap", "bootstrap.png"));
        temasAvanzados.add(new HibernateTema("casablanca", "casablanca.png"));
        temasAvanzados.add(new HibernateTema("cruze", "cruze.png"));
        temasAvanzados.add(new HibernateTema("cupertino", "cupertino.png"));
        temasAvanzados.add(new HibernateTema("dark-hive", "dark-hive.png"));
        temasAvanzados.add(new HibernateTema("dot-luv", "dot-luv.png"));
        temasAvanzados.add(new HibernateTema("eggplant", "eggplant.png"));
        temasAvanzados.add(new HibernateTema("excite-bike", "excite-bike.png"));
        temasAvanzados.add(new HibernateTema("flick", "flick.png"));
        temasAvanzados.add(new HibernateTema("glass-x", "glass-x.png"));
        temasAvanzados.add(new HibernateTema("home", "home.png"));
        temasAvanzados.add(new HibernateTema("hot-sneaks", "hot-sneaks.png"));
        temasAvanzados.add(new HibernateTema("humanity", "humanity.png"));
        temasAvanzados.add(new HibernateTema("le-frog", "le-frog.png"));
        temasAvanzados.add(new HibernateTema("midnight", "midnight.png"));
        temasAvanzados.add(new HibernateTema("mint-choc", "mint-choc.png"));
        temasAvanzados.add(new HibernateTema("overcast", "overcast.png"));
        temasAvanzados.add(new HibernateTema("pepper-grinder", "pepper-grinder.png"));
        temasAvanzados.add(new HibernateTema("redmond", "redmond.png"));
        temasAvanzados.add(new HibernateTema("rocket", "rocket.png"));
        temasAvanzados.add(new HibernateTema("sam", "sam.png"));
        temasAvanzados.add(new HibernateTema("smoothness", "smoothness.png"));
        temasAvanzados.add(new HibernateTema("south-street", "south-street.png"));
        temasAvanzados.add(new HibernateTema("start", "start.png"));
        temasAvanzados.add(new HibernateTema("sunny", "sunny.png"));
        temasAvanzados.add(new HibernateTema("swanky-purse", "swanky-purse.png"));
        temasAvanzados.add(new HibernateTema("trontastic", "trontastic.png"));
        temasAvanzados.add(new HibernateTema("ui-darkness", "ui-darkness.png"));
        temasAvanzados.add(new HibernateTema("ui-lightness", "ui-lightness.png"));
        temasAvanzados.add(new HibernateTema("vader", "vader.png"));
        
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

