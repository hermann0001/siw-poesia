package it.uniroma3.siw.poesia.siwpoesia0.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/contattaci")
    public String contatti(Model model) {
        return "contatti.html";
    }

    @GetMapping("/chi-siamo")
    public String chiSiamo(Model model) {
        return "poeti";
    }


}
