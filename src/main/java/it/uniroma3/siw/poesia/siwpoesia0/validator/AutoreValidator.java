package it.uniroma3.siw.poesia.siwpoesia0.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.repository.AutoreRepository;

@Component
public class AutoreValidator implements Validator{

	@Autowired
	private AutoreRepository autoreRepository;
	
	@Override
	public void validate(Object o, Errors errors) {
		Autore autore = (Autore)o;
		if(autore.getUsername() != null && autoreRepository.existsByUsername(autore.getUsername())) {
			errors.reject("autore.duplicate");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Autore.class.equals(aClass);
	}
}
