package it.uniroma3.siw.poesia.siwpoesia0.controller;

import it.uniroma3.siw.poesia.siwpoesia0.controller.session.SessionData;
import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;



/*Annotazione usata in Spring MVC per definire un controller 
 * che viene applicato su tutti gli altri controller*/
@ControllerAdvice
public class GlobalController {

	@Autowired
	private SessionData sessionData;

	@Autowired
	private CredenzialeService credentialsService;

	@ModelAttribute("user")
	public Autore getAutore() {

		try{
			return this.sessionData.getLoggedUser();
		}
		catch (ClassCastException e){
			return null;
		}
	}

	@ModelAttribute("credentials")
	public Credentials getCredentials(){
		try{
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return credentialsService.getCredentials(userDetails.getUsername());
		}catch(ClassCastException e){
			return null;
		}
	}
}