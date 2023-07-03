package it.uniroma3.siw.poesia.siwpoesia0.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

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
    	return "autore/bestia";
    }
    

}
