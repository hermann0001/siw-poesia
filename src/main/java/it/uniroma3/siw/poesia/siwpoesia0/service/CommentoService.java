package it.uniroma3.siw.poesia.siwpoesia0.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.CommentoRepository;
import jakarta.transaction.Transactional;

@Service
public class CommentoService {
	/*
	@Autowired
	private CommentoRepository reviewRepository;
	
	public Commento findReviewById(Long id) {
		return this.reviewRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public void removePoesiaAssociationFromCommento(Poesia poesia) {
		List<Commento> reviewToDelete= this.reviewRepository.findAllByPoesia(poesia);
		for(Commento review:reviewToDelete) {
			review.setPoesia(null);
			this.reviewRepository.delete(review);
		}
	}
	
	@Transactional
	public void setPoesiaToCommento(Commento review, Poesia poesia) {
		review.setPoesia(poesia);
		this.reviewRepository.save(review);
	}
	public boolean isTextLengthOverMax(Commento review) {
		return review.getText()!=null && review.getText().length()>Commento.getMaxLenghtText();
	}

	public void setUser(Commento commento, Autore user) {
		commento.setUser(user);
		this.reviewRepository.save(commento);		
	}

	public void delete(Long idReview) {
		Commento commento= this.findReviewById(idReview);
		this.reviewRepository.delete(commento);
		
	}
*/
}
