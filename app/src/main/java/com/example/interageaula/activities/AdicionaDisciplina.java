package com.example.interageaula.activities;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.interageaula.R;

import com.example.interageaula.adapter.AdapterDisciplinas;
import com.example.interageaula.model.Disciplina;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AdicionaDisciplina extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdiciona;
    private EditText codigoDisciplina;

    private List<Disciplina> listaCodigos =  new ArrayList<>();;

    //dados no firebase
    private DatabaseReference referenciaDisciplinas = FirebaseDatabase.getInstance().getReference();
    private String codigo;
    private String guardaValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_disciplina);
        DatabaseReference disciplinas = referenciaDisciplinas.child("disciplinas");

        disciplinas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaCodigos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    //Log.i("VALOR","retorno: "+dados.toString());
                    Disciplina disciplina = dados.getValue(Disciplina.class);
                    //Log.i("FIREBASE","dados: "+disciplina.getCodigo());
                    listaCodigos.add(disciplina);
                }

                //adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnAdiciona = (Button) findViewById(R.id.button);
        btnAdiciona.setOnClickListener(this);

        codigoDisciplina = (EditText) findViewById(R.id.editText2);

    }

    @Override
    public void onClick(View v) {
        if(v == btnAdiciona){
            codigo = codigoDisciplina.getText().toString();

            for(int k = 0; k < listaCodigos.size(); k++){
                guardaValor = listaCodigos.get(k).getCodigo();

                if(guardaValor.equals(codigo)){
                    Intent i = new Intent(this, RecycleViewDisciplinas.class);
                    Bundle enviaCodigo = new Bundle();
                    enviaCodigo.putString("CodigoDaDisciplina",listaCodigos.get(k).getNome());
                    i.putExtras(enviaCodigo);
                    startActivity(i);
                    this.finish();
                }

            }

        }
    }
}
