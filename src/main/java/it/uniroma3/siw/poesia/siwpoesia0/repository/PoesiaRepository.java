package it.uniroma3.siw.poesia.siwpoesia0.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PoesiaRepository extends CrudRepository<Poesia, Long>{
	public List<Poesia> findByTitolo(String titolo);

	@Query(value="select * from poesia where titolo like ':%titolo%'", nativeQuery=true)
	public List<Poesia> cercaPoesia(@Param("titolo") String titolo);
	public boolean existsByTitoloAndAutore(String Titolo, Autore autore);
	public List<Poesia> findAllByAutoreOrderByDataPubblicazioneDesc(Autore autore);
	public List<Poesia> findAllByCommentiIsContaining(Commento commento);
	
	
	@Query(value="select * from poesia order by data_pubblicazione DESC limit 6", nativeQuery=true)
	public List<Poesia> findLatestPoesie();

}
