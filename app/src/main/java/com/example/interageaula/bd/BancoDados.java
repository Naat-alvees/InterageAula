package com.example.interageaula.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.interageaula.model.Disciplina;

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
        db.execSQL("create table if not exists tabelaDisciplinaAluno (codigo text not null primary key, nome text not null );");
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
        ContentValues valores = new ContentValues();
        valores.put("nome", disciplina.getNome());
        valores.put("codigo", disciplina.getCodigo());
        db.insert("tabelaDisciplinaAluno", "", valores);
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


}