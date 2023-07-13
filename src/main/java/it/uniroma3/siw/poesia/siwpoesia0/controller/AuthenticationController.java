package it.uniroma3.siw.poesia.siwpoesia0.controller;


import it.uniroma3.siw.poesia.siwpoesia0.controller.session.SessionData;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.AutoreValidator;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.ImmagineValidator;
import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;
import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.CredenzialeValidator;
import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@Controller
public class AuthenticationController {

	@Autowired
	private CredenzialeService credenzialeService;
	@Autowired
	private AutoreService autoreService;
	@Autowired
	private PoesiaService poesiaService;
	@Autowired
	SessionData sessionData;
	@Autowired
	private CredenzialeValidator credenzialeValidator;
	@Autowired
	private AutoreValidator autoreValidator;
	@Autowired
	private ImmagineValidator immagineValidator;

	@GetMapping(value = "/login")
	public String ShowLoginForm() {
		return "formLogin";
	}

	@GetMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("ultimePoesie", this.poesiaService.getUltimePoesie());
		return "index";
	}

	@GetMapping(value = "/profilo")
	public String defaultAfterLogin(Model model) {
		model.addAttribute("poesie", this.poesiaService.getUltimePoesieDiAutore(sessionData.getLoggedUser()));
		return "profilo";
	}

	@GetMapping(value="/autore/formUpdateProfilo")
	public String formUpdateProfilo() {
		return "/autore/formUpdateProfilo";
	}


	//Ho tolto gli id perché un utente può modificare solo il suo profilo, ma noi il riferimento a utente ce l'abbiamo sempre da GlobalController
	@PostMapping (value="/autore/updateProfilo")
	public String updateProfilo(@Valid @ModelAttribute("user") Autore user, BindingResult autoreBindingResult,
								@Valid @ModelAttribute MultipartFile file, BindingResult fileBindingResult,
								Model model) {
		//this.autoreValidator.validate(autore, autoreBindingResult);
		if(!file.isEmpty()) this.immagineValidator.validate(file, fileBindingResult);
		if(!fileBindingResult.hasErrors()){
			try {
				this.autoreService.saveAutore(user, file);
				return "redirect:/profilo";
			} catch(IOException e) {
				model.addAttribute("fileUploadError", "errore imprevisto nell'upload!");
			}
		}
		return "/autore/formUpdateProfilo";
	}

	@GetMapping(value="/autore/deleteImmagineProfilo")
	public String deleteImmagineProfilo() {
		this.autoreService.deleteImmagine(this.sessionData.getLoggedUser());
		return "redirect:/autore/formUpdateProfilo";
	}

	@GetMapping(value = "/register")
	public String ShowRegisterForm(Model model) {
		model.addAttribute("autore", new Autore());
		model.addAttribute("credenziali", new Credentials());
		return "formRegisterUser";
	}

	@PostMapping(value = {"/register"})
	public String registerUser(@Valid @ModelAttribute("autore") Autore autore, BindingResult autoreBindingResult,
							   @Valid @ModelAttribute("credenziali") Credentials credenziali, BindingResult credentialsBindingResult,
							   @Valid @ModelAttribute MultipartFile file, BindingResult fileBindingResult,
							   Model model)  {
		this.credenzialeValidator.validate(credenziali, credentialsBindingResult);
		this.autoreValidator.validate(autore, autoreBindingResult);
		if(!file.isEmpty()) this.immagineValidator.validate(file, fileBindingResult);

		// se autore, credenziali e foto hanno contenuti validi, memorizza Autore e Credenziale e Immagine nel DB
		if (!autoreBindingResult.hasErrors() && !credentialsBindingResult.hasErrors() && !fileBindingResult.hasErrors()) {
			try{
				Autore a = this.autoreService.saveAutore(autore, file);
				credenziali.setAutore(a);
				credenzialeService.saveCredentials(credenziali);
				model.addAttribute("autore", a);
				return "redirect:/profilo";
			}catch(IOException e){
				model.addAttribute("fileUploadError", "errore imprevisto nell'upload!");
			}
		}
		return "formRegisterUser";
	}
}
