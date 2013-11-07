package ec.com.ebos.hibernate.mse.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.core.mse.model.Grupo;
import ec.com.ebos.core.mse.model.Monaguillo;
import ec.com.ebos.core.mse.model.MonaguilloGrupo;
import ec.com.ebos.core.mse.process.MseP;
import ec.com.ebos.core.mse.service.MseS;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.hibernate.mse.model.HibernateGrupo;
import ec.com.ebos.hibernate.mse.model.HibernateMonaguillo;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-05-21
 */
@Service("mseS")
public class MseSImpl implements MseS{

	private static final long serialVersionUID = -4681916054169595153L;
	
	@Getter @Setter
    @Autowired
    @Qualifier("mseP")
    private MseP mseP;

	//
    // Grupo
    //
	@Override
	public List<Grupo> findGrupoList(Grupo grupo, Pagination pagination) {
		return mseP.findGrupoList(grupo, pagination);
	}

	@Override
	public Grupo createGrupo() {
		return mseP.createGrupo();
	}

	@Override
	public Grupo saveGrupo(Grupo grupo) {
		return mseP.saveGrupo(grupo);
	}

	@Override
	public void deleteGrupo(Grupo grupo) {
		mseP.deleteGrupo(grupo);
	}
	
	public Grupo getInstanceGrupo(){
		return new HibernateGrupo();
	}

	//
    // Monaguillo
    //
	@Override
	public List<Monaguillo> findMonaguilloList(Monaguillo monagillo,
			Pagination pagination) {
		return mseP.findMonaguilloList(monagillo, pagination);
	}
	
	public List<Monaguillo> findMonaguilloList(String query){
		return mseP.findMonaguilloList(query);
	}
	
	@Override
	public List<MonaguilloGrupo> getMonaguilloGrupoList(Grupo grupo){
		return mseP.getMonaguilloGrupoList(grupo);
	}

	@Override
	public Monaguillo createMonaguillo() {
		return mseP.createMonaguillo();
	}

	@Override
	public Monaguillo saveMonaguillo(Monaguillo monagillo) {
		return mseP.saveMonaguillo(monagillo);
	}

	@Override
	public void deleteMonaguillo(Monaguillo monaguillo) {
		mseP.deleteMonaguillo(monaguillo);
	}

	@Override
	public MonaguilloGrupo createMonaguilloGrupo(){
		return mseP.createMonaguilloGrupo();
	}
	
	@Override
	public void saveMonaguilloGrupo(MonaguilloGrupo monaguilloGrupo){
		mseP.saveMonaguilloGrupo(monaguilloGrupo);
	}
	
	@Override
	public void deleteMonaguilloGrupo(MonaguilloGrupo monaguilloGrupo){
		mseP.deleteMonaguilloGrupo(monaguilloGrupo);
	}
	
	public Monaguillo getInstanceMonaguillo(){
		return new HibernateMonaguillo();
	}
	
}
