package ec.com.ebos.mse.web.jsf.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.mse.model.Monaguillo;
import ec.com.ebos.mse.model.MonaguilloGrupo;
import ec.com.ebos.mse.model.hibernate.HibernateGrupo;
import ec.com.ebos.mse.model.hibernate.HibernateMonaguillo;
import ec.com.ebos.mse.model.hibernate.HibernateMonaguilloGrupo;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.StringUtils;
import ec.com.ebos.util.web.jsf.component.DataTable;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-08-23
 */
@Component
@ManagedBean(name = GrupoBean.BEAN_NAME)
@SessionScoped
public class GrupoBean extends MseBean<HibernateGrupo> {
    	
	private static final long serialVersionUID = 6778254758599296978L;
	
	@Getter
	public static final String BEAN_NAME = "grupoBean";

	@Override
    public void getInit() {
        // Para busquedas
        entitySearch = new HibernateGrupo();
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(EntityUtils.isPersistent(activeEntity)){
            
        } else {

        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/modules/mse/grupo/finder.xhtml";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<HibernateGrupo> loadDataTableCollection(HibernateGrupo grupo, Pagination pagination) {
    	return mseS.findGrupoList(grupo, pagination);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = mseS.createGrupo();
        monaguilloGrupoDataTable.unload();
    }

    @Override
    public void editar() {        
    	//activeEntity = mseS.loadGrupo(activeEntity.getId());
    	monaguilloGrupoDataTable.load();
    }
    
    @Override
    public void actualizar(){                        
        editar();
    }

    @Override
    public void guardar() {
        activeEntity = mseS.saveGrupo(activeEntity);                
    }

    @Override
    public void eliminar() {
    	mseS.deleteGrupo(activeEntity);                
    }            
    
    //////////////////////// DATALISTS ///////////////////////////////
    
    @Getter @Setter
    protected DataTable<HibernateMonaguilloGrupo> monaguilloGrupoDataTable = new DataTable<HibernateMonaguilloGrupo>(){

		@Override
		public List<HibernateMonaguilloGrupo> loadCollection() {
			return mseS.getMonaguilloGrupoList(activeEntity);
		}
		
		@Override
		public MonaguilloGrupo add() {
			return mseS.createMonaguilloGrupo();
		}
		
		@Override
		public void save(HibernateMonaguilloGrupo entity) {
			entity.setGrupo(activeEntity);
			mseS.saveMonaguilloGrupo(entity);
		}
		
		@Override
		public void edit(MonaguilloGrupo entity) {
			
		}
		
		@Override
		public void delete(HibernateMonaguilloGrupo entity) {
			mseS.deleteMonaguilloGrupo(entity);
		}
				    	
    };
    
    
    //////////////////// AUTOCOMPLETES ///////////////////////////
    @Getter @Setter
	private List<HibernateMonaguillo> suggestionMonaguillo;
    
 	public List<HibernateMonaguillo> completeMonaguillo(String query){
 		suggestionMonaguillo = mseS.findMonaguilloList(query);
 		return suggestionMonaguillo;
 	}
 	
 	// //////////////////// CONVERTERS ////////////////////////////
 	public Converter getMonaguilloConverter() {
 		return new Converter() {

 			@Override
 			public Object getAsObject(FacesContext context,
 					UIComponent component, String submittedValue)
 					throws ConverterException {
 				if (submittedValue.trim().equals("")) {
 					return null;
 				} else {
 					try {
 						for(HibernateMonaguillo m : suggestionMonaguillo){
                 			if(m.getId().equals(Long.parseLong(submittedValue))){
                 				return m;
                 			}
                 		}
 					} catch (NumberFormatException exception) {
 						throw new ConverterException(new FacesMessage(
 								FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid object"));
 					}
 				}
 				return null;
 			}

 			@Override
 			public String getAsString(FacesContext context,
 					UIComponent component, Object value)
 					throws ConverterException {
 				if (value == null || value.equals("")) {
 					return StringUtils.EMPTY;
 				} else {
 					return String.valueOf(((HibernateMonaguillo) value).getId());
 				}
 			}
 		};
 	}
 	
 	// ////////////////////// GETTERS /////////////////////////////////
 	public Monaguillo getMonaguillo(Long id){
 		for(HibernateMonaguillo m : suggestionMonaguillo){
 			if(m.getId().equals(id)){
 				return m;
 			}
 		}
 		return null;
 	}
    
}
