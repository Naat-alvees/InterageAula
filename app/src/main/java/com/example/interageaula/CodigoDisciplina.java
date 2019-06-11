package com.example.interageaula;

import java.util.HashMap;
import java.util.Random;

public class CodigoDisciplina {
    private String nome;
    private String codigo;

    public CodigoDisciplina(){
        this.nome = "";
        this.codigo = "";
    }

     /*private String geraCodigo(){
        Random numerosAleatorios = new Random();
        String letras = "";
        int index = -1;
        for (int i = 0; i < 4; i++){
            index = numerosAleatorios.nextInt(26);
            letras += 'a'+index;
        }

        return letras;
    }*/

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
