//package com.example.interageaula.activities;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.LinearLayout;
//
//import com.example.interageaula.R;
//import com.example.interageaula.TelaConteudos;
//import com.example.interageaula.activities.ViewLogin;
//import com.example.interageaula.bd.BancoDados;
//import com.example.interageaula.configuracoesFirebase.ConfiguracaoFirebase;
//import com.example.interageaula.model.Roteiro;
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.ArrayList;
//
//public class TelaRoteiros extends AppCompatActivity implements View.OnClickListener {
//    private FirebaseAuth autentificacao;
//    private LinearLayout layoutRot1;
//    private String codigoDisciplina;
//    private Roteiro roteiro;
//    private String roteiros;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tela_roteiros);
//
//        autentificacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
//        //Configuração toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("InterageAula");
//        setSupportActionBar(toolbar);
//
//        layoutRot1 = (LinearLayout) findViewById(R.id.layoutRoteiro1);
//        layoutRot1.setOnClickListener(this);
//
////        BancoDados bd = new BancoDados(getApplicationContext());
////        roteiro = new Roteiro();
////        roteiro.setTituloRoteiro("Substantivo");
////        roteiro.setSubtituloRoteiro("Roteiro 1");
////        roteiro.setDataRoteiro("18/06/2019");
////        bd.inserirRoteiroDisciplina("123", roteiro);
////
////        Intent i = getIntent();
////
////        if(i != null){
////            Bundle disciplinaRecebida = new Bundle();
////            disciplinaRecebida = i.getExtras();
////
////            if(disciplinaRecebida != null){
////                codigoDisciplina = disciplinaRecebida.getString("enviadisciplina","Erro");
////                listarRoteiros(bd,codigoDisciplina);
////
////            }
////        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v == layoutRot1){
//            Intent i = new Intent(this, TelaConteudos.class);
//            startActivity(i);
//        }
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu){
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item){
//
//        switch (item.getItemId()){
//            case R.id.MenuSair:
//                deslogarUsuario();
//                break;
//            case R.id.MenuConfiguracao:
////                abrirConfiguracoes();
////                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void deslogarUsuario(){
//        try{
//            autentificacao.signOut();
//            startActivity(new Intent(getApplicationContext(), ViewLogin.class));
//            finish();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
////    public void listarRoteiros(BancoDados bd, String codigoDisciplina){
////        layoutRot1.removeAllViews();
////        ArrayList<Roteiro> lista = new ArrayList<>();
////
////        lista = bd.buscarRoteiroDisciplinas(codigoDisciplina);
////
////        for (int i = 0; i < lista.size(); i++){
////            layoutRot1 = new LinearLayout(this);
////
////
////        }
////    }
//}
