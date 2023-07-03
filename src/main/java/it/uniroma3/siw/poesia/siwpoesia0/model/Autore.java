package it.uniroma3.siw.poesia.siwpoesia0.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Autore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//attributi
	private String nome;
	private String cognome;
	@Column(nullable = false, unique = true)
	@NotBlank
	private String email;

	private String url_foto;
	
	//associazioni
	@OneToMany(mappedBy = "autore")
	private List<Poesia> poesie;

	@OneToMany(mappedBy="autore", cascade = CascadeType.ALL)
	private List<Commento> commenti;
	
	public Autore() {
	}
	
	public Autore(String nome,String cognome, String email, String url_foto) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.url_foto = url_foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Autore autore = (Autore) o;
		return Objects.equals(email, autore.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public String toString() {
		return "Autore{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", cognome='" + cognome + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
