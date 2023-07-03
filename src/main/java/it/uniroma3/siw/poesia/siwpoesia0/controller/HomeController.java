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
    	model.addAttribute("ultimePoesie", this.poesiaService.findPoesieBestia());
    	return "autore/bestia";
    }

    @GetMapping(value="/autore/quercia")
    public String poetaQuercia(Model model) {
        model.addAttribute("ultimePoesie", this.poesiaService.findPoesieQuercia());
        return "autore/quercia";
    }

    @GetMapping(value="/autore/marta")
    public String poetaMarta(Model model) {
        model.addAttribute("ultimePoesie", this.poesiaService.findPoesieMarta());
        return "autore/marta";
    }

    @GetMapping(value="/autore/sara")
    public String poetaSara(Model model) {
        model.addAttribute("ultimePoesie", this.poesiaService.findPoesieSara());
        return "autore/sara";
    }

    @GetMapping(value="/autore/farco")
    public String poetaFarco(Model model) {
        model.addAttribute("ultimePoesie", this.poesiaService.findPoesieFarco());
        return "autore/farco";
    }

    @GetMapping(value="/autore/inumi")
    public String poetaInumi(Model model) {
        model.addAttribute("ultimePoesie", this.poesiaService.findPoesieInumi());
        return "autore/inumi";
    }

}
