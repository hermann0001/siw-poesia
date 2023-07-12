package it.uniroma3.siw.poesia.siwpoesia0.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.repository.CredenzialeRepository;

@Service
public class CredenzialeService {
	@Autowired
	protected PasswordEncoder passwordEncoder;
	@Autowired
	protected CredenzialeRepository credenzialeRepository;
	
	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credenzialeRepository.findByUsername(username);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		credentials.setRole(Credentials.AUTORE_RUOLO);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credenzialeRepository.save(credentials);
	}

	@Transactional
	public boolean alreadyExists(Credentials credentials) {
		return credentials!=null && this.credenzialeRepository.existsByUsername(credentials.getUsername());
	}

	@Transactional
    public String getUsername(Long id) {
		return this.credenzialeRepository.findById(id).orElse(null).getUsername();   //mi torna null
	}

	public List<Credentials> findAllPoetiDerTrullo() {
		return this.credenzialeRepository.findAllByRole(Credentials.POETA_RUOLO);
	}

	@Transactional
	public Credentials findCredentials(Long id) {
		return this.credenzialeRepository.findById(id).orElse(null);
	}
}
