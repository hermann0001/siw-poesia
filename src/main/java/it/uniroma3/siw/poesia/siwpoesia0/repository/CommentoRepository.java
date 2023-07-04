package it.uniroma3.siw.poesia.siwpoesia0.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;

public interface CommentoRepository extends CrudRepository<Commento, Long>{
	public boolean existsByPoesiaAndText(Poesia poesia, String text);
	public List<Commento> findAllByPoesia(Poesia poesia);
	public Boolean  existsByAutoreAndPoesia(Autore credentials,Poesia poesia);
}
