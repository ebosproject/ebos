package ec.com.ebos.mse.core.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.ebos.conta.exception.ContaException;
import ec.com.ebos.master.model.field.Persona_;
import ec.com.ebos.mse.model.Grupo;
import ec.com.ebos.mse.model.MonaguilloGrupo;
import ec.com.ebos.mse.model.field.Grupo_;
import ec.com.ebos.mse.model.field.MonaguilloGrupo_;
import ec.com.ebos.mse.model.field.Monaguillo_;
import ec.com.ebos.mse.model.hibernate.HibernateGrupo;
import ec.com.ebos.mse.model.hibernate.HibernateMonaguillo;
import ec.com.ebos.mse.model.hibernate.HibernateMonaguilloGrupo;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.core.process.RootPImpl;
import ec.com.ebos.root.model.hibernate.HibernateEntidad.Estado;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.NumberUtils;
import ec.com.ebos.util.StringUtils;

/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 * 
 */
@Repository("mseP")
public class MsePImpl extends RootPImpl<Object, ContaException> implements MseP {

	private static final long serialVersionUID = 1631338076433131489L;

	//
    // Grupo
    //
    @Override
    public List<HibernateGrupo> findGrupoList(HibernateGrupo grupo, Pagination pagination) {
        GenericCriteria<HibernateGrupo> criteria = GenericCriteria.forClass(HibernateGrupo.class);
        criteria.addAliasedJoins(Grupo_.creador);
        criteria.addAliasedLeftJoins(Grupo_.modificador);
        criteria.addEqualsIfNotZero(Grupo_.id, grupo.getId());
       
        if(criteria.isChanged()){
        	return findByCriteria(criteria, pagination);
        }
        
        criteria.addLikeTokens(Grupo_.nombre, grupo.getNombre());
        
        return findByCriteria(criteria, pagination);
    }

    @Override
    public HibernateGrupo createGrupo() {
        HibernateGrupo grupo = new HibernateGrupo();
        grupo.setEstado(Estado.INACTIVO);
        return grupo;
    }

    @Override
    public HibernateGrupo saveGrupo(HibernateGrupo grupo) {
    	if(!EntityUtils.isPersistent(grupo)){
    		grupo.setEstado(Estado.ACTIVO);
    	}
        grupo = saveOrMerge(grupo);
        putSuccess("grupo.success.save", grupo.getId());
        return grupo;
    }

    @Override
    public void deleteGrupo(HibernateGrupo grupo) {
        Long id = grupo.getId();
        delete(grupo);
        putSuccess("grupo.success.delete",id);
    }
    
    //
    // Monaguillo
    //
    @Override
    public List<HibernateMonaguillo> findMonaguilloList(HibernateMonaguillo monaguillo, Pagination pagination) {
        GenericCriteria<HibernateMonaguillo> criteria = GenericCriteria.forClass(HibernateMonaguillo.class);
        criteria.addAliasedJoins(Monaguillo_.persona, Monaguillo_.creador);
        criteria.addAliasedLeftJoins(Monaguillo_.modificador);
        
        criteria.addEqualsIfNotZero(Monaguillo_.id, monaguillo.getId());
        if(criteria.isChanged()){
        	return findByCriteria(criteria, pagination);
        }
        
        criteria.addLikeTokens(Monaguillo_.persona+"."+Persona_.nombres,monaguillo.getPersona().getNombres());
        criteria.addLikeTokens(Monaguillo_.persona+"."+Persona_.apellidos, monaguillo.getPersona().getApellidos());
        criteria.addLikeTokens(Monaguillo_.centroEstudio, monaguillo.getCentroEstudio());
        criteria.addLikeTokens(Monaguillo_.representantes, monaguillo.getRepresentantes());
        
        return findByCriteria(criteria, pagination);
    }
    
    public List<HibernateMonaguillo> findMonaguilloList(String query){
    	if(StringUtils.isBlank(query)){
			return new ArrayList<HibernateMonaguillo>();
		}
	
		GenericCriteria<HibernateMonaguillo> criteria = GenericCriteria.forClass(HibernateMonaguillo.class);
		criteria.addAliasedJoins(Monaguillo_.persona);
		
		if(NumberUtils.tryParseLong(query)){
			criteria.addEqualsIfNotZero(Monaguillo_.id, NumberUtils.parseLong(query));
			if(criteria.isChanged()){
				return findByCriteria(criteria);
			}
		}
		
		criteria.addMultiLikeTokens(query, Monaguillo_.persona+"."+Persona_.nombres, Monaguillo_.persona+"."+Persona_.apellidos);

		return findByCriteria(criteria);
    }
    
    @Override
    public List<HibernateMonaguilloGrupo> getMonaguilloGrupoList(Grupo grupo){
    	GenericCriteria<HibernateMonaguilloGrupo> criteria = GenericCriteria.forClass(HibernateMonaguilloGrupo.class);
        criteria.addAliasedJoins(MonaguilloGrupo_.monaguillo, MonaguilloGrupo_.monaguillo+"."+MonaguilloGrupo_.persona,MonaguilloGrupo_.creador);
        criteria.addAliasedLeftJoins(MonaguilloGrupo_.modificador);
        criteria.addEquals(MonaguilloGrupo_.grupo, grupo);        
        return findByCriteria(criteria);
    }
    
       @Override
    public HibernateMonaguillo createMonaguillo() {
        HibernateMonaguillo monaguillo = new HibernateMonaguillo();
        monaguillo.setEstado(Estado.INACTIVO);
        return monaguillo;
    }

    @Override
    public HibernateMonaguillo saveMonaguillo(HibernateMonaguillo monaguillo) {
        
        if(!EntityUtils.isPersistent(monaguillo)){
        	monaguillo.setEstado(Estado.ACTIVO);
        }
        		        		
		monaguillo = saveOrMerge(monaguillo);
        putSuccess("monaguillo.success.save", monaguillo.getId());
        return monaguillo;
    }

    @Override
    public void deleteMonaguillo(HibernateMonaguillo monaguillo) {
        Long id = monaguillo.getId();
        delete(monaguillo);
        putSuccess("monaguillo.success.delete",id);
    }
    
    @Override
    public MonaguilloGrupo createMonaguilloGrupo(){
    	MonaguilloGrupo monaguilloGrupo = new HibernateMonaguilloGrupo();
        monaguilloGrupo.setEstado(Estado.INACTIVO);
        return monaguilloGrupo;
    }

    @Override
	public void saveMonaguilloGrupo(HibernateMonaguilloGrupo monaguilloGrupo){
    	 monaguilloGrupo = saveOrMerge(monaguilloGrupo);
         putSuccess("monaguilloGrupo.success.save", monaguilloGrupo.getId());
    }

    @Override
	public void deleteMonaguilloGrupo(HibernateMonaguilloGrupo monaguilloGrupo){
    	Long id = monaguilloGrupo.getId();
        delete(monaguilloGrupo);
        putSuccess("monaguilloGrupo.success.delete",id);
    }
}
