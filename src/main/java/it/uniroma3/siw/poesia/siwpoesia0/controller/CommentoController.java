package it.uniroma3.siw.poesia.siwpoesia0.controller;

import it.uniroma3.siw.poesia.siwpoesia0.controller.session.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.poesia.siwpoesia0.controller.validator.CommentoValidator;
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
    CommentoValidator commentoValidator;
	@Autowired
    CommentoService commentoService;
	@Autowired
    AutoreService userService;
    @Autowired
    SessionData sessionData;

	@GetMapping("/autore/formNewCommento/{idPoesia}/{idAutore}")
    public String formNewCommento(@PathVariable("idPoesia") Long idP, @PathVariable("idAutore") Long idA, Model model) {
        Poesia poesia = this.poesiaService.find(idP);

        if(poesia!= null) {
            model.addAttribute("commento", new Commento());
            model.addAttribute("poesia", poesia);
            model.addAttribute("credentials", this.sessionData.getLoggedCredentials());
            return "formNewCommento.html";
        } else
           return "error.html";
    }

    //non serviva paassare l'id dell'autore, basta prendere quello loggato!!! Anzi in quel modo tutti potevano creare commenti per altri utenti (terribile)
    @PostMapping("/addCommento/{idPoesia}")
    public String newCommento(@Valid @ModelAttribute("newCommento") Commento commento, BindingResult bindingResult,
                              @PathVariable("idPoesia") Long id, Model model) {
        this.commentoValidator.validate(commento, bindingResult);
        if (!bindingResult.hasErrors()) {
            this.commentoService.saveCommento(commento, this.sessionData.getLoggedUser().getId(), id);
            return "redirect:/poesia/" + id;
        }
        model.addAttribute("poesia", this.poesiaService.find(id));
        return "poesia";
    }

    @GetMapping(value = "/deleteCommento/{id}")
    public String deleteCommento(@PathVariable("id")Long id){
        Commento commento = this.commentoService.find(id);
        Poesia poesia = commento.getPoesia();
        //Controlliamo se la richiesta URL sia stata correttamente formata dall'autore del commento
        if(!this.sessionData.getLoggedUser().equals(commento.getUser()))
            return "/errors/error404";
        this.commentoService.deleteCommento(commento);
        return "redirect:/poesia/" + poesia.getId();
    }

    @GetMapping(value = "/autore/deleteCommento/{id}")
    public String autoreDeleteCommento(@PathVariable("id")Long id){
        Commento commento = this.commentoService.find(id);
        Poesia poesia = commento.getPoesia();
        this.commentoService.deleteCommento(commento);
        return "redirect:/poesia/" + poesia.getId();
    }
}
