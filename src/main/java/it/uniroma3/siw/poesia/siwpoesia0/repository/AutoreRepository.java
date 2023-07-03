package it.uniroma3.siw.poesia.siwpoesia0.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;

public interface AutoreRepository extends CrudRepository<Autore, Long>{
	
	public List<Autore> findByEmail(String Email);
	public boolean existsByEmail(String email);
	public List<Autore> findAllByCommentiIsContaining(Commento commento);
	public List<Autore> findAllByPoesieIsContaining(Poesia poesia);
	
	@Query(value="SELECT * FROM autore WHERE autore.id = (SELECT autore_id FROM credentials WHERE username='Er Bestia')", nativeQuery=true)
	public Autore findErBestia();

	@Query(value="SELECT * FROM autore WHERE autore.id = (SELECT autore_id FROM credentials WHERE username='Er Quercia')", nativeQuery=true)
	public Autore findErQuercia();

	@Query(value="SELECT * FROM autore WHERE autore.id = (SELECT autore_id FROM credentials WHERE username='Marta der Terzo Lotto')", nativeQuery=true)
	public Autore findMarta();

	@Query(value="SELECT * FROM autore WHERE autore.id = (SELECT autore_id FROM credentials WHERE username='Sara G.')", nativeQuery=true)
	public Autore findSara();

	@Query(value="SELECT * FROM autore WHERE autore.id = (SELECT autore_id FROM credentials WHERE username='Er Farco')", nativeQuery=true)
	public Autore findErFarco();

	@Query(value="SELECT * FROM autore WHERE autore.id = (SELECT autore_id FROM credentials WHERE username='Inumi Laconico')", nativeQuery=true)
	public Autore findInumi();

}
