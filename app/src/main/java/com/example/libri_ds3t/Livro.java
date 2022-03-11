package com.example.libri_ds3t;

public class Livro {

    private String titulo, descricao;

    public Livro(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
