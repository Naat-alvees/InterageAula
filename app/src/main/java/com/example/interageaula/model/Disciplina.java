package com.example.interageaula.model;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String nome;
    private String codigo;

    public List<Roteiro> getRoteiros() {
        return roteiros;
    }

    public void setRoteiros(List<Roteiro> roteiros) {
        this.roteiros = roteiros;
    }

    private List<Roteiro> roteiros = new ArrayList<>();

    public Disciplina() {
    }

    public Disciplina(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
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
