package it.uniroma3.siw.poesia.siwpoesia0.service;

import java.io.IOException;
import java.util.List;


import it.uniroma3.siw.poesia.siwpoesia0.repository.PoesiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	@Autowired
	ImmagineService immagineService;

	@Transactional
	public Poesia savePoesia(Poesia poesia) {
		return this.poesiaRepository.save(poesia);
	}

	/*
	Faccio l'overload della funzione savePoesia!
	Se aggiungiPoesia(poesia, file) solleva una IOException la rimandiamo al controller!
	 */
	@Transactional
	public Poesia savePoesia(Poesia poesia, MultipartFile file) throws IOException{
		if(!file.isEmpty()) this.aggiungiImmaginePoesia(poesia, file);						//aggiungi l'immagine solo se la foto non è null
		return this.poesiaRepository.save(poesia);
	}

	//da valutare se in effetti questo ping pong di metodi appesantisce il codice o potrebbe essere utile per riuso (esempio in possibile modifica immagine poesia)
	@Transactional
	public void aggiungiImmaginePoesia(Poesia poesia, MultipartFile file) throws IOException{
		poesia.setFoto(this.immagineService.saveImmagine(file));
	}

	@Transactional
	public Poesia find(Long id) {
		return this.poesiaRepository.findById(id).orElse(null);
	}

	public List<Poesia> findByTitolo(String titolo) {
		return this.poesiaRepository.findByTitolo(titolo);
	}

	@Transactional
	public Poesia updatePoesia(Long id, Poesia newPoesia, MultipartFile newFoto) throws IOException {
		Poesia oldPoesia=this.poesiaRepository.findById(id).get();
		oldPoesia.setTitolo(newPoesia.getTitolo());
		oldPoesia.setTesto(newPoesia.getTesto());
		oldPoesia.setDataPubblicazione(newPoesia.getDataPubblicazione());
		return this.savePoesia(oldPoesia, newFoto);
	}

	@Transactional
	public void deletePoesia(Long id) {
		Poesia poesia= this.poesiaRepository.findById(id).get();
		poesia.getAutore().getPoesie().remove(poesia);
		autoreRepository.save(poesia.getAutore());

		for(Commento commento : poesia.getCommenti()) {
			commento.getAutore().getCommenti().remove(commento);
		}
		poesiaRepository.delete(poesia);
	}
	
/*	@Transactional
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
	}*/
	

	@Transactional
	public List<Poesia> getUltimePoesie() {
		return this.poesiaRepository.findPrimeQuattroPoesie();
	}

	@Transactional
	public List<Poesia> getUltimePoesieDiAutore(Autore autore){
		return this.poesiaRepository.findAllByAutoreOrderByDataPubblicazioneDesc(autore);
	}

	@Transactional
	public boolean alreadyExists(Poesia poesia) {
		return poesia.getAutore()!=null && poesia.getTitolo()!=null && this.poesiaRepository.existsByTitoloAndAutore(poesia.getTitolo(), poesia.getAutore());
	}
	@Transactional
	public Poesia deleteImmagine(Long idP) {
		Poesia poesia = this.poesiaRepository.findById(idP).orElse(null);
		this.immagineService. deleteImmagine(poesia.getFoto());
		poesia.setFoto(null);
		return this.savePoesia(poesia);
	}
}
