package it.uniroma3.siw.poesia.siwpoesia0.controller;

import java.io.IOException;

import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.AutoreValidator;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.PoesiaValidator;
import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credenziale;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.PoesiaRepository;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;
import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.PoesiaValidator;
import jakarta.validation.Valid;

@Controller
public class PoesiaController {
	
	@Autowired 
	PoesiaRepository poesiaRepository;
	
	@Autowired
	PoesiaValidator poesiaValidator;
	
	@Autowired
	PoesiaService poesiaService;
	
	@Autowired
	CredenzialeService credenzialeService;

	@GetMapping(value="/autore/formNewPoesia") 
	public String formNewPoesia(Model model) { 
		model.addAttribute("poesia", new Poesia());
		return "admin/formNewPoesia.html";
	}
	
	@PostMapping("/autore/poesia") 
	public String newPoesia(@Valid @ModelAttribute("poesia") Poesia poesia, BindingResult bindingResult, Model model, MultipartFile file) throws IOException { 
		this.poesiaValidator.validate(poesia, bindingResult);
		UserDetails userDetails =  (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziale credentials = credenzialeService.getCredentials(userDetails.getUsername());

		if (!bindingResult.hasErrors()) { 
			this.poesiaService.newPoesia(poesia, file, model);
			model.addAttribute("poesia", poesia); 
			poesia.setAutore(credentials.getAutore());
			return "poesia.html";
		} else { 
			return "admin/formNewPoesia.html";
		}
	}
}
