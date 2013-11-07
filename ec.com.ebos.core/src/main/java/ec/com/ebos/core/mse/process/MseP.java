package ec.com.ebos.core.mse.process;

import java.util.List;

import ec.com.ebos.core.mse.model.Grupo;
import ec.com.ebos.core.mse.model.Monaguillo;
import ec.com.ebos.core.mse.model.MonaguilloGrupo;
import ec.com.ebos.core.orm.crud.Pagination;

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
	public List<Monaguillo> findMonaguilloList(Monaguillo monaguillo, Pagination pagination);
	
	public List<Monaguillo> findMonaguilloList(String query);
	
	public List<MonaguilloGrupo> getMonaguilloGrupoList(Grupo grupo);
	
	public Monaguillo createMonaguillo();
	
	public Monaguillo saveMonaguillo(Monaguillo monaguillo);
	
	public void deleteMonaguillo(Monaguillo monaguillo);

	public MonaguilloGrupo createMonaguilloGrupo();

	public void saveMonaguilloGrupo(MonaguilloGrupo monaguilloGrupo);

	public void deleteMonaguilloGrupo(MonaguilloGrupo monaguilloGrupo);


}
