package it.uniroma3.siw.poesia.siwpoesia0.controller.validator;

import it.uniroma3.siw.poesia.siwpoesia0.service.PoesiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;

import java.time.LocalDate;

@Component
public class PoesiaValidator implements Validator{
	
	@Autowired
	private PoesiaService poesiaService;
	
	@Override
	public void validate(Object o, Errors errors) {
		Poesia poesia = (Poesia)o;
		if(poesia.getDataPubblicazione() == null)
			poesia.setDataPubblicazione(LocalDate.now());
		if(this.poesiaService.alreadyExists(poesia)) {
				errors.reject("poesia.duplicate");
			}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Poesia.class.equals(aClass);
	}
}
