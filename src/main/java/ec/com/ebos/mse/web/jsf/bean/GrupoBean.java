package ec.com.ebos.mse.web.jsf.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.mse.model.Grupo;
import ec.com.ebos.mse.model.Monaguillo;
import ec.com.ebos.mse.model.MonaguilloGrupo;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.StringUtils;
import ec.com.ebos.util.web.jsf.component.DataTable;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-08-23
 */
@ManagedBean(name = GrupoBean.BEAN_NAME)
@SessionScoped
public class GrupoBean extends MseBean<Grupo> {
    	
	private static final long serialVersionUID = 6778254758599296978L;
	
	@Getter
	public static final String BEAN_NAME = "grupoBean";

	@Override
    public void getInit() {
        // Para busquedas
        entitySearch = new Grupo();
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(EntityUtils.isPersistent(activeEntity)){
            
        } else {
        	setHabilitaEliminar(false);
        	//TODO (epa): La plataforma no debe permitir para todos las pantallas mostrar el boton eliminar si la 
        	//entidad activa no esta persistida en la base de datos
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/modules/mse/grupo/finder.xhtml";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Grupo> loadDataTableCollection(Grupo grupo, Pagination pagination) {
    	return mseS.findGrupoList(grupo, pagination);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = mseS.createGrupo();
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
    protected DataTable<MonaguilloGrupo> monaguilloGrupoDataTable = new DataTable<MonaguilloGrupo>(){

		@Override
		public List<MonaguilloGrupo> loadCollection() {
			return mseS.getMonaguilloGrupoList(activeEntity);
		}
		
		@Override
		public MonaguilloGrupo add() {
			return mseS.createMonaguilloGrupo();
		}
		
		@Override
		public void save(MonaguilloGrupo entity) {
			entity.setGrupo(activeEntity);
			mseS.saveMonaguilloGrupo(entity);
		}
		
		@Override
		public void edit(MonaguilloGrupo entity) {
			
		}
		
		@Override
		public void delete(MonaguilloGrupo entity) {
			mseS.deleteMonaguilloGrupo(entity);
		}
				    	
    };
    
    
    //////////////////// AUTOCOMPLETES ///////////////////////////
    @Getter @Setter
	private List<Monaguillo> suggestionMonaguillo;
    
 	public List<Monaguillo> completeMonaguillo(String query){
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
 						for(Monaguillo m : suggestionMonaguillo){
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
 					return StringUtils.empty;
 				} else {
 					return String.valueOf(((Monaguillo) value).getId());
 				}
 			}
 		};
 	}
 	
 	// ////////////////////// GETTERS /////////////////////////////////
 	public Monaguillo getMonaguillo(Long id){
 		for(Monaguillo m : suggestionMonaguillo){
 			if(m.getId().equals(id)){
 				return m;
 			}
 		}
 		return null;
 	}
    
}
