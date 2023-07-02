package it.uniroma3.siw.poesia.siwpoesia0.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;

public interface PoesiaRepository extends CrudRepository<Poesia, Long>{
	
	public List<Poesia> findByTitolo(String titolo);
	public boolean existsByTestoAndAutore(String Testo, Autore autore);
	public List<Poesia> findAllByAutore(Autore autore);
	public List<Poesia> findAllByCommentiIsContaining(Commento commento);
	
	
	@Query(value="select top(4) from poesia order by data_pubblicazione", nativeQuery=true)
	public List<Poesia> findPrimeQuattroPoesie();

}
