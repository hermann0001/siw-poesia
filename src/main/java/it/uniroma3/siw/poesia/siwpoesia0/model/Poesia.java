package it.uniroma3.siw.poesia.siwpoesia0.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames= {"titolo", "autore_id"}))
public class Poesia {
	//attributi
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	@NotBlank
	private String testo;
	
	@Column(nullable = false)
	@NotBlank
	private String titolo;

	@OneToOne(cascade = CascadeType.ALL)
	private Immagine foto;
	
	@Column(nullable = false)
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPubblicazione;
	
	//associazioni
	@ManyToOne
	private Autore autore;
	
	//private List<Tag> tags;
	
	@OneToMany(mappedBy = "poesia")
	private List<Commento> commenti;
	
	public Poesia() {
	}
	
	public Poesia(String testo, String titolo, Immagine foto, LocalDate dataPubblicazione) {
		this.testo = testo;
		this.titolo = titolo;
		this.foto = foto;
		this.dataPubblicazione = dataPubblicazione;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
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

	public Immagine getFoto() {
		return foto;
	}

	public void setFoto(Immagine file) {
		this.foto = file;
	}

	public LocalDate getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(LocalDate data_pubblicazione) {
		this.dataPubblicazione = data_pubblicazione;
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
		return "Poesia [id=" + id + ", testo=" + testo + ", titolo=" + titolo + ", foto=" + foto
				+ ", data_pubblicazione=" + dataPubblicazione + ", autore=" + autore + "]";
	}
}
