package com.example.interageaula;

import java.util.ArrayList;

public class Disciplina {
    private String nome;
    private String codigo;
    private ArrayList<Roteiro> roteiros;

    public ArrayList<Roteiro> getRoteiros() {
        return roteiros;
    }

    public void setRoteiros(ArrayList<Roteiro> roteiros) {
        this.roteiros = roteiros;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
