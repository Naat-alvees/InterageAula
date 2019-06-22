package com.example.interageaula.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.interageaula.R;
import com.example.interageaula.adapter.AdapterRoteiros;
import com.example.interageaula.configuracoesFirebase.ConfiguracaoFirebase;
import com.example.interageaula.model.Roteiro;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewRoteiros extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth autentificacao;

    private List<Roteiro> listaRoteiros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_roteiros);

        recyclerView = findViewById(R.id.recyclerView2);

        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Configuração toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("InterageAula");
        setSupportActionBar(toolbar);
        //Fim da Configuração toolbar

//        this.criarRoteiros();

        //Configurar adapter
        AdapterRoteiros adapter = new AdapterRoteiros(listaRoteiros);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
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
            startActivity(new Intent(getApplicationContext(), TelaLogin.class));
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public void criarRoteiros(){
//        Roteiro roteiro = new Roteiro();
//        roteiro.setTituloRoteiro("Gramática");
//        roteiro.setSubtituloRoteiro("Roteiro 1");
//        roteiro.setDataRoteiro("22/06/2019");
//        this.listaRoteiros.add(roteiro);
//
//        Roteiro roteiro1 = new Roteiro();
//        roteiro1.setTituloRoteiro("Semântica");
//        roteiro1.setSubtituloRoteiro("Roteiro 2");
//        roteiro1.setDataRoteiro("23/06/2019");
//        this.listaRoteiros.add(roteiro1);
//
//    }
}
