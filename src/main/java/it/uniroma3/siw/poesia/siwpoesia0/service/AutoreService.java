package it.uniroma3.siw.poesia.siwpoesia0.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Credenziale;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.AutoreRepository;
import it.uniroma3.siw.poesia.siwpoesia0.repository.CommentoRepository;
import jakarta.transaction.Transactional;

@Service
public class AutoreService {
	@Autowired private AutoreRepository userRepository;
	@Autowired private CredenzialeService credentialsService;
	@Autowired private CommentoRepository reviewRepository;
	

	@Transactional
	public void saveAutore(Autore user) {
		this.userRepository.save(user);
	}
	
	public void addCommento(Autore user, Commento commento) {
		user.getCommenti().add(commento);
		this.userRepository.save(user);		
	}
	public Autore getCurrentUser() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credenziale credentials = credentialsService.getCredentials(userDetails.getUsername());
		return credentials.getAutore();
	}
	
	public boolean multipleCommentofPeasiaBySameUser(Poesia poesia){
		Autore autore=this.getCurrentUser();
		return this.reviewRepository.existsByAutoreAndPoesia(autore, poesia);
	}
	
	@Transactional
	public void removeCommentoAsscociationFromUser(Commento commento) {
		Autore autore=this.userRepository.findAllByCommentiIsContaining(commento).get(0);
		autore.getCommenti().remove(commento);
		this.userRepository.save(autore);
	}
}
