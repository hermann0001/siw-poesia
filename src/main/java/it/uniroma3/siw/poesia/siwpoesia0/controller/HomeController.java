package it.uniroma3.siw.poesia.siwpoesia0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;

@Controller
public class HomeController {

	@Autowired
	PoesiaService poesiaService;
	
	
    @GetMapping(value="/contattaci")
    public String contatti(Model model) {
        return "contatti.html";
    }

    @GetMapping(value="/chi-siamo")
    public String chiSiamo(Model model) {
        return "poeti";
    }

    @GetMapping(value="/libro")
    public String libro(Model model){ 
    	return "libro"; 
    }
    
    @GetMapping(value="/autore/bestia")
    public String poetaBestia(Model model) {
    	model.addAttribute("ultimePoesie", this.poesiaService.findPoesiePoeta());
    	return "autore/bestia";
    }
    

}
