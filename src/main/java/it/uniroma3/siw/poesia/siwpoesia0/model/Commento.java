package it.uniroma3.siw.poesia.siwpoesia0.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Commento {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	
	private static int maxLenghtTitle=20;
	
	@NotBlank
	private String text;
	
	private static int maxLenghtText=150;
	
	private int miPiace;
	
	@ManyToOne
	private Autore autore;
	
	private LocalDate data;
	
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

	public static int getMaxLenghtTitle() {
		return maxLenghtTitle;
	}

	public static void setMaxLenghtTitle(int maxLenghtTitle) {
		Commento.maxLenghtTitle = maxLenghtTitle;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public static int getMaxLenghtText() {
		return maxLenghtText;
	}

	public static void setMaxLenghtText(int maxLenghtText) {
		Commento.maxLenghtText = maxLenghtText;
	}

	public int getMiPiace() {
		return miPiace;
	}

	public void setMiPiace(int miPiace) {
		this.miPiace = miPiace;
	}

	public Autore getUser() {
		return autore;
	}

	public void setUser(Autore user) {
		this.autore = user;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
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
