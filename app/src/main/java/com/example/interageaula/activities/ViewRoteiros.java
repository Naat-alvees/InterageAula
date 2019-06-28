package com.example.interageaula.activities;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.interageaula.R;
import com.example.interageaula.adapter.AdapterRoteiros;
import com.example.interageaula.configuracoesFirebase.ConfiguracaoFirebase;
import com.example.interageaula.eventos.RecyclerItemClickListener;
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

public class ViewRoteiros extends AppCompatActivity {
    private TextView tituloDisciplina;
    private RecyclerView recyclerView;
    private FirebaseAuth autentificacao;
    Disciplina disciplinaRecebida;
    private List<Roteiro> listaRoteiros = new ArrayList<>();
    private List<Disciplina> listaCodigos =  new ArrayList<>();
    private Roteiro roteiros = new Roteiro();
    private DatabaseReference referenciaDisciplinas = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_roteiros);

        inicializaComponentes();

        Intent recebe = getIntent();
        if(recebe != null){
            Bundle bundleRecebido;
            bundleRecebido = recebe.getExtras();
            if(bundleRecebido != null){
                String nomeDisciplina = bundleRecebido. getString("nomeDisciplina", null);
                String codDisciplina = bundleRecebido. getString("codigoDisciplina", null);
                disciplinaRecebida = new Disciplina(nomeDisciplina, codDisciplina);
            }
        }

        resgataDadosFirebase();

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
            startActivity(new Intent(getApplicationContext(), ViewLogin.class));
            finish();
        }catch (Exception e){
            e.printStackTrace();
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
                    if(disciplina.getCodigo().compareToIgnoreCase(disciplinaRecebida.getCodigo()) == 0){
                        disciplinaRecebida = disciplina;
                    }
                }
                carregaRoteiros();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void carregaRoteiros(){
        for (int i = 0; i < disciplinaRecebida.getRoteiros().size(); i++){
            roteiros.setTituloRoteiro(disciplinaRecebida.getRoteiros().get(i).getTituloRoteiro());
            roteiros.setSubtituloRoteiro(disciplinaRecebida.getRoteiros().get(i).getSubtituloRoteiro());
            roteiros.setDataRoteiro(disciplinaRecebida.getRoteiros().get(i).getDataRoteiro());
            listaRoteiros.add(roteiros);
        }

        montaTelaRoteiro();
    }

    public void montaTelaRoteiro(){
        //Configurar adapter
        AdapterRoteiros adapter = new AdapterRoteiros(listaRoteiros);

        tituloDisciplina.setText(disciplinaRecebida.getNome());
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
//                                Intent i = new Intent(ViewDisciplinas.this,ViewRoteiros.class);
//                                Bundle box = new Bundle();
//                                box.putString("nomeDisciplina",listaDisciplinas.get(position).getNome());
//                                box.putString("codigoDisciplina",listaDisciplinas.get(position).getCodigo());
//                                i.putExtras(box);
//                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {}

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
                        }
                )
        );
    }

    public void inicializaComponentes(){
        recyclerView = findViewById(R.id.recyclerView2);
        tituloDisciplina = (TextView) findViewById(R.id.tituloDisciplina);
        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("Roteiros");
//        setSupportActionBar(toolbar);
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
