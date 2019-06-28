package com.example.interageaula.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.interageaula.R;
import com.example.interageaula.adapter.AdapterDisciplinas;
import com.example.interageaula.bd.BancoDados;
import com.example.interageaula.configuracoesFirebase.ConfiguracaoFirebase;
import com.example.interageaula.eventos.RecyclerItemClickListener;
import com.example.interageaula.model.Disciplina;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewDisciplinas extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdicionar;
    private FirebaseAuth autentificacao;
    private BancoDados bd;
    private ArrayList<Disciplina> listaDisciplinas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_disciplinas);

        //bd.deleta();

        this.inicializaComponentes();

        Intent recebe = getIntent();
        if(recebe != null){
            Bundle bundleRecebido;
            bundleRecebido = recebe.getExtras();
            if(bundleRecebido != null){
                String nomeDisciplina = bundleRecebido. getString("nomeDisciplina", null);
                String codDisciplina = bundleRecebido. getString("codigoDisciplina", null);
                Disciplina disciplinaRecebida = new Disciplina(nomeDisciplina, codDisciplina);
                bd.inserirDisciplinaAluno(disciplinaRecebida);

            }
        }

        listaDisciplinas = bd.buscarDisciplinasAluno();

        this.listarDisciplinas();

    }


    public void listarDisciplinas(){
        //Configurar adapter
        AdapterDisciplinas adapter = new AdapterDisciplinas(listaDisciplinas);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

        //evento de click
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {
                                Intent i = new Intent(ViewDisciplinas.this,ViewRoteiros.class);
                                Bundle box = new Bundle();
                                box.putString("nomeDisciplina",listaDisciplinas.get(position).getNome());
                                box.putString("codigoDisciplina",listaDisciplinas.get(position).getCodigo());
                                i.putExtras(box);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {}

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
                        }
                )
        );
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
                Intent i = new Intent(this,ViewConfiguracoes.class);
                startActivity(i);
                this.finish();
                break;
//                abrirConfiguracoes();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deslogarUsuario(){
        try{
            autentificacao.signOut();
            startActivity(new Intent(getApplicationContext(), ViewLogin.class));
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdicionar) {
            Intent i = new Intent(this, ViewAdicionaDisciplina.class);
            startActivity(i);
        }
    }

    public void inicializaComponentes(){
        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        bd = new BancoDados(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Minhas disciplinas");
        setSupportActionBar(toolbar);
        btnAdicionar = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        btnAdicionar.setOnClickListener(this);
    }

}
