package it.uniroma3.siw.poesia.siwpoesia0.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


import it.uniroma3.siw.poesia.siwpoesia0.repository.PoesiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.AutoreRepository;
import jakarta.transaction.Transactional;

@Service
public class PoesiaService {
	
	@Autowired
	PoesiaRepository poesiaRepository;
	@Autowired
	AutoreRepository autoreRepository;
	
	public void newPoesia(Poesia poesia, MultipartFile immagine, Model model) {
		try {
			String base64Image= Base64.getEncoder().encodeToString(immagine.getBytes());
			poesia.setFoto(base64Image);
			this.savePoesia(poesia);
		} catch(IOException e ) {}
	}

	public Poesia savePoesia(Poesia poesia) {
		return this.poesiaRepository.save(poesia);
		
	}
	


	public Poesia findPoesiaById(Long id) {
		return this.poesiaRepository.findById(id).orElse(null);
	}

	public Iterable<Poesia> findAllPoesia(){
		return this.poesiaRepository.findAll();
	}

	public Poesia saveAutoreToPoesia(Long idPoesia, Long idAutore) {
		Poesia poesia= this.findPoesiaById(idPoesia);
		Autore autore= this.autoreRepository.findById(idAutore).orElse(null);
		if(poesia!=null && autore!=null) {
			poesia.setAutore(autore);
			autore.getPoesie().add(poesia);
			autoreRepository.save(autore);
			this.savePoesia(poesia);
			return poesia;
		}
		return null;
	}

	public List<Poesia> findByTitolo(String titolo) {
		return this.poesiaRepository.findByTitolo(titolo);
	}

	public void addCommentoToPoesia(Poesia poesia, Commento commento) {
		poesia.getCommenti().add(commento);
		this.savePoesia(poesia);
	}
	
	public void removeAutoreAssociationFromPoesia(Long idMPoesia) {
		Poesia poesia=this.poesiaRepository.findById(idMPoesia).get();
		poesia.setAutore(null);
		this.poesiaRepository.save(poesia);
	}
	
	public void delete(Long idMPoesia) {
		Poesia poesia= this.poesiaRepository.findById(idMPoesia).get();
		this.poesiaRepository.delete(poesia);
	}
	
	@Transactional 
	public void removeAutoreAssociationFromAllPoesie(Long idaAutore) {
		Autore autore= this.autoreRepository.findById(idaAutore).get();
		List<Poesia> poesie=this.poesiaRepository.findAllByAutoreOrderByDataPubblicazioneDesc(autore);
		for(Poesia poesia :poesie) {
			poesia.setAutore(null);;
			this.poesiaRepository.save(poesia);
		}
	}

	public void removeCommentoAssociationFromPoesia(Commento commento) {
		Poesia poesia= this.poesiaRepository.findAllByCommentiIsContaining(commento).get(0);
		poesia.getCommenti().remove(commento);
		this.poesiaRepository.save(poesia);	
	}

	
	@Transactional 
	public Poesia update(Long idPoesia, Poesia newPoesia, MultipartFile image) {
		Poesia poesia = this.poesiaRepository.findById(idPoesia).get();
		poesia.setTitolo(newPoesia.getTitolo());
		poesia.setTesto(newPoesia.getTesto());
		poesia.setDataPubblicazione(newPoesia.getDataPubblicazione());
		
		if(!image.isEmpty()) {			
			try {
				String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
				poesia.setFoto(base64Image);
				} catch(IOException e) {}
			this.poesiaRepository.save(poesia);
		}
		return poesia;
	}
	

	@Transactional
	public List<Poesia> getUltimePoesie() {
		return this.poesiaRepository.findPrimeQuattroPoesie();
	}

	@Transactional
	public List<Poesia> getUltimePoesieDiAutore(Autore autore){
		return this.poesiaRepository.findAllByAutoreOrderByDataPubblicazioneDesc(autore);
	}


	public boolean alreadyExists(Poesia poesia) {
		return poesia.getAutore()!=null && poesia.getTitolo()!=null && this.poesiaRepository.existsByTitoloAndAutore(poesia.getTitolo(), poesia.getAutore());
	}
	
}
