package it.uniroma3.siw.poesia.siwpoesia0.controller;


import it.uniroma3.siw.poesia.siwpoesia0.controller.session.SessionData;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.AutoreValidator;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.ImmagineValidator;
import it.uniroma3.siw.poesia.siwpoesia0.model.Immagine;
import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CommentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.CredenzialeValidator;
import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;
import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
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
	@Autowired
	private CommentoService commentoService;

	@GetMapping(value = "/register")
	public String ShowRegisterForm(Model model) {
		model.addAttribute("autore", new Autore());
		model.addAttribute("credenziali", new Credentials());
		return "formRegisterUser";
	}

	@GetMapping(value = "/login")
	public String ShowLoginForm(Model model) {
		return "formLogin";
	}

	@GetMapping(value = "/")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("ultimePoesie", this.poesiaService.getUltimePoesie());
		model.addAttribute("ultimiCommenti", this.commentoService.getUltimiCommenti());

		/*per ora non ho trovato nulla di particolare da mettere nella home dei poeti*/

		return "index";
	}

	@GetMapping(value = "/profilo")
	public String defaultAfterLogin(Model model) {
		model.addAttribute("poesie", this.poesiaService.getUltimePoesieDiAutore(sessionData.getLoggedUser()));

		return "profilo";
	}

	@PostMapping(value = {"/register"})
	public String registerUser(@Valid @ModelAttribute("autore") Autore autore, BindingResult autoreBindingResult,
							   @Valid @ModelAttribute("credenziali") Credentials credenziali, BindingResult credentialsBindingResult,
							   @Valid @ModelAttribute MultipartFile file, BindingResult fileBindingResult,
							   Model model)  {
		this.credenzialeValidator.validate(credenziali, credentialsBindingResult);
		this.autoreValidator.validate(autore, autoreBindingResult);
		this.immagineValidator.validate(file, fileBindingResult);
		// se autore, credenziali e foto hanno contenuti validi, memorizza Autore e Credenziale e Immagine nel DB
		if (!autoreBindingResult.hasErrors() && !credentialsBindingResult.hasErrors() && !fileBindingResult.hasErrors()) {
			try{
				Autore a = this.autoreService.saveAutore(autore, file);
				credenziali.setAutore(a);
				credenzialeService.saveCredentials(credenziali);
				model.addAttribute("autore", a);
				return "registrationSuccessful";
			}catch(IOException e){
				model.addAttribute("fileUploadError", "errore imprevisto nell'upload!");
			}
		}
		return "formRegisterUser";
	}
}
