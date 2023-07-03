package it.uniroma3.siw.poesia.siwpoesia0.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credenziale;
import it.uniroma3.siw.poesia.siwpoesia0.repository.CredenzialeRepository;

@Service
public class CredenzialeService {
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected CredenzialeRepository credenzialeRepository;
	
	@Transactional
	public Credenziale getCredentials(Long id) {
		Optional<Credenziale> result = this.credenzialeRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Credenziale getCredentials(String username) {
		Optional<Credenziale> result = this.credenzialeRepository.findByUsername(username);
		return result.orElse(null);
	}
	
	@Transactional
	public Credenziale saveCredentials(Credenziale credentials) {
		credentials.setRuolo(Credenziale.AUTORE_RUOLO);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credenzialeRepository.save(credentials);
	}
	
	public boolean alreadyExists(Credenziale credentials) {
		return credentials!=null && this.credenzialeRepository.existsByUsername(credentials.getUsername());
	}
}
