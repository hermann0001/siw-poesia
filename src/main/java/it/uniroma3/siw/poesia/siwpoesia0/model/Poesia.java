package it.uniroma3.siw.poesia.siwpoesia0.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames= {"titolo", "autore_id"}))
public class Poesia {
	
	//attributi
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 2000)
	private String testo;
	
	@Column(nullable = false)
	private String titolo;
	
	
	private String url_foto;
	
	@Column(nullable = false)
	private LocalDate data_pubblicazione;
	
	//associazioni
	@ManyToOne
	private Autore autore;
	//private List<Tag> tags;
	//private List<Commento> commenti
	
	public Poesia() {
	}
	
	public Poesia(String testo, String titolo, String url_foto, LocalDate data_pubblicazione) {
		this.testo = testo;
		this.titolo = titolo;
		this.url_foto = url_foto;
		this.data_pubblicazione = data_pubblicazione;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getUrl_foto() {
		return url_foto;
	}

	public void setUrl_foto(String url_foto) {
		this.url_foto = url_foto;
	}

	public LocalDate getData_pubblicazione() {
		return data_pubblicazione;
	}

	public void setData_pubblicazione(LocalDate data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
	}
	
	public void setAutore(Autore autore) {
		this.autore = autore;
	}
	
	public Autore getAutore() {
		return this.autore;
	}

	@Override
	public int hashCode() {
		return Objects.hash(autore, titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poesia other = (Poesia) obj;
		return Objects.equals(autore, other.autore) && Objects.equals(titolo, other.titolo);
	}

	@Override
	public String toString() {
		return "Poesia [id=" + id + ", testo=" + testo + ", titolo=" + titolo + ", url_foto=" + url_foto
				+ ", data_pubblicazione=" + data_pubblicazione + ", autore=" + autore + "]";
	}
}
