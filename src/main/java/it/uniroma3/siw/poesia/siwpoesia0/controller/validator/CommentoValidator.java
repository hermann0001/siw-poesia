package it.uniroma3.siw.poesia.siwpoesia0.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;

@Component
public class CommentoValidator implements Validator{
	
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

}
