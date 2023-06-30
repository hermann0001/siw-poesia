package it.uniroma3.siw.poesia.siwpoesia0.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Credenziale {
	public static final String DEFAULT_RUOLO = "DEFAULT";
	public static final String POETA_RUOLO = "POETA";
	public static final String AUTORE_RUOLO = "AUTORE";

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String username;
	private String password;
	private String ruolo;

	@OneToOne(cascade = CascadeType.ALL)
	private Autore autore;
	
	public String getUsername() {
		return username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Autore getUser() {
		return autore;
	}

	public void setUser(Autore user) {
		this.autore = user;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRuolo() {
		return ruolo;
	}
	
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	public boolean isPoeta() {
		return this.ruolo.equals(POETA_RUOLO);
	}
	
	public boolean isAutore() {
		return this.ruolo.equals(AUTORE_RUOLO);
	}
}
