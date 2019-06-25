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


public class RecycleViewDisciplinas extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdicionar;
    private FirebaseAuth autentificacao;
    private BancoDados bd;
    private AdapterDisciplinas adapter;
    private ArrayList<Disciplina> listaDisciplinas = new ArrayList<>();
    //dados no firebase
    private DatabaseReference referenciaDisciplinas = FirebaseDatabase.getInstance().getReference();
    private Disciplina disciplina = new Disciplina();
    private String codigoDisciplina;
    Boolean chamouAdicionaDisciplina = false;
    private List<Disciplina> listaCodigos =  new ArrayList<>();
    private String guardaValor;
    private SharedPreferences caixa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_disciplinas);

        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();



        recyclerView = findViewById(R.id.recyclerView);
        caixa = getSharedPreferences("chave1",0);
        bd = new BancoDados(getApplicationContext());

        //bd.deleta();

        //Configuração toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("InterageAula");
        setSupportActionBar(toolbar);
        //Fim da Configuração toolbar

        resgataDadosFirebase();

        listaDisciplinas = bd.buscarDisciplinasAluno();

        this.listarDisciplinas();

        btnAdicionar = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        btnAdicionar.setOnClickListener(this);

    }


    public void listarDisciplinas(){
        //Configurar adapter
        adapter = new AdapterDisciplinas(listaDisciplinas);

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
                    Disciplina disciplina = dados.getValue(Disciplina.class);
                    listaCodigos.add(disciplina);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public int checarCodigo(String codigo){
        codigoDisciplina = codigo;
        for(int k = 0; k < listaCodigos.size(); k++){
            guardaValor = listaCodigos.get(k).getCodigo();
            if(guardaValor.equals(codigo)){
                Disciplina novaDisciplina = new Disciplina(listaCodigos.get(k).getNome(),guardaValor);
                Log.d("TESTE","Nome: "+novaDisciplina.getNome()+" Código: "+novaDisciplina.getCodigo());
                bd.inserirDisciplinaAluno(novaDisciplina);
                return 1;
            }
        }
        return -1;
    }


    protected void onResume(){
        super.onResume();

        chamouAdicionaDisciplina = caixa.getBoolean("codigoVerifica",false);

        Log.d("TESTE",Boolean.toString(chamouAdicionaDisciplina));

        if(!chamouAdicionaDisciplina){
            SharedPreferences.Editor editor = caixa.edit();
            editor.putBoolean("codigoVerifica",true);


            String x = caixa.getString("codigo", null);

            if(checarCodigo(x) == -1 ){
                Toast.makeText(RecycleViewDisciplinas.this,"Código incorreto!",Toast.LENGTH_SHORT).show();
            }

            listaDisciplinas = bd.buscarDisciplinasAluno();
            listarDisciplinas();
        }
    }

}
