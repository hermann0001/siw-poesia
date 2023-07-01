package it.uniroma3.siw.poesia.siwpoesia0.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.poesia.siwpoesia0.model.Poesia;
import it.uniroma3.siw.poesia.siwpoesia0.repository.PoesiaRepository;

@Component
public class PoesiaValidator implements Validator{
	
	@Autowired
	private PoesiaRepository poesiaRepository;
	
	@Override
	public void validate(Object o, Errors errors) {
		Poesia poesia = (Poesia)o;
		if(poesia.getTesto() != null && poesia.getAutore()!= null
			&& poesiaRepository.existsByTestoAndAutore(poesia.getTesto(), poesia.getAutore())) {
				errors.reject("poesia.duplicate");
			}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Poesia.class.equals(aClass);
	}
}
