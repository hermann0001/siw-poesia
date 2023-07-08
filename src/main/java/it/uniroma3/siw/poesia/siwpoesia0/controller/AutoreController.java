package it.uniroma3.siw.poesia.siwpoesia0.controller;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.AutoreValidator;
import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;
import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class AutoreController {
	
	@Autowired
	AutoreService autoreService;
	@Autowired
	AutoreValidator autoreValidator;
	@Autowired
	PoesiaService poesiaService;
	@Autowired
	CredenzialeService credentialsService;
	
	@GetMapping("/autori/{id}")
	public String getAutore(@PathVariable("id") Long id, Model model) {
		Credentials credentials =this.credentialsService.findCredentials(id);
		String username = credentials.getUsername();
		Autore autore = credentials.getAutore();
		model.addAttribute("autore" , autore);
		model.addAttribute("username", username);
		model.addAttribute("ultimePoesie", this.poesiaService.getUltimePoesieDiAutore(autore));
		return "/autore/autore";
	}
	
	@GetMapping("/admin/formNewAutore")
	public String formNewAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "/admin/formNewAutore";
	}
	
	@PostMapping("/autore/autore")
	public String newAutore(@Valid @ModelAttribute("autore") Autore autore, BindingResult bindingResult,
			MultipartFile image, Model model) {
		this.autoreValidator.validate(autore, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.autoreService.createArtist(autore, image);
			model.addAttribute("autore", autore);
			return "autore/autore";
		}
		else {
			return "admin/formNewAutore";
		}
	}
/*
	@GetMapping("/autore/formUpdateAutore/{idAutore}")
	public String formUpdateAutore(@PathVariable("idAutore") Long idAutore, Model model) {
		Autore autore=this.autoreService.findAutoreById(idAutore);
		model.addAttribute("autore",autore);
		return "admin/formUpdateAutore";
	}*/
/*
	TODO: A COSA SERVE QUESTO?
	@GetMapping("/autore/formUpdateAutoreData/{idAutore}")
	public String formUpdateArtistData(@PathVariable("idAutore") Long idAutore, Model model) {
		Autore autore=this.autoreService.findAutoreById(idAutore);
		model.addAttribute("autore",autore);
		return "autore/formUpdateAutoreData.html";
	}*/

	//TODO: BOH?
	/*@PostMapping("/autore/updateAutoretData/{idAutore}")
	public String updateArtistData(@PathVariable("idAutore") Long idAutore, 
			@Valid @ModelAttribute("autore") Autore newAutore, BindingResult bindingResult,
			MultipartFile image, Model model) {
		this.autoreValidator.validate(newAutore(newAutore, bindingResult, image, model), bindingResult);
		if(!bindingResult.hasErrors()) {
			model.addAttribute("artist", this.autoreService.update(idAutore, newAutore, image));
			return "autore/formUpdateArtist.html";
		}
		else {
			return "autore/formUpdateArtistData.html";
		}
	}*/
	
	@GetMapping("/autori")
	public String showAutori(Model model) {
		model.addAttribute("autori", this.autoreService.findAllAutori());
		return "/autore/autori";
	}
	
	@GetMapping("/admin/manageAutore")
	public String manageAutore(Model model) {
		model.addAttribute("autori", this.autoreService.findAllAutori());
		return "admin/manageAutore.html";
	}
	
	@GetMapping("/admin/formConfirmDeleteAutore/{idAutore}")
	public String formConfirmDeleteArtist(@PathVariable ("idAutore") Long idAutore, Model model) {
		Autore autore=this.autoreService.findAutoreById(idAutore);
		if(autore==null)
			return "generic/autoreError.html";
		model.addAttribute("autore", autore);
		return "/admin/formConfirmDeleteAutore.html";
	}

	//TODO: ME PUZZA...
	@Transactional
	@GetMapping("/admin/deleteAutore/{idAutore}")
	public String deleteAutore(@PathVariable ("idAutore") Long idAutore, Model model) {
		Autore autore=this.autoreService.findAutoreById(idAutore);
		if(autore==null)
			return "generic/artistError.html";
		else {
			this.poesiaService.removeAutoreAssociationFromAllPoesie(idAutore);
			this.autoreService.delete(idAutore);
			model.addAttribute("artists", this.autoreService.findAllAutori());
			return "admin/manageArtist.html";
		}
	}
}