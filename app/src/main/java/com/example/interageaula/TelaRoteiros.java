package com.example.interageaula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class TelaRoteiros extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout layoutRot1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_roteiros);

        layoutRot1 = (LinearLayout) findViewById(R.id.layoutRoteiro1);
        layoutRot1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == layoutRot1){
            Intent i = new Intent(this,TelaConteudos.class);
            startActivity(i);
        }
    }
}
