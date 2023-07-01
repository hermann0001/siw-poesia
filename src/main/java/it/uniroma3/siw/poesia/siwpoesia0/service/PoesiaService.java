package it.uniroma3.siw.poesia.siwpoesia0.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.PoesiaRepository;

public class PoesiaService {
	
	@Autowired
	PoesiaRepository poesiaRepository;
	
	public void newPoesia(Poesia poesia, MultipartFile immagine, Model model) {
		try {
			String base64Image= Base64.getEncoder().encodeToString(immagine.getBytes());
			poesia.setFoto(base64Image);
			this.savePoesia(poesia);
		} catch(IOException e ) {}
	}

	private Poesia savePoesia(Poesia poesia) {
		return this.poesiaRepository.save(poesia);
		
	}
	

}
