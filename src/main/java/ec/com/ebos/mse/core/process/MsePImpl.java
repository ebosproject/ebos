package ec.com.ebos.mse.core.process;

import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.ebos.conta.exception.ContaException;
import ec.com.ebos.mse.model.Grupo;
import ec.com.ebos.mse.model.Monaguillo;
import ec.com.ebos.mse.model.field.Grupo_;
import ec.com.ebos.mse.model.field.Monagillo_;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.core.process.RootPImpl;

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
        Grupo monagillo = new Grupo();
        return monagillo;
    }

    @Override
    public Grupo saveGrupo(Grupo monagillo) {
        monagillo = saveOrMerge(monagillo);
        putSuccess("monagillo.success.save", monagillo.getId());
        return monagillo;
    }

    @Override
    public void deleteGrupo(Grupo monagillo) {
        Long id = monagillo.getId();
        delete(monagillo);
        putSuccess("monagillo.success.delete",id);
    }
    
    //
    // Monaguillo
    //
    @Override
    public List<Monaguillo> findMonagilloList(Monaguillo monagillo, Pagination pagination) {
        GenericCriteria<Monaguillo> criteria = GenericCriteria.forClass(Monaguillo.class);
        
        criteria.addEqualsIfNotZero(Monagillo_.id, monagillo.getId());
        if(criteria.isChanged()){
        	return findByCriteria(criteria, pagination);
        }
        
//        criteria.addEqualsIfNotNull(Monagillo_.tipo, monagillo.getTipo());
//        criteria.addEqualsIfNotNull(Monagillo_.naturaleza, monagillo.getNaturaleza());
//        criteria.addLikeIfNotNull(Monagillo_.codigo, monagillo.getCodigo());
//        criteria.addLikeIfNotNull(Monagillo_.descripcion, monagillo.getDescripcion());        
        
        return findByCriteria(criteria, pagination);
    }

    @Override
    public Monaguillo createMonagillo() {
        Monaguillo monagillo = new Monaguillo();
        return monagillo;
    }

    @Override
    public Monaguillo saveMonagillo(Monaguillo monagillo) {
        monagillo = saveOrMerge(monagillo);
        putSuccess("monagillo.success.save", monagillo.getId());
        return monagillo;
    }

    @Override
    public void deleteMonagillo(Monaguillo monagillo) {
        Long id = monagillo.getId();
        delete(monagillo);
        putSuccess("monagillo.success.delete",id);
    }
}
