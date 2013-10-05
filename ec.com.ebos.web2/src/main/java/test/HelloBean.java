package test;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

@Component
@ManagedBean
@SessionScoped
public class HelloBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Getter @Setter
    private String name = "Amor";
//    
//    public String getName() {
//        return name;
//    }
//    
//    public void setName(String name) {
//        this.name = name;
//    }

}
