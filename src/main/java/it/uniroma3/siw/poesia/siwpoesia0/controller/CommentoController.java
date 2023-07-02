package it.uniroma3.siw.poesia.siwpoesia0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.AutoreValidator;
import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.CommentoValidator;
import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CommentoService;
import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
import jakarta.validation.Valid;

@Controller
public class CommentoController {
	
	@Autowired CommentoService reviewService;
	@Autowired CommentoValidator reviewValidator;
	@Autowired PoesiaService movieService;
	@Autowired AutoreService userService;
	@Autowired AutoreValidator onlyOneReviewForUserValidator;
	
	
	
	@GetMapping("registered/addCommentoToPoesia/{idPoesia}")
	public String createReviewToMovie(@PathVariable("idPoesia") Long idPoesia, Model model) {
		Poesia poesia= this.movieService.findPoesiaById(idPoesia);
		model.addAttribute("poesia", poesia);
		model.addAttribute("commento",new Commento());
		return "registered/commentoToAddToPoesia.html";
	}
	
	@PostMapping("registered/createCommentoToPoesia/{idPoesia}")
	public String newReviewToMovie(@Valid @ModelAttribute("review") Commento commento, BindingResult bindingResult, @PathVariable("idPoesia") Long idPoesia , Model model) {
		this.reviewValidator.validate(commento, bindingResult);
		this.onlyOneReviewForUserValidator.validate(this.movieService.findPoesiaById(idPoesia), bindingResult);
		if(!bindingResult.hasErrors()) {
			Poesia poesia=this.movieService.findPoesiaById(idPoesia);
			this.reviewService.setPoesiaToCommento(commento, poesia);
			this.movieService.addCommentoToPoesia(poesia, commento);
			Autore autore=userService.getCurrentUser();
			this.userService.addCommento(autore, commento);
			this.reviewService.setUser(commento, autore);
			
			model.addAttribute("poesia", poesia);		
			model.addAttribute("commenti",poesia.getCommenti());
			return "generic/poesia.html";
		}
		else {
			model.addAttribute("poesia",this.movieService.findPoesiaById(idPoesia));
			return "registered/commentoToAddToPoesia.html";
		}
	}
	
	@GetMapping("registered/formConfirmDeleteCommento/{idCommento}")
	public String formConfirmDeleteReview(@PathVariable ("idCommento") Long idCommento, Model model) {
		Commento commento=this.reviewService.findReviewById(idCommento);
		if(commento==null)
			return "commentoError.html";
		model.addAttribute("commento", commento);
		return "registered/formConfirmDeleteCommento.html";
	}
	
	@GetMapping("/admin/deleteCommento/{idCommento}")
	public String deleteReview(@PathVariable ("idCommento") Long idCommento, Model model) {
		Commento commento=this.reviewService.findReviewById(idCommento);
		if(commento==null)
			return "commentoError.html";
		Poesia poesia=commento.getPoesia();
		this.movieService.removeCommentoAssociationFromPoesia(commento);
		this.userService.removeCommentoAsscociationFromUser(commento);
		this.reviewService.delete(idCommento);
		model.addAttribute("poesia",poesia);
		return "generic/poesia.html";
	}
	
	@GetMapping("/registered/deleteCommentoRegistered/{idCommento}")
	public String deleteReviewRegistered(@PathVariable ("idCommento") Long idCommento, Model model) {
		Commento review=this.reviewService.findReviewById(idCommento);
		if(review==null || !review.getUser().equals(userService.getCurrentUser()))
			return "reviewError.html";
		Poesia poesia=review.getPoesia();
		this.movieService.removeCommentoAssociationFromPoesia(review);
		this.userService.removeCommentoAsscociationFromUser(review);
		this.reviewService.delete(idCommento);
		model.addAttribute("poesia",poesia);
		return "generic/poesia.html";
	}

}
