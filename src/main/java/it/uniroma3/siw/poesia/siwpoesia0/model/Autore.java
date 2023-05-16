package it.uniroma3.siw.poesia.siwpoesia0.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Autore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//attributi
	@Column(nullable = false, unique = true)
	private String nome;
	
	
	private String descrizione;
	
	
	private String url_foto;
	
	//associazioni
	@OneToMany(mappedBy = "autore")
	private List<Poesia> poesie;
	//Poeta ?
	//Utente ?
	
	public Autore() {
	}
	
	public Autore(String nome, String descrizione, String url_foto) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.url_foto = url_foto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
		return Objects.hash(nome);
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
		return Objects.equals(nome, other.nome);
	}
	
	
}
