package com.example.interageaula.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.interageaula.R;
import com.example.interageaula.model.Usuario;
import com.example.interageaula.configuracoesFirebase.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ViewLogin extends Activity implements View.OnClickListener {
    private EditText campoEmail, campoSenha;
    private Button btnEntrar;
    private TextView txtCadastrese;
    private ProgressBar progressBar;
    private Usuario usuario;
    private FirebaseAuth autentificacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        verificarUsuarioLogado();
        inicializaComponentes();
        txtCadastrese.setOnClickListener(this);

        //Fazer login do usuario
        progressBar.setVisibility(View.GONE);
        btnEntrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoEmail.isEmpty()){
                    if (!textoSenha.isEmpty()){

                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);

                        validarLogin(usuario);

                    }else {
                        Toast.makeText(ViewLogin.this,"Preencha a senha!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ViewLogin.this,"Preencha o e-mail!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verificarUsuarioLogado(){
        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        if (autentificacao.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), ViewDisciplinas.class));
            finish();
        }
    }

    public void validarLogin(Usuario usuario) {
        progressBar.setVisibility(View.VISIBLE);
        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autentificacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), ViewDisciplinas.class));
                    finish();
                } else {
                    Toast.makeText(ViewLogin.this, "Usuário ou senha incorreto", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void inicializaComponentes(){
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
        btnEntrar = findViewById(R.id.buttonEntrar);
        txtCadastrese = findViewById(R.id.textCadastrese);
        progressBar = findViewById(R.id.progressLogin);
        campoEmail.requestFocus();

    }

    @Override
    public void onClick(View v) {
        if (v == txtCadastrese){
            Intent i = new Intent(this, ViewCadastro.class);
            startActivity(i);
        }
    }
}
