package it.uniroma3.siw.poesia.siwpoesia0.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Autore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//attributi
	@Column(nullable = false, unique = true)
	@NotBlank
	private String username;
	
	private String email;


	private String url_foto;
	
	//associazioni
	@OneToMany(mappedBy = "autore")
	private List<Poesia> poesie;

	@OneToMany(mappedBy="autore")
	private List<Commento> commenti;
	//Poeta ?
	//Utente ?
	
	public Autore() {
	}
	
	public Autore(String nome, String url_foto) {
		this.username = nome;
		this.url_foto = url_foto;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	public List<Poesia> getPoesie() {
		return poesie;
	}

	public void setPoesie(List<Poesia> poesie) {
		this.poesie = poesie;
	}

	public String getUrl_foto() {
		return url_foto;
	}

	public void setUrl_foto(String url_foto) {
		this.url_foto = url_foto;
	}
	
	public void aggiungiPoesia(Poesia p) {
		this.poesie.add(p);
	}
	
	
	//hash code e equals su NOME che deve essere unico
	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autore other = (Autore) obj;
		return Objects.equals(username, other.username);
	}
	
	
}
