package it.uniroma3.siw.poesia.siwpoesia0.service;

import java.time.LocalDateTime;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.CommentoRepository;
import jakarta.transaction.Transactional;

@Service
public class CommentoService {

    @Autowired
    CommentoRepository commentoRepository;

    @Autowired
    PoesiaService poesiaService;

    @Autowired
    AutoreService autoreService;

    @Transactional
    public Commento find(Long id) {
        return this.commentoRepository.findById(id).get();
    }

    @Transactional
    public void deleteCommento(Commento commento) {
        if(commento != null) this.commentoRepository.delete(commento);
    }

    @Transactional
    public Commento saveCommento(Commento commento, Long idA, Long idP) {
        Poesia poesia = this.poesiaService.find(idP);
        Autore autore = this.autoreService.find(idA);

        commento.setAutore(autore);
        commento.setPoesia(poesia);
        poesia.getCommenti().add(commento);
        autore.getCommenti().add(commento);

        System.out.println(LocalDateTime.now());
        commento.setData(LocalDateTime.now());              //imposta data di pubblicazione al momento della creazione

        return this.commentoRepository.save(commento);
    }
    @Transactional
    public Commento deleteCommento(Long idC, Long idP) {
        Commento commento = this.commentoRepository.findById(idC).orElse(null);
        Poesia poesia = this.poesiaService.find(idP);

        if(commento != null && poesia!=null) {
            poesia.getCommenti().remove(commento);
            commento.getAutore().getCommenti().remove(commento);

            this.commentoRepository.delete(commento);
            this.poesiaService.savePoesia(poesia);
            this.autoreService.saveAutore(commento.getAutore());
        }
        return commento;
    }

    @Transactional
    public List<Commento> getUltimiCommenti() {
        return this.commentoRepository.findPrimiQuattroCommenti();
    }

    @Transactional
    public boolean alreadyExists(Commento commento) {
        return commento.getPoesia() != null && commento.getAutore() != null
                && commentoRepository.existsByAutoreAndPoesia(commento.getAutore(), commento.getPoesia());
    }
}
