package it.uniroma3.siw.poesia.siwpoesia0.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;

@Component
public class CredenzialeValidator implements Validator{
	
	@Autowired private CredenzialeService credenzialeService;
	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Credentials credentials=(Credentials)o;
		if(this.credenzialeService.alreadyExists(credentials))
			errors.reject("credentials.duplicate");
	}
	
}
