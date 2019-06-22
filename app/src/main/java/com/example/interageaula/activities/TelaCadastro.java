package com.example.interageaula.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.interageaula.R;
import com.example.interageaula.configuracoesFirebase.ConfiguracaoFirebase;
import com.example.interageaula.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class TelaCadastro extends Activity{
    private EditText campoNome, campoEmail, campoSenha;
    private Button btnCadastrar;
    private ProgressBar progressBar;

    private Usuario usuario;

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        inicializaComponentes();

        //Cadastrar usuario
        progressBar.setVisibility(View.GONE);
        btnCadastrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if(!textoNome.isEmpty()){
                    if(!textoEmail.isEmpty()){
                        if(!textoSenha.isEmpty()){
                            //Se tiver caido dentro deste if significa que todos os campos estao preenchidos
                            usuario = new Usuario();

                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrarUsuario(usuario);
                        }else {
                            Toast.makeText(TelaCadastro.this,"Preencha a Senha!",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(TelaCadastro.this,"Preencha o Email!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(TelaCadastro.this,"Preencha o nome!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*Metodo responsavel por cadastrar usario com email e senha
        e fazer validações ao fazer o cadastro
     */
    public void cadastrarUsuario(Usuario usuario){
        progressBar.setVisibility(View.VISIBLE);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(TelaCadastro.this,"Cadastro efetuado com sucesso",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), TelaLogin.class));
                    finish();
                }else {
                    progressBar.setVisibility(View.GONE);

                    String erroExcecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "Por facvor, digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Esta conta já foi cadastrada";
                    }catch (Exception e){
                        erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(TelaCadastro.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void inicializaComponentes(){
        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        btnCadastrar = findViewById(R.id.buttonCadastrar);
        progressBar = findViewById(R.id.progressCadastro);

        campoNome.requestFocus();
    }

}
