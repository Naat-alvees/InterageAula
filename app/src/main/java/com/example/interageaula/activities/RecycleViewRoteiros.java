package com.example.interageaula.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
import android.widget.LinearLayout;

import com.example.interageaula.R;
import com.example.interageaula.adapter.AdapterRoteiros;
import com.example.interageaula.configuracoesFirebase.ConfiguracaoFirebase;
import com.example.interageaula.model.Disciplina;
import com.example.interageaula.model.Roteiro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewRoteiros extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth autentificacao;

    private List<Roteiro> listaRoteiros = new ArrayList<>();
    private List<Disciplina> listaCodigos =  new ArrayList<>();
    private List<Roteiro> listaRoteirosFirebase = new ArrayList<>();
    private Roteiro roteiros = new Roteiro();
    private String codigoDisciplina;
    private String guardaValor;
    private SharedPreferences caixa;
    private DatabaseReference referenciaDisciplinas = FirebaseDatabase.getInstance().getReference();
    private String bundleRecebido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_roteiros);

        recyclerView = findViewById(R.id.recyclerView2);
        caixa = getSharedPreferences("chave1",0);

        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Configuração toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("InterageAula");
        setSupportActionBar(toolbar);
        //Fim da Configuração toolbar

//        this.criarRoteiros();
        bundleRecebido = caixa.getString("nomeDisciplina", null);

        ///estamos aqui
        resgataDadosFirebase();
        //Log.i("FIREBASE", "Lista de codigos: "+Integer.toString(listaCodigos.size()));

      //  Log.i("FIREBASE", "x "+x);
        //checarCodigo(x);



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

    public void resgataDadosFirebase(){

        DatabaseReference disciplinas = referenciaDisciplinas.child("disciplinas");
        DatabaseReference roteiros = referenciaDisciplinas.child("roteiros");
        disciplinas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //listaCodigos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Log.i("FIREBASE","Retorno resgatado no escutador: "+dados.toString());
                    Disciplina disciplina = dados.getValue(Disciplina.class);
                    listaCodigos.add(disciplina);


                }
                for (int i = 0; i < listaCodigos.size(); i++){
                    Log.i("FIREBASE","For varreando a lista: "+ listaCodigos.get(i).getNome());

                }
                //adapter.notifyDataSetChanged();
                Log.i("FIREBASE","Tamanho da lista ainda dentro do escutador:::::::::::::::::::::::::::::::: "+ listaCodigos.size());
                checarCodigo(bundleRecebido);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("FIREBASE","ERRO!!!!!!!!!!!!!");

            }
        });
        Log.i("FIREBASE"," Fora do escutador$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");


            Log.i("FIREBASE","Tamanho fora do escutador:::::::::::::::::::::::::::::::: "+ listaCodigos.size());

        roteiros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //listaRoteirosFirebase.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Log.i("FIREBASE","retorno1: "+dados.toString());
                    Roteiro roteiro = dados.getValue(Roteiro.class);
                    listaRoteirosFirebase.add(roteiro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    protected void onStart(){
        super.onStart();
        Log.i("FIREBASE", "AQUI");

    }

    protected void onRestart(){
        super.onRestart();
        Log.i("FIREBASE", "AQUI 1");
    }

//    protected void onResume(){
//        super.onResume();
//        Log.i("FIREBASE", "AQUI 2");
////        String x = caixa.getString("codigo", null);
////        checarCodigo(x);
//    }

    public void checarCodigo(String nome){
        //resgataDadosFirebase();

        codigoDisciplina = nome;
        Log.i("FIREBASE", "!!!!!!! Estamos dentro do checar codigo"+Integer.toString(listaCodigos.size()));
        for(int k = 0; k < listaCodigos.size(); k++){
            guardaValor = listaCodigos.get(k).getNome();
            Log.i("FIREBASE", "minha lista: "+listaCodigos.get(k).getNome()+" comparando com "+codigoDisciplina);
            if(guardaValor.compareToIgnoreCase(codigoDisciplina) == 0){
                Log.i("FIREBASE", "dentro do if");
                for (int i = 0; i < listaCodigos.get(k).getRoteiros().size(); i++){

                    roteiros.setTituloRoteiro(listaCodigos.get(k).getRoteiros().get(i).getTituloRoteiro());
                    roteiros.setSubtituloRoteiro(listaCodigos.get(k).getRoteiros().get(i).getSubtituloRoteiro());
                    roteiros.setDataRoteiro(listaCodigos.get(k).getRoteiros().get(i).getDataRoteiro());
                    listaRoteiros.add(roteiros);
                }

            }

        }

        montaTelaRoteiro();

    }
    public void montaTelaRoteiro(){
        //Configurar adapter
        AdapterRoteiros adapter = new AdapterRoteiros(listaRoteiros);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }


    protected void onResume(){
        super.onResume();
        Log.i("FIREBASE", "roteiros entrou");

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
