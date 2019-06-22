package com.example.interageaula.model;

import java.util.HashMap;
import java.util.Random;

public class CodigoDisciplina {
    private String nome;
    private String codigo;

    public CodigoDisciplina(){
        this.nome = "";
        this.codigo = "";
    }


    public void adicionaDisciplina(String nome, String codigo){
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }


    public String getNome() {
        return nome;
    }
}
