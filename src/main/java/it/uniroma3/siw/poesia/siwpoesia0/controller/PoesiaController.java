package it.uniroma3.siw.poesia.siwpoesia0.controller;

import java.io.IOException;


import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.ImmagineValidator;
import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@Autowired
	AutoreService artistService;
	@Autowired
	CommentoService reviewService;
	@Autowired
	GlobalController globalController;
	@Autowired
	ImmagineValidator immagineValidator;

	@GetMapping("/poesia/{id}")
	public String getPoesia(@PathVariable("id") Long id, Model model) {
		Poesia poesia=this.poesiaService.findPoesiaById(id);
		model.addAttribute("poesia", poesia);
		if(globalController.getCredentials()!=null) {
			model.addAttribute("credentials", globalController.getCredentials());
		}
		return "poesia";
	}

	@GetMapping(value="/autore/formNewPoesia")
	public String formNewPoesia(Model model) {
		model.addAttribute("poesia", new Poesia());
		return "autore/formNewPoesia";
	}
	
	@PostMapping("/autore/poesia")
	public String newPoesia(@Valid @ModelAttribute("poesia") Poesia poesia, BindingResult poesiaBindingResult,
							@Valid @ModelAttribute MultipartFile file, BindingResult fileBindingResult, Model model, RedirectAttributes redirectAttributes){
		UserDetails userDetails =  (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credenzialeService.getCredentials(userDetails.getUsername());
		poesia.setAutore(credentials.getAutore());
		this.poesiaValidator.validate(poesia, poesiaBindingResult);
		this.immagineValidator.validate(file, fileBindingResult);

		if (!poesiaBindingResult.hasErrors() && !fileBindingResult.hasErrors()) {
			//visto che saveMovie(poesia,file) può sollevare una IOException dobbiamo catturarla qui e gestirla!
			try{
				model.addAttribute("poesia", this.poesiaService.savePoesia(poesia,file));
				return "redirect:/poesia/" + poesia.getId();
			}catch(IOException e){
				redirectAttributes.addFlashAttribute("fileUploadError", "errore imprevisto nell'upload!"); //Genero un attributo passabile tramite redirect per gestire l'errore e mostrarlo in validazione
				return "redirect:/autore/formNewPoesia";
			}
		}
		return "/autore/formNewPoesia";																								//ritorna l'html e mostra gli errori del binding result
	}

	@GetMapping("/autore/formUpdatePoesia/{id}")
	public String formUpdatePoesia(@PathVariable("id") Long id, Model model) {
		Poesia poesia= this.poesiaService.findPoesiaById(id);
		if(poesia!=null) {
			model.addAttribute("poesia",poesia);
		}
		else {
			return "error";
		}
		return "/autore/formUpdatePoesia";
	}


	@PostMapping ("/autore/updatePoesia/{id}")
	public String updatePoesia(@PathVariable("id") Long id,
							   @Valid @ModelAttribute("poesia") Poesia poesia, BindingResult bindingResult,
							   @Valid @ModelAttribute MultipartFile file, Model model, RedirectAttributes redirectAttributes){

		this.poesiaValidator.validate(poesia, bindingResult); //OCCHIO BINDING RESULT NON AVRA' MAI ERRORI SE NON VALIDI LA POESIA!!!
		this.immagineValidator.validate(file, bindingResult);
		if(!bindingResult.hasErrors()) {
			try {
				model.addAttribute("poesia",this.poesiaService.updatePoesia(id, poesia, file));		//bisogna aggiornare il model con la nuova poesia
			} catch (IOException e) {
				redirectAttributes.addFlashAttribute("fileUploadError", "errore imprevisto nell'upload!"); //Genero un attributo passabile tramite redirect per gestire l'errore e mostrarlo in validazione
				return "redirect:/autore/formNewPoesia";
			}
		}
		return "poesia";
	}

	@GetMapping("/autore/deletePoesia/{idPoesia}")
	public String deletePoesia(@PathVariable("idPoesia") Long idP, Model model) {
		this.poesiaService.deletePoesia(idP);
		return "profilo";
	}

	@GetMapping("/deleteImmagine/{idPoesia}")
	public String deleteImmagineFromPoesia(@PathVariable("idPoesia") Long idP, Model model) {
		System.out.println(this.poesiaService.deleteImmagine(idP));
		return "redirect:/autore/formUpdatePoesia/" + idP;
	}


	@PostMapping("/searchPoesie")
	public String searchPoesie(Model model, @RequestParam String titolo) {
		model.addAttribute("poesie", this.poesiaService.findByTitolo(titolo));
		return "poesie.html";
	}
	
}
