package test;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class HelloBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name = "Amor";
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

}
