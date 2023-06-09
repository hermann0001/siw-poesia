package it.uniroma3.siw.poesia.siwpoesia0.model;

import jakarta.persistence.*;

@Entity
public class Immagine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private byte[] data;
    private String nome;
    private String tipo;

    public Immagine(){

    }

    public Immagine(String nome,String type, byte[] data){
        this.data = data;
        this.tipo = type;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String type) {
        this.tipo = type;
    }
}
