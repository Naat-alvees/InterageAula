package com.example.interageaula;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaLogin extends Activity implements View.OnClickListener {
    private Button btnEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        btnEntrar = (Button) findViewById(R.id.buttonEntrar);
        btnEntrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnEntrar){
            Intent i = new Intent(this,TelaDisciplinas.class);
            startActivity(i);
        }
    }
}
