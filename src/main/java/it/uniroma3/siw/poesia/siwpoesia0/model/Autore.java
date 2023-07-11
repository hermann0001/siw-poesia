package it.uniroma3.siw.poesia.siwpoesia0.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Autore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//attributi
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@Column(nullable = false, unique = true)
	@NotNull
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	private Immagine foto;
	
	//associazioni
	@OneToMany(mappedBy = "autore")
	private List<Poesia> poesie;

	@OneToMany(mappedBy="autore", cascade = CascadeType.ALL)
	private List<Commento> commenti;
	
	public Autore() {
	}
	
	public Autore(String nome,String cognome, String email, Immagine foto) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.foto = foto;
		this.commenti = new LinkedList<>();
		this.poesie = new LinkedList<>();
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

	public Immagine getFoto() {
		return foto;
	}

	public void setFoto(Immagine foto) {
		this.foto = foto;
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
