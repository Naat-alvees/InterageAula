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


public class RecycleViewDisciplinas extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdicionar;
    private FirebaseAuth autentificacao;

    private List<Disciplina> listarDisciplinas =  new ArrayList<>();


    private AdapterDisciplinas adapter;
    //dados no firebase
    private DatabaseReference referenciaDisciplinas = FirebaseDatabase.getInstance().getReference();
    private Disciplina disciplina = new Disciplina();
    private String codigoDisciplina;

    private List<Disciplina> listaCodigos =  new ArrayList<>();
    private String guardaValor;
    private SharedPreferences caixa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_disciplinas);

        recyclerView = findViewById(R.id.recyclerView);
        caixa = getSharedPreferences("chave1",0);

        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Configuração toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("InterageAula");
        setSupportActionBar(toolbar);
        //Fim da Configuração toolbar

        //Adicionando dados no firebase
//        DatabaseReference roteiros = referenciaDisciplinas.child("roteiros");
//        Roteiro roteiro = new Roteiro();
//        List<Roteiro> listaDeRoteiros = new ArrayList<>();
//        roteiro.setTituloRoteiro("Gramática");
//        roteiro.setSubtituloRoteiro("Roteiro 1");
//        roteiro.setDataRoteiro("22/06/2019");
//        listaDeRoteiros.add(roteiro);
//        roteiros.push().setValue(roteiro);
//
//        Roteiro roteiro1 = new Roteiro();
//        List<Roteiro> listaDeRoteiros1 = new ArrayList<>();
//        roteiro1.setTituloRoteiro("Numeros Complexos");
//        roteiro1.setSubtituloRoteiro("Roteiro 2");
//        roteiro1.setDataRoteiro("23/06/2019");
//        listaDeRoteiros1.add(roteiro);
//        roteiros.push().setValue(roteiro1);
//
//
//        DatabaseReference disciplinas = referenciaDisciplinas.child("disciplinas");
//
//        Disciplina disciplina = new Disciplina();
//        disciplina.setNome("Portugues");
//        disciplina.setCodigo("123");
//        disciplina.setRoteiros(listaDeRoteiros);
//        this.listarDisciplinas.add(disciplina);
//
//        disciplinas.push().setValue(disciplina);
//
//        Disciplina disciplina1 = new Disciplina();
//        disciplina1.setNome("Matematica");
//        disciplina1.setCodigo("321");
//        disciplina1.setRoteiros(listaDeRoteiros1);
//        this.listarDisciplinas.add(disciplina1);
//
//        disciplinas.push().setValue(disciplina1);
        //metodo push() gera identificadores unicos no firebase
        //fim do adicionar dados no firebase



        resgataDadosFirebase();


        //Configurar adapter
        adapter = new AdapterDisciplinas(listarDisciplinas);
        Log.i("FIREBASE","Nos: "+Integer.toString(listarDisciplinas.size()));

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
//                                Intent i = new Intent(RecycleViewDisciplinas.this,RecycleViewRoteiros.class);
//////                                Bundle enviaDisciplina = new Bundle();
//////                                enviaDisciplina.putString("nomeDisciplina", codigoDisciplina);
//////                                i.putExtras(enviaDisciplina);
//////                                startActivity(i);
//                                SharedPreferences.Editor editor = caixa.edit();
//                                editor.putString("codigo",codigo);
//                                editor.commit();
                                Intent i = new Intent(RecycleViewDisciplinas.this,RecycleViewRoteiros.class);
                                Disciplina disciplinaSelecionada = listarDisciplinas.get(position);
                                SharedPreferences.Editor editor = caixa.edit();
                                editor.putString("nomeDisciplina",disciplinaSelecionada.getNome());
                                editor.commit();

                                startActivity(i);
                                //Toast.makeText(getApplicationContext(),"Botao pressionado"+disciplinaSelecionada.getNome(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            }
                        }
                )
        );

        btnAdicionar = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        btnAdicionar.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        if(v == btnAdicionar) {
            Intent i = new Intent(this, AdicionaDisciplina.class);
            startActivity(i);
        }
    }

    public void resgataDadosFirebase(){

        DatabaseReference disciplinas = referenciaDisciplinas.child("disciplinas");

        disciplinas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaCodigos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    //Log.i("VALOR","retorno: "+dados.toString());
                    Disciplina disciplina = dados.getValue(Disciplina.class);
                    listaCodigos.add(disciplina);
                }
                //adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void checarCodigo(String codigo){


        codigoDisciplina = codigo;

        for(int k = 0; k < listaCodigos.size(); k++){
            guardaValor = listaCodigos.get(k).getCodigo();

            if(guardaValor.equals(codigo)){
                disciplina.setNome(listaCodigos.get(k).getNome());
                listarDisciplinas.add(disciplina);
            }

        }

    }

    protected void onStart(){
        super.onStart();
        Log.i("FIREBASE", "AQUI");
    }

    protected void onRestart(){
        super.onRestart();
        Log.i("FIREBASE", "AQUI 1");
    }

    protected void onResume(){
        super.onResume();
        Log.i("FIREBASE", "AQUI 2");
        String x = caixa.getString("codigo", null);
        checarCodigo(x);
    }

}
