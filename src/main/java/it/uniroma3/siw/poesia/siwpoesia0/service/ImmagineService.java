package it.uniroma3.siw.poesia.siwpoesia0.service;

import it.uniroma3.siw.poesia.siwpoesia0.model.Immagine;
import it.uniroma3.siw.poesia.siwpoesia0.repository.ImmagineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImmagineService {
    @Autowired
    private ImmagineRepository immagineRepository;

    @Transactional
    public Immagine saveImmagine(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Immagine image = new Immagine(fileName, file.getContentType(), file.getBytes());

        return this.immagineRepository.save(image);
    }

    public Immagine getImage(Long id){
        return this.immagineRepository.findById(id).get();
    }

    public Iterable<Immagine> getAllImages(){
        return this.immagineRepository.findAll();
    }

}
