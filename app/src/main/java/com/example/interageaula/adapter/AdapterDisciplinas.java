package com.example.interageaula;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdapterDisciplinas extends RecyclerView.Adapter<AdapterDisciplinas.MyViewHolder> {

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemListaDisciplina = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapterlistadisciplinas,viewGroup,false);

        return new MyViewHolder(itemListaDisciplina);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.nomeDisciplina.setText("Teste");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeDisciplina;
        public MyViewHolder(View itemView) {
            super(itemView);
            nomeDisciplina = itemView.findViewById(R.id.tituloDisciplina);
        }
    }
}
