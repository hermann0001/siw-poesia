package it.uniroma3.siw.poesia.siwpoesia0.controller;


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

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Credenziale;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;
import jakarta.validation.Valid;


@Controller
public class AuthenticationController {

	@Autowired
	private CredenzialeService credenzialeService;
	
	@GetMapping(value = "/register")
	public String ShowRegisterForm (Model model) {
		model.addAttribute("autore", new Autore());
		model.addAttribute("credenziali", new Credenziale());
		return "formRegisterUser";
	}
	
	@GetMapping(value = "/login")
	public String ShowLoginForm(Model model) {
		return "formLogin.html";
	}
	
	@GetMapping(value = "/")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return "index.html";
		} else {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credenziale credentials = credenzialeService.getCredentials(userDetails.getUsername());
			if (credentials.getRuolo().equals(Credenziale.POETA_RUOLO)) {
				return "admin/indexAdmin.html";
			}
		}
		return "index.html";
	}
	
	@GetMapping(value = "/success")
	public String defaultAfterLogin(Model model) {
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credenziale credentials = credenzialeService.getCredentials(userDetails.getUsername());
    	if (credentials.getRuolo().equals(Credenziale.POETA_RUOLO)) {
            return "admin/indexAdmin.html";
        }
        return "index.html";
	}
	
	@PostMapping(value = {"/register"})
	public String registerUser(@Valid @ModelAttribute("user") Autore autore,
			BindingResult userBindingResult, @Valid
            @ModelAttribute("credentials") Credenziale credentials,
            BindingResult credentialsBindingResult,
            Model model) {
		
		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            credentials.setAutore(autore);
            credenzialeService.saveCredentials(credentials);
            model.addAttribute("user", autore);
            return "registrationSuccessful";
        }
        return "registerUser";
		
	}
	
}
