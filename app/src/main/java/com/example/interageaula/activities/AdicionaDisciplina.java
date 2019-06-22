package com.example.interageaula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdicionaDisciplina extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdiciona;
    private EditText codigoDisciplina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_disciplina);

        btnAdiciona = (Button) findViewById(R.id.button);
        btnAdiciona.setOnClickListener(this);

        codigoDisciplina = (EditText) findViewById(R.id.editText2);

    }

    @Override
    public void onClick(View v) {
        if(v == btnAdiciona){
            // Verificar o codigo da disciplina
            Intent i = new Intent(this, TelaDisciplinas.class);

            Bundle enviaCodigo = new Bundle();
            enviaCodigo.putString("CodigoDaDisciplina",codigoDisciplina.getText().toString());
            i.putExtras(enviaCodigo);

            startActivity(i);
            this.finish();

        }
    }
}
