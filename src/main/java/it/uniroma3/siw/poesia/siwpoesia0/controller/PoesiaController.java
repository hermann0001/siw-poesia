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

import it.uniroma3.siw.poesia.siwpoesia0.model.Credenziale;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.PoesiaRepository;
import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CommentoService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;
import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.PoesiaValidator;

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
	
	@Autowired AutoreService artistService;
	@Autowired CommentoService reviewService;
	
	@GetMapping("/admin/managePoesie")
	public String managePoesie(Model model) {
		model.addAttribute("movies", this.poesiaService.findAllPoesia());
		return "admin/managePoesie.html";
	}
	@Transactional
	@GetMapping("/admin/formUpdatePoesia/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		Poesia poesia= this.poesiaService.findPoesiaById(id);
		if(poesia!=null) {
			model.addAttribute("poesia",poesia);
		}
		else {
			return "poesiaError.html";
		}
		return "/admin/formUpdatePoesia.html";
	}
	@Transactional
	@GetMapping("/admin/addAutoreToPoesia/{id}")
	public String addDirector(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autori",this.artistService.findAllAutori());
		Poesia poesia= this.poesiaService.findPoesiaById(id);
		if(poesia == null)
			return "generic/poesiaError.html";
		model.addAttribute("poesia",poesia);
		return "/admin/autoreToAdd.html";
	}
	@Transactional
	@GetMapping("/admin/setAutoreToMovie/{idAutore}/{idPoesia}")
	public String setDirectorToMovie(@PathVariable("idAutore") Long idAutore, @PathVariable("idPoesia") Long idPoesia, Model model){
		Poesia poesia=this.poesiaService.saveAutoreToPoesia(idPoesia, idAutore);
		if(poesia!=null) {
			model.addAttribute("poesia", poesia);
			model.addAttribute("autore", poesia.getAutore());
			return "/admin/formUpdatePoesia.html";
		}
		else
			return "generic/poesiaError.html";
		}
	
	
	@GetMapping("/admin/formConfirmDeletePoesia/{idPoesia}")
	public String formConfirmDeletePoesia(@PathVariable("idPoesia") Long idPoesia, Model model) {
		Poesia poesia=this.poesiaService.findPoesiaById(idPoesia);
		if(poesia==null)
			return "generic/poesiaError.html";
		else {
			model.addAttribute("poesia", poesia);
			return "admin/formConfirmDeletePoesia.html";
		}
	}
	@Transactional
	@GetMapping("/admin/deletePoesia/{idPoesia}")
	public String deleteMovie(@PathVariable("idPoesia") Long idPoesia, Model model) {
		Poesia poesia=this.poesiaService.findPoesiaById(idPoesia);
		if(poesia==null)
			return "generic/poesiaError.html";
		this.artistService.removeMovieAssociationFromAllActor(poesia);
		this.poesiaService.removeAutoreAssociationFromPoesia(idPoesia);
		this.reviewService.removePoesiaAssociationFromCommento(poesia);
		this.poesiaService.delete(idPoesia);
		model.addAttribute("poesie", this.poesiaService.findAllPoesia());
		return "admin/managePoesie.html";
	}	
	
	@GetMapping("/admin/formUpdatePoesiaData/{idPoesia}")
	public String formUpdateMovieData(@PathVariable("idPoesia") Long idPoesia, Model model) {
		Poesia poesia=this.poesiaService.findPoesiaById(idPoesia);
		if(poesia==null)
			return "generic/poesiaError.html";
		model.addAttribute("poesia",poesia);
		return "admin/formUpdatePesiaData.html";
	}
	@PostMapping("/admin/updatePoesiaData/{idPoesia}")
		public String updateMovieData(@PathVariable("idPoesia") Long idPoesia, 
				@Valid @ModelAttribute("poesia") Poesia newPoesia, BindingResult bindingResult,
				MultipartFile image, Model model) {
		this.poesiaValidator.validate(newPoesia, bindingResult);
		if(!bindingResult.hasErrors()) {
			model.addAttribute("poesia", this.poesiaService.update(idPoesia, newPoesia, image));
			return "/admin/formUpdatePoesia.html";
		}
		else {
			model.addAttribute("poesia", this.poesiaService.findPoesiaById(idPoesia));
		}
			return "/admin/formUpdatePoesiaData.html";
		}
	
	
	@GetMapping("/generic/poesie/{id}")
	public String getPoesia(@PathVariable("id") Long id, Model model) {
		Poesia poesia=this.poesiaService.findPoesiaById(id);
		if(poesia==null)
			return "generic/poesiaError.html";
		else {
			model.addAttribute("poesia", poesia);
			return "generic/poesia.html";
		}
	}
	@GetMapping("/generic/poesie")
	public String showPoesie(Model model) {
		model.addAttribute("poesie", this.poesiaService.findAllPoesia());
		return "generic/poesie.html";
	}
	@PostMapping("/generic/searchPoesie")
	public String searchPoesie(Model model, @RequestParam String titolo) {
		model.addAttribute("poesie", this.poesiaService.findByTitolo(titolo));
		return "generic/poesie.html";
	}
}
