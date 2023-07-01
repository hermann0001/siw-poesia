package it.uniroma3.siw.poesia.siwpoesia0.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames= {"titolo", "autore_id"}))
public class Poesia {
	
	//attributi
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 2000)
	@NotBlank
	@Size(max=2000)
	private String testo;
	
	@Column(nullable = false)
	@NotBlank
	private String titolo;
	
	private String foto;
	
	@Column(nullable = false)
	@PastOrPresent
	private LocalDate data_pubblicazione;
	
	//associazioni
	@ManyToOne
	@NotNull
	private Autore autore;
	
	//private List<Tag> tags;
	
	@OneToMany(mappedBy = "poesia")
	private List<Commento> commenti;
	
	public Poesia() {
	}
	
	public Poesia(String testo, String titolo, String foto, LocalDate data_pubblicazione) {
		this.testo = testo;
		this.titolo = titolo;
		this.foto = foto;
		this.data_pubblicazione = data_pubblicazione;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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
		return "Poesia [id=" + id + ", testo=" + testo + ", titolo=" + titolo + ", url_foto=" + foto
				+ ", data_pubblicazione=" + data_pubblicazione + ", autore=" + autore + "]";
	}
}
