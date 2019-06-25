package com.example.interageaula.activities;

import android.content.Intent;

import android.content.SharedPreferences;
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



    //dados no firebase
    private DatabaseReference referenciaDisciplinas = FirebaseDatabase.getInstance().getReference();
    private String codigo;
    private SharedPreferences caixa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_disciplina);

        caixa = getSharedPreferences("chave1",0);



        btnAdiciona = (Button) findViewById(R.id.button);
        btnAdiciona.setOnClickListener(this);

        codigoDisciplina = (EditText) findViewById(R.id.editText2);

    }

    @Override
    public void onClick(View v) {
        if(v == btnAdiciona){
            codigo = codigoDisciplina.getText().toString();


            SharedPreferences.Editor editor = caixa.edit();
            editor.putBoolean("codigoVerifica",false);
            editor.putString("codigo",codigo);
            editor.commit();

            this.finish();

        }
    }
}
