package it.uniroma3.siw.poesia.siwpoesia0.controller;


import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;
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
	private CredenzialeValidator credenzialeValidator;
	
	@GetMapping(value = "/register")
	public String ShowRegisterForm (Model model) {
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
		if (authentication instanceof AnonymousAuthenticationToken) {
			return "index";
		} else {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credenzialeService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.POETA_RUOLO)) {
				return "admin/indexAdmin";
			}
		}
		return "index";
	}
	
	@GetMapping(value = "/success")
	public String defaultAfterLogin(Model model) {
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credenzialeService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.POETA_RUOLO)) {
            return "admin/indexAdmin";
        }
        return "index";
	}
	
	@PostMapping(value = {"/register"})
	public String registerUser(@Valid @ModelAttribute("autore") Autore autore,
			BindingResult autoreBindingResult, @Valid @ModelAttribute("credenziali") Credentials credenziali,
            BindingResult credentialsBindingResult,
            Model model) {

		this.credenzialeValidator.validate(credenziali, credentialsBindingResult);
		// se autore e  credenziali hanno entrambi contenuti validi, memorizza Autore e Credenziale nel DB
        if(!autoreBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			autoreService.saveAutore(autore);
        	credenziali.setAutore(autore);
            credenzialeService.saveCredentials(credenziali);
            model.addAttribute("autore", autore);
            return "registrationSuccessful";
        }
        return "formRegisterAutore";
		
	}
	
	@GetMapping("/autore/profile") 
	public String profilo(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credenzialeService.getCredentials(userDetails.getUsername());
		
		model.addAttribute("credentials", credentials);
		return "profilo";
	}
	
}
