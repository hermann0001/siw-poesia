package it.uniroma3.siw.poesia.siwpoesia0.controller.validator;

import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.repository.CommentoRepository;
import it.uniroma3.siw.poesia.siwpoesia0.service.CommentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class CommentoValidator implements Validator {

	@Autowired
	private CommentoService commentoService;

	@Override
	public void validate(Object o, Errors errors) {
		Commento commento = (Commento) o;
		if (this.commentoService.alreadyExists(commento)) {
			errors.reject("commento.duplicate");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Commento.class.equals(aClass);
	}
}
