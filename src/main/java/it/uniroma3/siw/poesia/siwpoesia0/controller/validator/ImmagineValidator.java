package it.uniroma3.siw.poesia.siwpoesia0.controller.validator;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImmagineValidator implements Validator {
    private final List<String> permittedTypes = List.of(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE);  //LISTA DI MIME TYPES DI IMMAGINI

    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartFile.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile image = (MultipartFile) target;
        if(image.isEmpty()) return;                                  //una foto può essere null
        String imageType = image.getContentType();
        if(imageType != null && !permittedTypes.contains(imageType)){
            errors.reject("foto.InvalidFormat");
        }
    }
}
