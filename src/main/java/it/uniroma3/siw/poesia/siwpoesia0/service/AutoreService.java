package it.uniroma3.siw.poesia.siwpoesia0.service;



import java.io.IOException;
import java.util.Base64;
import java.util.List;

import it.uniroma3.siw.poesia.siwpoesia0.repository.AutoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.CommentoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class AutoreService {
	@Autowired
	private AutoreRepository autoreRepository;
	@Autowired
	private ImmagineService immagineService;

	@Transactional
	public Autore saveAutore(Autore user) {
		return this.autoreRepository.save(user);
	}

	//qui niente ping pong di chiamate (PoesiaService.java :: line 49)
	@Transactional
	public Autore saveAutore(Autore user, MultipartFile file) throws IOException {
		user.setFoto(this.immagineService.saveImmagine(file));
		return this.autoreRepository.save(user);
	}

	public Iterable<Autore> findAllAutori(){
		return this.autoreRepository.findAll();
	}

	public void delete(Long idAutore) {
		Autore artist = this.autoreRepository.findById(idAutore).get();
		this.autoreRepository.delete(artist);
	}

	public boolean alreadyExists(Autore autore){
		return autore.getEmail() != null && this.autoreRepository.existsByEmail(autore.getEmail());
	}
}
