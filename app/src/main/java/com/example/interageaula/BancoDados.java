package com.example.interageaula;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class BancoDados extends SQLiteOpenHelper {
    private static final String NOME_BD = "banco";
    private static final int VERSAO = 1;
    private SQLiteDatabase db;

    public BancoDados(Context contexto) {
        super(contexto, NOME_BD, null, VERSAO);
        db = getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists tabelaDisciplinaAluno (codigo text not null primary key, nome text not null);");
        db.execSQL("create table if not exists tabelaDisciplinaCodigo (codigo text not null primary key, nome text not null);");
        db.execSQL("create table if not exists tabelaRoteiro (tituloRoteiro text not null primary key, subtituloRoteiro text not null, dataRoteiro text not null, codigo text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table tabelaDisciplinaAluno");
        onCreate(db);
    }

    public void deleta() {
        db.execSQL("drop table tabelaDisciplinaAluno");
        onCreate(db);
    }

    public void inserirDisciplinaAluno(Disciplina disciplina) {
        Log.d("TAG", "Metodo inserir invocado");
        ContentValues valores = new ContentValues();
        valores.put("nome", disciplina.getNome());
        valores.put("codigo", disciplina.getCodigo());
        db.insert("tabelaDisciplinaAluno", "", valores);
        //Log.d("BANCO","Inserido com sucesso");
    }

    public ArrayList<Disciplina> buscarDisciplinasAluno() {
        ArrayList<Disciplina> lista = new ArrayList<>();
        Cursor c = db.query("tabelaDisciplinaAluno", null, null, null, null, null, null, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                Disciplina c1 = new Disciplina();
                c1.setCodigo(c.getString(c.getColumnIndex("codigo")));
                c1.setNome(c.getString(c.getColumnIndex("nome")));
                lista.add(c1);
            } while (c.moveToNext());
        }
        return lista;
    }

    public void deletarDisciplinaAluno(Disciplina disciplina) {
        db.delete("tabelaDisciplinaAluno", "codigo=" + disciplina.getCodigo(), null);
    }

    public void inserirDisciplina(CodigoDisciplina disciplina) {
        //Log.d("BANCO","Metodo inserir invocado");
        ContentValues valores = new ContentValues();
        valores.put("nome", disciplina.getNome());
        valores.put("codigo", disciplina.getCodigo());
        db.insert("tabelaDisciplinaCodigo", "", valores);
        Log.d("TAG",disciplina.getNome());
        Log.d("TAG",disciplina.getCodigo());
        db.insert("tabelaDisciplinaCodigo","",valores);
        //Log.d("BANCO","Inserido com sucesso");
    }


    public String[] buscarDisciplina(String codigo){
        String mensagem[] = new String[2];

        String query =  "SELECT * FROM tabelaDisciplinaCodigo WHERE codigo='"+codigo+"'";

        Cursor c = db.rawQuery(query, null);

        if (c.getCount() != 0){
            c.moveToFirst();
            mensagem[0] = "1";
            mensagem[1] = c.getString(c.getColumnIndex("nome"));
        }else {
            mensagem[0] = "-1";
        }
        return mensagem;
    }

    public void inserirRoteiroDisciplina(String codigo, Roteiro roteiro) {

        ContentValues valores = new ContentValues();

        valores.put("tituloRoteiro", roteiro.getTituloRoteiro());
        valores.put("subtituloRoteiro", roteiro.getSubtituloRoteiro());
        valores.put("dataRoteiro", roteiro.getDataRoteiro());
        valores.put("codigo", codigo);
        db.insert("tabelaRoteiro", "", valores);
    }

    public ArrayList<Roteiro> buscarRoteiroDisciplinas(String codigo) {
        ArrayList<Roteiro> lista = new ArrayList<>();
        String query = "SELECT * FROM tabelaRoteiro WHERE codigo='" + codigo + "'";
        Cursor c = db.rawQuery(query, null);


        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                Roteiro rot = new Roteiro();
                rot.setTituloRoteiro(c.getString(c.getColumnIndex("tituloRoteiro")));
                rot.setSubtituloRoteiro(c.getString(c.getColumnIndex("subtituloRoteiro")));
                rot.setDataRoteiro(c.getString(c.getColumnIndex("dataRoteiro")));
                lista.add(rot);
            } while (c.moveToNext());
        }
        return lista;
    }

//    public String[] buscarRoteiro(String codigo) {
//        String[] arrayRoteiro = new String[4];
//        String query = "SELECT * FROM tabelaRoteiro WHERE codigo='" + codigo + "'";
//
//        Cursor c = db.rawQuery(query, null);
//
//        if (c.getCount() != 0) {
//            c.moveToFirst();
//            arrayRoteiro[0] = "1";
//            arrayRoteiro[1] = c.getString(c.getColumnIndex("tituloRoteiro"));
//            arrayRoteiro[2] = c.getString(c.getColumnIndex("subtituloRoteiro"));
//            arrayRoteiro[3] = c.getString(c.getColumnIndex("dataRoteiro"));
//
//        } else {
//            arrayRoteiro[0] = "-1";
//        }
//
//        return arrayRoteiro;
//    }

}