package it.uniroma3.siw.poesia.siwpoesia0.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import org.springframework.stereotype.Repository;

@Repository
public interface CredenzialeRepository extends CrudRepository<Credentials, Long>{
	
	public Optional<Credentials> findByUsername(String username);
	public boolean existsByUsername(String username);
	public List<Credentials> findAllByRole(String role);
	@Query(value="select username from credentials where autore_id = :idAutore", nativeQuery=true)
    public  String findUsernameFromAutore(Long idAutore);
}
