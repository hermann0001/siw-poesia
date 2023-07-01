package it.uniroma3.siw.poesia.siwpoesia0.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credenziale;

public interface CredenzialeRepository extends CrudRepository<Credenziale, Long>{
	
	public Optional<Credenziale> findByUsername(String username);

}
