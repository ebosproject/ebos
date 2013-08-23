package ec.com.ebos.mse.core.process;

import java.util.List;

import ec.com.ebos.mse.model.Grupo;
import ec.com.ebos.mse.model.Monaguillo;
import ec.com.ebos.orm.crud.Pagination;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
public interface MseP {

	//
	// Grupo
	//
	public List<Grupo> findGrupoList(Grupo grupo, Pagination pagination);
	
	public Grupo createGrupo();
	
	public Grupo saveGrupo(Grupo grupo);
	
	public void deleteGrupo(Grupo grupo);
	
	//
	// Monagillo
	//
	public List<Monaguillo> findMonagilloList(Monaguillo monagillo, Pagination pagination);
	
	public Monaguillo createMonagillo();
	
	public Monaguillo saveMonagillo(Monaguillo monagillo);
	
	public void deleteMonagillo(Monaguillo monagillo);
}