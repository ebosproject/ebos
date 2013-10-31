package ec.com.ebos.mse.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.mse.core.process.MseP;
import ec.com.ebos.mse.model.Grupo;
import ec.com.ebos.mse.model.MonaguilloGrupo;
import ec.com.ebos.mse.model.hibernate.HibernateGrupo;
import ec.com.ebos.mse.model.hibernate.HibernateMonaguillo;
import ec.com.ebos.mse.model.hibernate.HibernateMonaguilloGrupo;
import ec.com.ebos.orm.crud.Pagination;

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
	public List<HibernateGrupo> findGrupoList(HibernateGrupo grupo, Pagination pagination) {
		return mseP.findGrupoList(grupo, pagination);
	}

	@Override
	public HibernateGrupo createGrupo() {
		return mseP.createGrupo();
	}

	@Override
	public HibernateGrupo saveGrupo(HibernateGrupo grupo) {
		return mseP.saveGrupo(grupo);
	}

	@Override
	public void deleteGrupo(HibernateGrupo grupo) {
		mseP.deleteGrupo(grupo);
	}

	//
    // Monaguillo
    //
	@Override
	public List<HibernateMonaguillo> findMonaguilloList(HibernateMonaguillo monagillo,
			Pagination pagination) {
		return mseP.findMonaguilloList(monagillo, pagination);
	}
	
	public List<HibernateMonaguillo> findMonaguilloList(String query){
		return mseP.findMonaguilloList(query);
	}
	
	@Override
	public List<HibernateMonaguilloGrupo> getMonaguilloGrupoList(Grupo grupo){
		return mseP.getMonaguilloGrupoList(grupo);
	}

	@Override
	public HibernateMonaguillo createMonaguillo() {
		return mseP.createMonaguillo();
	}

	@Override
	public HibernateMonaguillo saveMonaguillo(HibernateMonaguillo monagillo) {
		return mseP.saveMonaguillo(monagillo);
	}

	@Override
	public void deleteMonaguillo(HibernateMonaguillo monaguillo) {
		mseP.deleteMonaguillo(monaguillo);
	}

	@Override
	public MonaguilloGrupo createMonaguilloGrupo(){
		return mseP.createMonaguilloGrupo();
	}
	
	@Override
	public void saveMonaguilloGrupo(HibernateMonaguilloGrupo monaguilloGrupo){
		mseP.saveMonaguilloGrupo(monaguilloGrupo);
	}
	
	@Override
	public void deleteMonaguilloGrupo(HibernateMonaguilloGrupo monaguilloGrupo){
		mseP.deleteMonaguilloGrupo(monaguilloGrupo);
	}
	
}
