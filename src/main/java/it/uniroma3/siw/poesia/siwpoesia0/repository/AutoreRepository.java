package it.uniroma3.siw.poesia.siwpoesia0.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;

public interface AutoreRepository extends CrudRepository<Autore, Long>{
	
	public List<Autore> findByNome(String nome);
	public boolean existsByNome(String nome);

}
