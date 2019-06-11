package com.example.interageaula;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
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

        btnAdicionar = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        btnAdicionar.setOnClickListener(this);

        layoutBotoes = (LinearLayout) findViewById(R.id.layoutBotoes);

        BancoDados bd = new BancoDados(getApplicationContext());
        CodigoDisciplina c1 = new CodigoDisciplina();
        c1.adicionaDisciplina("Matematica","123");

        CodigoDisciplina c2 = new CodigoDisciplina();
        c2.adicionaDisciplina("Portugues", "321");



        //bd.deleta();
        bd.inserirDisciplina(c1);
        bd.inserirDisciplina(c2);

//        ArrayList<Disciplina> lista = new ArrayList<>();
//
//        lista = bd.buscarDisciplinasAluno();
//
//        for (int i = 0; i < lista.size(); i++){
//            btnDisciplina = new Button(this);
//            btnDisciplina.setText(lista.get(i).getNome());
//            btnDisciplina.setOnClickListener(this);
//            layoutBotoes.addView(btnDisciplina);
//        }

        this.lista(bd);
        Intent i = getIntent();

        if(i != null){
            Bundle codigoRecebido = new Bundle();
            codigoRecebido = i.getExtras();

            if(codigoRecebido != null){
                codigoDisciplina = codigoRecebido.getString("CodigoDaDisciplina","Erro");
                disciplina = bd.buscarDisciplina(codigoDisciplina);
                if (disciplina != "") {
                    Disciplina d = new Disciplina();
                    d.setNome(disciplina);
                    d.setCodigo(codigoDisciplina);
                    bd.inserirDisciplinaAluno(d);
                    this.lista(bd);
//                    btnDisciplina = new Button(this);
//                    btnDisciplina.setText(disciplina);
//                    btnDisciplina.setOnClickListener(this);
//                    layoutBotoes.addView(btnDisciplina);
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

    public void lista(BancoDados bd){
        layoutBotoes.removeAllViews();
        ArrayList<Disciplina> lista = new ArrayList<>();

        lista = bd.buscarDisciplinasAluno();

        for (int i = 0; i < lista.size(); i++){
            Log.d("TAG","lista: "+lista.get(i).getNome());
            btnDisciplina = new Button(this);
            btnDisciplina.setText(lista.get(i).getNome());
            btnDisciplina.setOnClickListener(this);
            layoutBotoes.addView(btnDisciplina);
        }
    }
}
