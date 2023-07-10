package it.uniroma3.siw.poesia.siwpoesia0.controller;

import it.uniroma3.siw.poesia.siwpoesia0.model.Immagine;
import it.uniroma3.siw.poesia.siwpoesia0.service.ImmagineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImmagineController {
    @Autowired
    private ImmagineService immagineService;

    @GetMapping(value = "/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") Long id){
        Immagine image = this.immagineService.getImage(id);

        /*Costruisci una pagina html che abbia come contenuto un file immagine */
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getTipo()))
                .body(image.getData());
    }
}
