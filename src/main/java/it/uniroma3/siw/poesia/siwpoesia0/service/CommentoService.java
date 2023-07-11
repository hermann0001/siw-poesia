package it.uniroma3.siw.poesia.siwpoesia0.service;

import java.util.List;


import it.uniroma3.siw.poesia.siwpoesia0.repository.AutoreRepository;
import it.uniroma3.siw.poesia.siwpoesia0.repository.PoesiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.CommentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.ui.Model;

@Service
public class CommentoService {

    @Autowired
    CommentoRepository commentoRepository;

    @Autowired
    PoesiaRepository poesiaRepository;

    @Autowired
    AutoreRepository autoreRepository;

    @Transactional
    public void newCommento(Commento commento, Long idA, Long idP, Model model) {
        Poesia poesia = this.poesiaRepository.findById(idP).get();
        Autore autore = this.autoreRepository.findById(idA).get();


        if(poesia != null && autore != null) {

            commento.setAutore(autore);
            commento.setPoesia(poesia);
            poesia.getCommenti().add(commento);
            autore.getCommenti().add(commento);

            this.commentoRepository.save(commento);
            this.poesiaRepository.save(poesia);
            this.autoreRepository.save(autore);
        }
    }
    @Transactional
    public Commento deleteCommento(Long idC, Long idP) {
        Commento commento = this.commentoRepository.findById(idC).orElse(null);
        Poesia poesia = this.poesiaRepository.findById(idP).orElse(null);

        if(commento != null && poesia!=null) {
            poesia.getCommenti().remove(commento);
            commento.getAutore().getCommenti().remove(commento);

            this.commentoRepository.delete(commento);
            this.poesiaRepository.save(poesia);
            this.autoreRepository.save(commento.getAutore());
        }
        return commento;
    }

    @Transactional
    public List<Commento> getUltimiCommenti() {
        return this.commentoRepository.findPrimiQuattroCommenti();
    }

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
