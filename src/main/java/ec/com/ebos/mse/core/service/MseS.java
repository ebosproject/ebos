package ec.com.ebos.mse.core.service;

import java.io.Serializable;
import java.util.List;

import ec.com.ebos.mse.model.Grupo;
import ec.com.ebos.mse.model.Monaguillo;
import ec.com.ebos.mse.model.MonaguilloGrupo;
import ec.com.ebos.orm.crud.Pagination;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-08-23
 */
public interface MseS extends Serializable {
    
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
	
	public List<MonaguilloGrupo> getMonaguilloGrupoList();
	
	public Monaguillo createMonaguillo();

	public Monaguillo saveMonaguillo(Monaguillo monaguillo);
	
	public void deleteMonaguillo(Monaguillo monaguillo);

	public MonaguilloGrupo createMonaguilloGrupo();
		
	public void saveMonaguilloGrupo(MonaguilloGrupo monaguilloGrupo);

	public void deleteMonaguilloGrupo(MonaguilloGrupo monaguilloGrupo);

	
}
