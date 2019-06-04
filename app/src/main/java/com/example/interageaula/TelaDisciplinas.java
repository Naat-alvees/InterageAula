package com.example.interageaula;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Hashtable;

public class TelaDisciplinas extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton btnAdicionar;
    private String codigoDisciplina, disciplina;
    private Hashtable<String, String> codigos;
    private LinearLayout layoutBotoes;
    private Button btnDisciplina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_disciplinas);
        codigos =  new Hashtable<String, String>();
        disciplina = null;

        btnAdicionar = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        btnAdicionar.setOnClickListener(this);

        layoutBotoes = (LinearLayout) findViewById(R.id.layoutBotoes);

        codigos.put("123", "Portugues");
        codigos.put("matematica01", "Matematica");
        codigos.put("biologia01", "Biologia");

        Intent i = getIntent();

        if(i != null){
            Bundle codigoRecebido = new Bundle();
            codigoRecebido = i.getExtras();

            if(codigoRecebido != null){
                codigoDisciplina = codigoRecebido.getString("CodigoDaDisciplina","Erro");
                disciplina = codigos.get(codigoDisciplina);
                if (disciplina != null) {
                    btnDisciplina = new Button(this);
                    btnDisciplina.setText(disciplina);
                    layoutBotoes.addView(btnDisciplina);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdicionar){
            Intent i = new Intent(this, AdicionaDisciplina.class);
            startActivity(i);
        }
    }
}
