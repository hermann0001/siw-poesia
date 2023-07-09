package it.uniroma3.siw.poesia.siwpoesia0.controller.validator;

import it.uniroma3.siw.poesia.siwpoesia0.model.Commento;
import it.uniroma3.siw.poesia.siwpoesia0.repository.CommentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class CommentoValidator implements Validator{

	@Autowired
	private CommentoRepository commentoRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Commento commento = (Commento) o;
		if(commento.getPoesia() != null && commento.getAutore()!= null
				&& commentoRepository.existsByAutoreAndPoesia(commento.getAutore(), commento.getPoesia())) {
			errors.reject("recensione.duplicate");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Commento.class.equals(aClass);
	}
	/*
@Autowired AutoreService autoreService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Poesia.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Poesia poesia=(Poesia)o;
		if(this.autoreService.multipleCommentofPeasiaBySameUser(poesia))
			errors.reject("review.duplicate");		
	}
*/
}
