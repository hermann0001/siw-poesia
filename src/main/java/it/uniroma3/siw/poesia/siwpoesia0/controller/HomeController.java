package it.uniroma3.siw.poesia.siwpoesia0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping (value="/find")
    public String cosaCerchi(Model model, @RequestParam String poesiaCercata) {
        model.addAttribute("poesie", this.poesiaService.cercaPoesia(poesiaCercata));
        return "foundPoesie";

    }
}
