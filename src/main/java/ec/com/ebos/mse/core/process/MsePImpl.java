package ec.com.ebos.mse.core.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.ebos.conta.exception.ContaException;
import ec.com.ebos.master.model.field.Persona_;
import ec.com.ebos.mse.model.Grupo;
import ec.com.ebos.mse.model.Monaguillo;
import ec.com.ebos.mse.model.MonaguilloGrupo;
import ec.com.ebos.mse.model.field.Grupo_;
import ec.com.ebos.mse.model.field.MonaguilloGrupo_;
import ec.com.ebos.mse.model.field.Monaguillo_;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.core.process.RootPImpl;
import ec.com.ebos.root.model.Entidad.Estado;
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
    public List<Grupo> findGrupoList(Grupo monagillo, Pagination pagination) {
        GenericCriteria<Grupo> criteria = GenericCriteria.forClass(Grupo.class);
        criteria.addAliasedJoins(Grupo_.creador);
        criteria.addAliasedLeftJoins(Grupo_.modificador);
        criteria.addEqualsIfNotZero(Grupo_.id, monagillo.getId());
        if(criteria.isChanged()){
        	return findByCriteria(criteria, pagination);
        }
        
//        criteria.addEqualsIfNotNull(Grupo_.tipo, monagillo.getTipo());
//        criteria.addEqualsIfNotNull(Grupo_.naturaleza, monagillo.getNaturaleza());
//        criteria.addLikeIfNotNull(Grupo_.codigo, monagillo.getCodigo());
//        criteria.addLikeIfNotNull(Grupo_.descripcion, monagillo.getDescripcion());        
        
        return findByCriteria(criteria, pagination);
    }

    @Override
    public Grupo createGrupo() {
        Grupo grupo = new Grupo();
        grupo.setEstado(Estado.INACTIVO);
        return grupo;
    }

    @Override
    public Grupo saveGrupo(Grupo grupo) {
        grupo = saveOrMerge(grupo);
        putSuccess("grupo.success.save", grupo.getId());
        return grupo;
    }

    @Override
    public void deleteGrupo(Grupo grupo) {
        Long id = grupo.getId();
        delete(grupo);
        putSuccess("grupo.success.delete",id);
    }
    
    //
    // Monaguillo
    //
    @Override
    public List<Monaguillo> findMonaguilloList(Monaguillo monaguillo, Pagination pagination) {
        GenericCriteria<Monaguillo> criteria = GenericCriteria.forClass(Monaguillo.class);
        criteria.addAliasedJoins(Monaguillo_.persona, Monaguillo_.creador);
        criteria.addAliasedLeftJoins(Monaguillo_.modificador);
        criteria.addEqualsIfNotZero(Monaguillo_.id, monaguillo.getId());
        if(criteria.isChanged()){
        	return findByCriteria(criteria, pagination);
        }
        
//        criteria.addEqualsIfNotNull(Monagillo_.tipo, monagillo.getTipo());
//        criteria.addEqualsIfNotNull(Monagillo_.naturaleza, monagillo.getNaturaleza());
//        criteria.addLikeIfNotNull(Monagillo_.codigo, monagillo.getCodigo());
//        criteria.addLikeIfNotNull(Monagillo_.descripcion, monagillo.getDescripcion());        
        
        return findByCriteria(criteria, pagination);
    }
    
    public List<Monaguillo> findMonaguilloList(String query){
    	if(StringUtils.isBlank(query)){
			return new ArrayList<Monaguillo>();
		}
	
		GenericCriteria<Monaguillo> criteria = GenericCriteria.forClass(Monaguillo.class);
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
    public List<MonaguilloGrupo> getMonaguilloGrupoList(Grupo grupo){
    	GenericCriteria<MonaguilloGrupo> criteria = GenericCriteria.forClass(MonaguilloGrupo.class);
        criteria.addAliasedJoins(MonaguilloGrupo_.monaguillo, MonaguilloGrupo_.monaguillo+"."+MonaguilloGrupo_.persona,MonaguilloGrupo_.creador);
        criteria.addAliasedLeftJoins(MonaguilloGrupo_.modificador);
        criteria.addEquals(MonaguilloGrupo_.grupo, grupo);        
        return findByCriteria(criteria);
    }
    
       @Override
    public Monaguillo createMonaguillo() {
        Monaguillo monaguillo = new Monaguillo();
        monaguillo.setEstado(Estado.INACTIVO);
        return monaguillo;
    }

    @Override
    public Monaguillo saveMonaguillo(Monaguillo monaguillo) {
        monaguillo = saveOrMerge(monaguillo);
        putSuccess("monaguillo.success.save", monaguillo.getId());
        return monaguillo;
    }

    @Override
    public void deleteMonaguillo(Monaguillo monaguillo) {
        Long id = monaguillo.getId();
        delete(monaguillo);
        putSuccess("monaguillo.success.delete",id);
    }
    
    @Override
    public MonaguilloGrupo createMonaguilloGrupo(){
    	MonaguilloGrupo monaguilloGrupo = new MonaguilloGrupo();
        monaguilloGrupo.setEstado(Estado.INACTIVO);
        return monaguilloGrupo;
    }

    @Override
	public void saveMonaguilloGrupo(MonaguilloGrupo monaguilloGrupo){
    	 monaguilloGrupo = saveOrMerge(monaguilloGrupo);
         putSuccess("monaguilloGrupo.success.save", monaguilloGrupo.getId());
    }

    @Override
	public void deleteMonaguilloGrupo(MonaguilloGrupo monaguilloGrupo){
    	Long id = monaguilloGrupo.getId();
        delete(monaguilloGrupo);
        putSuccess("monaguilloGrupo.success.delete",id);
    }
}
