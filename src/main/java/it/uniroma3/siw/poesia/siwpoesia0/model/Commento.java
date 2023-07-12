package it.uniroma3.siw.poesia.siwpoesia0.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Commento {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@NotBlank
	private String text;
	@ManyToOne
	private Autore autore;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private LocalDateTime data;
	@ManyToOne
	private Poesia poesia;
	
	public Autore getAutore() {
		return autore;
	}

	public void setAutore(Autore autore) {
		this.autore = autore;
	}

	public Poesia getPoesia() {
		return poesia;
	}

	public void setPoesia(Poesia poesia) {
		this.poesia = poesia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Autore getUser() {
		return autore;
	}

	public void setUser(Autore user) {
		this.autore = user;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, text);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commento other = (Commento) obj;
		return Objects.equals(data, other.data) && Objects.equals(text, other.text);
	}
}
