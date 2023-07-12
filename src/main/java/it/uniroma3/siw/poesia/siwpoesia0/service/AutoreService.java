package it.uniroma3.siw.poesia.siwpoesia0.service;



import java.io.IOException;

import it.uniroma3.siw.poesia.siwpoesia0.repository.AutoreRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;

import jakarta.transaction.Transactional;

@Service
public class AutoreService {
	@Autowired
	private AutoreRepository autoreRepository;
	@Autowired
	private ImmagineService immagineService;

	@Transactional
	public Autore find(Long id) { return this.autoreRepository.findById(id).orElse(null); }

	@Transactional
	public Autore saveAutore(Autore user) {
		return this.autoreRepository.save(user);
	}

	//qui niente ping pong di chiamate (PoesiaService.java :: line 49)
	@Transactional
	public Autore saveAutore(Autore user, MultipartFile file) throws IOException {
		if(!file.isEmpty()) user.setFoto(this.immagineService.saveImmagine(file));
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

	@Transactional
	public void deleteImmagine(Long idA) {
		Autore autore = this.autoreRepository.findById(idA).orElse(null);
		this.immagineService.deleteImmagine(autore.getFoto());
		autore.setFoto(null);
		this.saveAutore(autore);
	}
	@Transactional
	public Autore updateAutore(Long id, Autore newAutore, MultipartFile newFoto) throws IOException {
		Autore oldAutore=this.find(id);
		oldAutore.setNome(newAutore.getNome());
		oldAutore.setCognome(newAutore.getCognome());
		oldAutore.setEmail(newAutore.getEmail());
		this.immagineService.deleteImmagine(oldAutore.getFoto()); //bisogna cancellarla sennò perdiamo il riferimento all'immagine!
		return this.saveAutore(oldAutore, newFoto);
	}
}
