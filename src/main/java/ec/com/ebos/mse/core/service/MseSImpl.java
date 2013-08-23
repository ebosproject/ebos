package ec.com.ebos.mse.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.mse.core.process.MseP;
import ec.com.ebos.mse.model.Grupo;
import ec.com.ebos.mse.model.Monaguillo;
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

	//
    // Monaguillo
    //
	@Override
	public List<Monaguillo> findMonagilloList(Monaguillo monagillo,
			Pagination pagination) {
		return mseP.findMonagilloList(monagillo, pagination);
	}

	@Override
	public Monaguillo createMonagillo() {
		return mseP.createMonagillo();
	}

	@Override
	public Monaguillo saveMonagillo(Monaguillo monagillo) {
		return mseP.saveMonagillo(monagillo);
	}

	@Override
	public void deleteMonagillo(Monaguillo monagillo) {
		mseP.deleteMonagillo(monagillo);
	}

	
	
}
