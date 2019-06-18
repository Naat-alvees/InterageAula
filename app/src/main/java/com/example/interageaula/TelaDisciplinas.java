package com.example.interageaula;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Hashtable;

public class TelaDisciplinas extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton btnAdicionar;
    private LinearLayout layoutBotoes;
    private Button btnDisciplina;
    private FirebaseAuth autentificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_disciplinas);

        btnAdicionar = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        btnAdicionar.setOnClickListener(this);

        layoutBotoes = (LinearLayout) findViewById(R.id.layoutBotoes);

        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        BancoDados bd = new BancoDados(getApplicationContext());

        //Configuração toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("InterageAula");
        setSupportActionBar(toolbar);
        //Fim da Configuração toolbar

        //Insere codigos de disciplinas
        CodigoDisciplina c1 = new CodigoDisciplina();
        c1.adicionaDisciplina("Matematica","123");
        CodigoDisciplina c2 = new CodigoDisciplina();
        c2.adicionaDisciplina("Portugues", "321");
        //bd.deleta();
        bd.inserirDisciplina(c1);
        bd.inserirDisciplina(c2);
        // fim inserção codigo

        this.listaDisciplinas(bd);

        Intent i = getIntent();
        if(i != null){
            Bundle codigoRecebido = new Bundle();
            codigoRecebido = i.getExtras();
            if(codigoRecebido != null){
                String codigoDisciplina = codigoRecebido.getString("CodigoDaDisciplina","Erro");
                String[] resultadoBD = bd.buscarDisciplina(codigoDisciplina);
                if (resultadoBD[0] == "1") {
                    Disciplina d = new Disciplina();
                    d.setNome(resultadoBD[1]);
                    d.setCodigo(codigoDisciplina);
                    bd.inserirDisciplinaAluno(d);
                    this.listaDisciplinas(bd);
//                    btnDisciplina = new Button(this);
//                    btnDisciplina.setText(disciplina);
//                    btnDisciplina.setOnClickListener(this);
//                    layoutBotoes.addView(btnDisciplina);
                } else {
                    Toast.makeText(TelaDisciplinas.this,"Código incorreto!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdicionar) {
            Intent i = new Intent(this, AdicionaDisciplina.class);
            startActivity(i);
        }
    }

    public void listaDisciplinas(BancoDados bd){
        layoutBotoes.removeAllViews();
        ArrayList<Disciplina> lista = new ArrayList<>();

        lista = bd.buscarDisciplinasAluno();

        for (int i = 0; i < lista.size(); i++){
            btnDisciplina = new Button(this);
            btnDisciplina.setText(lista.get(i).getNome());
            btnDisciplina.setOnClickListener(this);
            layoutBotoes.addView(btnDisciplina);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.MenuSair:
               deslogarUsuario();
               break;
            case R.id.MenuConfiguracao:
//                abrirConfiguracoes();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deslogarUsuario(){
        try{
            autentificacao.signOut();
            startActivity(new Intent(getApplicationContext(),TelaLogin.class));
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
