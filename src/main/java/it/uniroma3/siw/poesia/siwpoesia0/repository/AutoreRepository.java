package it.uniroma3.siw.poesia.siwpoesia0.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;

public interface AutoreRepository extends CrudRepository<Autore, Long>{
	
	public List<Autore> findByUsername(String username);
	public boolean existsByUsername(String username);
	public List<Autore> findAllByCommentiIsContaining(Commento commento);

}
