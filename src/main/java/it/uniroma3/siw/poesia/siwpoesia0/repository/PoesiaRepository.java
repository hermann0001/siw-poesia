package it.uniroma3.siw.poesia.siwpoesia0.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;

public interface PoesiaRepository extends CrudRepository<Poesia, Long>{
	
	public List<Poesia> findByTitolo(String titolo);
	public boolean existsByTestoAndAutore(String Testo, Autore autore);

}
