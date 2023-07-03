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
	@Autowired private AutoreRepository autoreRepository;
	@Autowired private CredenzialeService credenzialeService;
	@Autowired private CommentoRepository commentoRepository;
	

	@Transactional
	public void saveAutore(Autore user) {
		this.autoreRepository.save(user);
	}
	
	public void addCommento(Autore user, Commento commento) {
		user.getCommenti().add(commento);
		this.autoreRepository.save(user);
	}
	public Autore getCurrentUser() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credenzialeService.getCredentials(userDetails.getUsername());
		return credentials.getAutore();
	}
	
	public boolean multipleCommentofPeasiaBySameUser(Poesia poesia){
		Autore autore=this.getCurrentUser();
		return this.commentoRepository.existsByAutoreAndPoesia(autore, poesia);
	}
	
	@Transactional
	public void removeCommentoAsscociationFromUser(Commento commento) {
		Autore autore=this.autoreRepository.findAllByCommentiIsContaining(commento).get(0);
		autore.getCommenti().remove(commento);
		this.autoreRepository.save(autore);
	}
	/*--------------------------------------------------------------------------------------------*/

	public Iterable<Autore> findAllAutori(){
		return this.autoreRepository.findAll();
	}
	
	public Autore findAutoreById(Long idAutore) {
		return this.autoreRepository.findById(idAutore).orElse(null);
	}
	
	public void delete(Long idAutore) {
		Autore artist= this.autoreRepository.findById(idAutore).get();
		this.autoreRepository.delete(artist);
	}

	public void saveArtist(@Valid Autore autore) {
		this.autoreRepository.save(autore);
	}
	
	@Transactional
	public void createArtist(Autore autore, MultipartFile image) {
		try {
			String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
			autore.setUrl_foto(base64Image);
			this.saveArtist(autore);
			} catch(IOException e) {}
	}
	//TODO: BOH?
	/*public Autore update(Long idAutore, Autore newAutore, MultipartFile image) {
		Autore autore= this.autoreRepository.findById(idAutore).get();
		autore.setUsername(newAutore.getUsername());
		autore.setEmail(newAutore.getEmail());
		if(!image.isEmpty()) {
			try {
				String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
				autore.setUrl_foto(base64Image);
				} catch(IOException e) {}
		}
		this.autoreRepository.save(autore);
		return autore;
	}*/
	
	@Transactional
	public void removeMovieAssociationFromAllActor(Poesia poesia) {
		List<Autore> autore=this.autoreRepository.findAllByPoesieIsContaining(poesia);
		for(Autore actor:autore) {
			actor.getPoesie().remove(poesia);
			this.autoreRepository.save(actor);
		}
	}

	public boolean alreadyExists(Autore autore){
		return autore.getEmail() != null && this.autoreRepository.existsByEmail(autore.getEmail());
	}
}
