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

    @Autowired
    PoesiaService poesiaService;

    @Autowired
    GlobalController globalController;

    @Autowired
    CommentoValidator commentoValidator;


	@Autowired
    CommentoService commentoService;


	@Autowired AutoreService userService;

	
	@GetMapping("/autore/formNewCommento/{idPoesia}/{idAutore}")
    public String formNewCommento(@PathVariable("idPoesia") Long idP, @PathVariable("idAutore") Long idA, Model model) {
        Poesia poesia = this.poesiaService.findPoesiaById(idP);

        if(poesia!= null) {
            model.addAttribute("commento", new Commento());
            model.addAttribute("poesia", poesia);
            model.addAttribute("credentials", globalController.getCredentials());
            return "formNewCommento.html";
        } else
           return "error.html";
    }

    @PostMapping("/addCommento/{idPoesia}/{idAutore}")
    public String newCommento(@Valid @ModelAttribute("commento") Commento commento, @PathVariable("idPoesia") Long idP, @PathVariable("idAutore") Long idA, BindingResult bindingResult, Model model) {
        this.commentoValidator.validate(commento, bindingResult);
        if (!bindingResult.hasErrors()) {
            this.commentoService.newCommento(commento, idA, idP, model);
            model.addAttribute("commento", commento);
            return "redirect:/poesia/"+idP;
        } else {
            return "formNewCommento";
        }
    }

    @GetMapping(value="/deleteCommento/{idCommento}/{idPoesia}")
    public String deleteCommentoToPoesia(@PathVariable("idCommento") Long idC, @PathVariable("idPoesia") Long idP, Model model) {
        Commento commento = this.commentoService.deleteCommento(idC, idP);
        if(commento != null) {
            model.addAttribute("commento", commento);
            return "redirect:/poesia/"+idP;
        } else {
            return "error.html";
        }
    }


}
