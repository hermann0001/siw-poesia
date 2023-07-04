package it.uniroma3.siw.poesia.siwpoesia0.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Credentials {
	public static final String DEFAULT_RUOLO = "DEFAULT";
	public static final String POETA_RUOLO = "POETA";
	public static final String AUTORE_RUOLO = "AUTORE";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@NotNull
	private String username;
	@NotNull
	private String password;
	private String role;

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

	public Autore getAutore() {
		return autore;
	}

	public void setAutore(Autore autore) {
		this.autore = autore;
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
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String ruolo) {
		this.role = ruolo;
	}
	
	public boolean isPoeta() {
		return this.role.equals(POETA_RUOLO);
	}
	
	public boolean isAutore() {
		return this.role.equals(AUTORE_RUOLO);
	}
}
