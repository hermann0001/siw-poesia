package it.uniroma3.siw.poesia.siwpoesia0.controller;


import it.uniroma3.siw.poesia.siwpoesia0.controller.session.SessionData;
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

		/*per ora non ho trovato nulla di particolare da mettere nella home dei poeti*/

		/*if (authentication instanceof AnonymousAuthenticationToken) {
			return "index";
		} else {
			if (this.sessionData.getLoggedCredentials().getRole().equals(Credentials.POETA_RUOLO)) {
				return "poeta/indexAdmin";
			}
		}*/
		return "index";
	}

	@GetMapping(value = "/profilo")
	public String defaultAfterLogin(Model model) {
		model.addAttribute("poesie", this.poesiaService.getUltimePoesieDiAutore(sessionData.getLoggedUser()));

		return "profilo";
	}

	@PostMapping(value = {"/register"})
	public String registerUser(@Valid @ModelAttribute("autore") Autore autore,
							   BindingResult autoreBindingResult, @Valid @ModelAttribute("credenziali") Credentials credenziali,
							   BindingResult credentialsBindingResult,
							   Model model) {

		this.credenzialeValidator.validate(credenziali, credentialsBindingResult);
		// se autore e  credenziali hanno entrambi contenuti validi, memorizza Autore e Credenziale nel DB
		if (!autoreBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			autoreService.saveAutore(autore);
			credenziali.setAutore(autore);
			credenzialeService.saveCredentials(credenziali);
			model.addAttribute("autore", autore);
			return "registrationSuccessful";
		}
		return "formRegisterAutore";
	}
}
