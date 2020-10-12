package com.example.hackathon;

public class itemRelatorio {
    private String valor;
    private String categoria;

    public itemRelatorio() {

    }
    public itemRelatorio(String categoria, String valor) {
        this.valor = valor;
        this.categoria = categoria;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
