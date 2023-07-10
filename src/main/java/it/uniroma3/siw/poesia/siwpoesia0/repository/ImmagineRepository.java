package it.uniroma3.siw.poesia.siwpoesia0.repository;

import it.uniroma3.siw.poesia.siwpoesia0.model.Immagine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImmagineRepository extends CrudRepository<Immagine, Long> {
    Optional<Immagine> findByNome(String nome);
}
