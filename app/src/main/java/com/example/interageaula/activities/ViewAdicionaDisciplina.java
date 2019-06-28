package com.example.interageaula.activities;

import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
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


public class ViewAdicionaDisciplina extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdiciona;
    private EditText codigoDisciplina;
    private List<Disciplina> listaCodigos =  new ArrayList<>();
    private DatabaseReference referenciaDisciplinas = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_disciplina);


        resgataDadosFirebase();

        btnAdiciona = (Button) findViewById(R.id.button);
        btnAdiciona.setOnClickListener(this);

        codigoDisciplina = (EditText) findViewById(R.id.editText2);

    }

    @Override
    public void onClick(View v) {
        if(v == btnAdiciona){
            String codigo = codigoDisciplina.getText().toString();

            Disciplina disciplinaAdd = checarCodigo(codigo);
            if(disciplinaAdd != null){
                Intent i = new Intent(this,ViewDisciplinas.class);
                Bundle box = new Bundle();
                box.putString("nomeDisciplina",disciplinaAdd.getNome());
                box.putString("codigoDisciplina",disciplinaAdd.getCodigo());
                i.putExtras(box);
                startActivity(i);
                this.finish();
            } else{
                Toast.makeText(ViewAdicionaDisciplina.this,"Código incorreto!",Toast.LENGTH_SHORT).show();
            }
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

    public Disciplina checarCodigo(String codigoDisciplina){
        String codigoTemp;
        for(int k = 0; k < listaCodigos.size(); k++){
            codigoTemp = listaCodigos.get(k).getCodigo();
            if(codigoTemp.equals(codigoDisciplina)){
                Disciplina novaDisciplina = new Disciplina(listaCodigos.get(k).getNome(),codigoTemp);
                return novaDisciplina;
            }
        }
        return null;
    }
}
