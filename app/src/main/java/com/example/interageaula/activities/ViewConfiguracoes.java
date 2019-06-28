package com.example.interageaula.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.interageaula.R;
import com.example.interageaula.bd.BancoDados;

public class ViewConfiguracoes extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btnReset;
    private BancoDados bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_configuracoes);
        bd = new BancoDados(getApplicationContext());
        btnReset = (LinearLayout) findViewById(R.id.resetDisciplinas);
        btnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnReset){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Atenção");
            builder.setMessage("Você deseja realmente exlcuir todas as diciplinas e seus conteudos?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    bd.deleta();
                    Intent i = new Intent(ViewConfiguracoes.this,ViewDisciplinas.class);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("Cancela", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
            AlertDialog alerta = builder.create();
            alerta.show();

        }
    }
}
