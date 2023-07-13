package it.uniroma3.siw.poesia.siwpoesia0.controller;

import java.io.IOException;


import it.uniroma3.siw.poesia.siwpoesia0.controller.session.SessionData;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.ImmagineValidator;
import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CommentoService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.PoesiaValidator;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PoesiaController {
	
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
	SessionData sessionData;
	@Autowired
	ImmagineValidator immagineValidator;

	@GetMapping("/poesia/{id}")
	public String getPoesia(@PathVariable("id") Long id, Model model) {
		Poesia poesia=this.poesiaService.find(id);
		model.addAttribute("poesia", poesia);
		model.addAttribute("username", this.credenzialeService.findUsernameFromAutore(poesia.getAutore().getId()));
		Autore loggedUser = this.sessionData.getLoggedUser();
		if(loggedUser != null) model.addAttribute("newCommento", new Commento());			//se loggato posso commentare la poesia
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
		Poesia poesia = this.poesiaService.find(id);
		Credentials loggedCredentials = this.sessionData.getLoggedCredentials();
		if(loggedCredentials.isPoeta() || poesia.getAutore().equals(loggedCredentials.getAutore())) {
			model.addAttribute("poesia", poesia);
			return "/autore/formUpdatePoesia";
		}
		return "redirect:/login";
	}

	@GetMapping("/poeta/formUpdatePoesia/{id}")
	public String poetaFormUpdatePoesia(@PathVariable("id") Long id, Model model) {
		model.addAttribute("poesia", this.poesiaService.find(id));
		return "/autore/formUpdatePoesia";
	}


	@PostMapping ("/autore/updatePoesia/{id}")
	public String autoreUpdatePoesia(@PathVariable("id") Long id,
							   @Valid @ModelAttribute("poesia") Poesia poesia, BindingResult poesiaBindingResult,
							   @Valid @ModelAttribute MultipartFile file, BindingResult fileBindingResult,
							   Model model, RedirectAttributes redirectAttributes){

		this.poesiaValidator.validate(poesia, poesiaBindingResult); //OCCHIO BINDING RESULT NON AVRA' MAI ERRORI SE NON VALIDI LA POESIA!!!
		this.immagineValidator.validate(file, fileBindingResult);
		if(!poesiaBindingResult.hasErrors() && !fileBindingResult.hasErrors()) {
			try {
				model.addAttribute("poesia",this.poesiaService.updatePoesia(id, poesia, file));		//bisogna aggiornare il model con la nuova poesia
			} catch (IOException e) {
				redirectAttributes.addFlashAttribute("fileUploadError", "errore imprevisto nell'upload!"); //Genero un attributo passabile tramite redirect per gestire l'errore e mostrarlo in validazione
				return "redirect:/autore/formUpdatePoesia";
			}
		}
		return "redirect:/poesia/" + poesia.getId();
	}

	@PostMapping ("/poeta/updatePoesia/{id}")
	public String poetaUpdatePoesia(@PathVariable("id") Long id,
							   @Valid @ModelAttribute("poesia") Poesia poesia, BindingResult poesiaBindingResult,
							   @Valid @ModelAttribute MultipartFile file, BindingResult fileBindingResult,
							   Model model, RedirectAttributes redirectAttributes){

		this.poesiaValidator.validate(poesia, poesiaBindingResult); //OCCHIO BINDING RESULT NON AVRA' MAI ERRORI SE NON VALIDI LA POESIA!!!
		this.immagineValidator.validate(file, fileBindingResult);
		if(!poesiaBindingResult.hasErrors() && !fileBindingResult.hasErrors()) {
			try {
				model.addAttribute("poesia",this.poesiaService.updatePoesia(id, poesia, file));		//bisogna aggiornare il model con la nuova poesia
			} catch (IOException e) {
				redirectAttributes.addFlashAttribute("fileUploadError", "errore imprevisto nell'upload!"); //Genero un attributo passabile tramite redirect per gestire l'errore e mostrarlo in validazione
				return "redirect:/poeta/formUpdatePoesia";
			}
		}
		return "redirect:/poesia/" + poesia.getId();
	}

	@GetMapping("/autore/deletePoesia/{idPoesia}")
	public String deletePoesia(@PathVariable("idPoesia") Long idP) {
		this.poesiaService.deletePoesia(idP);
		return "redirect:/profilo";
	}

	@GetMapping("/deleteImmagine/{idPoesia}")
	public String deleteImmagineFromPoesia(@PathVariable("idPoesia") Long idP) {
		System.out.println(this.poesiaService.deleteImmagine(idP));
		return "redirect:/autore/formUpdatePoesia/" + idP;
	}

	
}
