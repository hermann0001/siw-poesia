package it.uniroma3.siw.poesia.siwpoesia0.controller;

import java.io.IOException;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.PoesiaRepository;
import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CommentoService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.PoesiaValidator;

@Controller
public class PoesiaController {
	
	@Autowired
	PoesiaRepository poesiaRepository;
	@Autowired
	PoesiaValidator poesiaValidator;
	@Autowired
	it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService poesiaService;
	@Autowired
	CredenzialeService credenzialeService;
	@Autowired
	AutoreService artistService;
	@Autowired
	CommentoService reviewService;

	@GetMapping(value="/autore/formNewPoesia") 
	public String formNewPoesia(Model model) { 
		model.addAttribute("poesia", new Poesia());
		return "autore/formNewPoesia.html";
	}
	
	@PostMapping("/autore/poesia")
	public String newPoesia(@Valid @ModelAttribute("poesia") Poesia poesia, BindingResult bindingResult, Model model, MultipartFile file) throws IOException {
		UserDetails userDetails =  (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credenzialeService.getCredentials(userDetails.getUsername());
		poesia.setAutore(credentials.getAutore());
		this.poesiaValidator.validate(poesia, bindingResult);

		System.out.println(bindingResult.getAllErrors().toString());

		if (!bindingResult.hasErrors()) {
			System.out.println(poesia);
			this.poesiaService.savePoesia(poesia);
			model.addAttribute("poesia", poesia);
			return "redirect:/poesia/"+poesia.getId();
		} else { 
			return "autore/formNewPoesia.html";
		}
	}
	
	@GetMapping("/autore/managePoesie")
	public String managePoesie(Model model) {
		model.addAttribute("movies", this.poesiaService.findAllPoesia());
		return "autore/managePoesie.html";
	}
	
	@Transactional
	@GetMapping("/autore/formUpdatePoesia/{id}")
	public String formUpdatePoesia(@PathVariable("id") Long id, Model model) {
		Poesia poesia= this.poesiaService.findPoesiaById(id);
		if(poesia!=null) {
			model.addAttribute("poesia",poesia);
		}
		else {
			return "poesiaError.html";
		}
		return "/autore/formUpdatePoesia.html";
	}
	
	/*SERVE???*/
	
	@Transactional
	@GetMapping("/autore/addAutoreToPoesia/{id}")
	public String addAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autori",this.artistService.findAllAutori());
		Poesia poesia= this.poesiaService.findPoesiaById(id);
		if(poesia != null) {
			model.addAttribute("poesia",poesia);
		} else {
			return "poesiaError.html";   
		}
		return "/autore/autoreToAdd.html";
	}
	
	
	/*PURE QUESTO NON SO SE SERVE*/
	
	@Transactional
	@GetMapping("/autore/setAutoreToPoesia/{idAutore}/{idPoesia}")
	public String setAutoreToPoesia(@PathVariable("idAutore") Long idAutore, @PathVariable("idPoesia") Long idPoesia, Model model){
		Poesia poesia=this.poesiaService.saveAutoreToPoesia(idPoesia, idAutore);
		if(poesia!=null) {
			model.addAttribute("poesia", poesia);
			model.addAttribute("autore", poesia.getAutore());
			return "/autore/formUpdatePoesia.html";
		}
		else
			return "poesiaError.html";
	}
	
	
	@GetMapping("/autore/formConfirmDeletePoesia/{idPoesia}")
	public String formConfirmDeletePoesia(@PathVariable("idPoesia") Long idPoesia, Model model) {
		Poesia poesia=this.poesiaService.findPoesiaById(idPoesia);
		if(poesia==null)
			return "generic/poesiaError.html";
		else {
			model.addAttribute("poesia", poesia);
			return "autore/formConfirmDeletePoesia.html";
		}
	}
	
	
	/*@Transactional
	@GetMapping("/admin/deletePoesia/{idPoesia}")
	public String deletePoesia(@PathVariable("idPoesia") Long idPoesia, Model model) {
		Poesia poesia=this.poesiaService.findPoesiaById(idPoesia);
		
		if(poesia!=null) {    
			this.artistService.removeMovieAssociationFromAllActor(poesia);
			this.poesiaService.removeAutoreAssociationFromPoesia(idPoesia);
			this.reviewService.removePoesiaAssociationFromCommento(poesia);
			this.poesiaService.delete(idPoesia);
			model.addAttribute("poesie", this.poesiaService.findAllPoesia());
			return "autore/managePoesie.html";
		} else 
			return "poesiaError.html";
	}	*/
	
	/*SERVE???*/
	
	@GetMapping("/autore/formUpdatePoesiaData/{idPoesia}")
	public String formUpdatePoesiaData(@PathVariable("idPoesia") Long idPoesia, Model model) {
		Poesia poesia=this.poesiaService.findPoesiaById(idPoesia);
		if(poesia==null)
			return "generic/poesiaError.html";
		model.addAttribute("poesia",poesia);
		return "autore/formUpdatePoesiaData.html";
	}
	
	
	@PostMapping("/admin/updatePoesiaData/{idPoesia}")
		public String updatePoesiaData(@PathVariable("idPoesia") Long idPoesia, 
				@Valid @ModelAttribute("poesia") Poesia newPoesia, BindingResult bindingResult,
				MultipartFile image, Model model) {
		this.poesiaValidator.validate(newPoesia, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			model.addAttribute("poesia", this.poesiaService.update(idPoesia, newPoesia, image));
			return "/autore/formUpdatePoesia.html";
		} else {
			model.addAttribute("poesia", this.poesiaService.findPoesiaById(idPoesia));
			}
		return "/autore/formUpdatePoesiaData.html";
	}
	
	
	@GetMapping("/poesia/{id}")
	public String getPoesia(@PathVariable("id") Long id, Model model) {
		Poesia poesia=this.poesiaService.findPoesiaById(id);
		if(poesia==null)
			return "poesiaError.html";
		else {
			model.addAttribute("poesia", poesia);
			return "poesia.html";
		}
	}
	
	@GetMapping("/poesie")
	public String showPoesie(Model model) {
		model.addAttribute("poesie", this.poesiaService.findAllPoesia());
		return "generic/poesie.html";
	}
	
	@PostMapping("/searchPoesie")
	public String searchPoesie(Model model, @RequestParam String titolo) {
		model.addAttribute("poesie", this.poesiaService.findByTitolo(titolo));
		return "poesie.html";
	}
	
}
