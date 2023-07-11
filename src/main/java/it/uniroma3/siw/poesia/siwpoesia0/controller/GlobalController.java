package it.uniroma3.siw.poesia.siwpoesia0.controller;

import it.uniroma3.siw.poesia.siwpoesia0.controller.session.SessionData;
import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.service.CommentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;

import java.util.List;


/*Annotazione usata in Spring MVC per definire un controller
 * che viene applicato su tutti gli altri controller*/
@ControllerAdvice
public class GlobalController {

	@Autowired
	private SessionData sessionData;

	@Autowired
	private CredenzialeService credentialsService;

	@Autowired
	private CommentoService commentoService;

	@ModelAttribute("user")
	public Autore getAutore() {

		try{
			return this.sessionData.getLoggedUser();
		}
		catch (ClassCastException e){
			return null;
		}
	}

	@ModelAttribute("poetidertrullo")
	public List<Credentials> getPoetiDerTrullo(){
		return this.credentialsService.findAllPoetiDerTrullo();
	}

	@ModelAttribute("ultimiCommenti")
	public List<Commento> getUltimiCommenti() { return this.commentoService.getUltimiCommenti(); }

	@ModelAttribute("credentials")
	public Credentials getCredentials(){
		try{
			return this.sessionData.getLoggedCredentials();
		}catch(ClassCastException e){
			return null;
		}
	}
}