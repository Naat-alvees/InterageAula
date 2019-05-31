package com.example.interageaula;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaDisciplinas extends AppCompatActivity implements View.OnClickListener{
    private Button btnPort, btnMat, btnBio;
    private FloatingActionButton btnAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_disciplinas);

        btnPort = (Button) findViewById(R.id.buttonPort);
        btnPort.setOnClickListener(this);

        btnMat = (Button) findViewById(R.id.buttonMat);
        btnMat.setOnClickListener(this);

        btnBio = (Button) findViewById(R.id.buttonBio);
        btnBio.setOnClickListener(this);

        btnAdicionar = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        btnAdicionar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnPort){
            Intent i = new Intent(this, TelaRoteiros.class);
            startActivity(i);
        }
        if(v == btnAdicionar){
            Intent i = new Intent(this, AdicionaDisciplina.class);
            startActivity(i);
        }
    }
}
