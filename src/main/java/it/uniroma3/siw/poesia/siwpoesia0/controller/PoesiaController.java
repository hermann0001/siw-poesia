package it.uniroma3.siw.poesia.siwpoesia0.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.PoesiaRepository;

@Controller
public class PoesiaController {
	@Autowired PoesiaRepository poesiaRepository;
	
	@GetMapping(value = "/")
	public String index(Model model) {
		return "index.html";
	}

	@GetMapping(value="/admin/formNewPoesia") 
	public String formNewPoesia(Model model) { 
		model.addAttribute("poesia", new Poesia());
		return "admin/formNewPoesia.html";
	}
	
	@PostMapping("/admin/poesia") 
	public String newMovie(@Valid @ModelAttribute("poesia") Poesia poesia, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile[] file) throws IOException { 
		this.poesiaValidator.validate(poesia, bindingResult);
		if (!bindingResult.hasErrors()) { 
			this.posiaService.newPoesia(poesia, file, model);
			model.addAttribute("posia", poesia); 
			return "poesia.html";
		} else { 
			return "admin/formNewPoesia.html";
		}
	}
}
