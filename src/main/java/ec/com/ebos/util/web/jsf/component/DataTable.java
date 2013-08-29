package ec.com.ebos.util.web.jsf.component;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.root.model.Entidad;

/**
 * 
 * @author Eduardo Plua Alay
 *
 * @param <T>
 */
public abstract class DataTable<T extends Entidad<T>> extends org.primefaces.component.datatable.DataTable {
	
	@Getter @Setter
    private List<T> list = new ArrayList<T>();
           
    @Getter @Setter
    private T[] selectedList;
    
    @Getter @Setter
    private T selected;    
    
        
    public abstract List<T> load();
    
    public void _add(){        
        selected = add();
    }
    
    public T add(){        
        return null;
    }
    
    public void _save(){                
    	save(selected);
    }

    public void save(T entity){                
        
    }
    
    public void _edit(){
        edit(selected);
    }
    
    public void edit(T entity){
        
    }
    
    public void _delete(){
        delete(selected);
    }
    
    public void delete(T entity){
        
    }

}
