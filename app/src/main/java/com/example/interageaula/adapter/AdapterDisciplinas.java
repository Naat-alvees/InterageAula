package com.example.interageaula.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.interageaula.R;
import com.example.interageaula.model.Disciplina;

import java.util.List;

public class AdapterDisciplinas extends RecyclerView.Adapter<AdapterDisciplinas.MyViewHolder> {

    private List<Disciplina> listaDisciplina;

    public AdapterDisciplinas(List<Disciplina> lista) {
        this.listaDisciplina = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemListaDisciplina = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapterlistadisciplinas,viewGroup,false);

        return new MyViewHolder(itemListaDisciplina);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Disciplina disciplina = listaDisciplina.get(i);
        myViewHolder.nomeDisciplina.setText(disciplina.getNome());
    }

    @Override
    public int getItemCount() {
        return listaDisciplina.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeDisciplina;
        public MyViewHolder(View itemView) {
            super(itemView);
            nomeDisciplina = itemView.findViewById(R.id.tituloDisciplina);
        }
    }
}
