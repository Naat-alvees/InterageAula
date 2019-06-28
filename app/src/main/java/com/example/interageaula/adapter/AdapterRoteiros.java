package com.example.interageaula.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.interageaula.R;
import com.example.interageaula.model.Roteiro;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterRoteiros extends RecyclerView.Adapter<AdapterRoteiros.MyViewHolder>{

    private List<Roteiro> listaRoteiros;

    public AdapterRoteiros(List<Roteiro> rot) {
        this.listaRoteiros = rot;
    }

    @NonNull
    @Override
    public AdapterRoteiros.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemListaRoteiro = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapterlistaroteiro,viewGroup,false);

        return new MyViewHolder(itemListaRoteiro);

    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Roteiro roteiro = listaRoteiros.get(i);
        myViewHolder.tituloRoteiro.setText(roteiro.getTituloRoteiro());
        myViewHolder.subtituloRoteiro.setText(roteiro.getSubtituloRoteiro());
        myViewHolder.dataRoteiro.setText(roteiro.getDataRoteiro());
    }

    @Override
    public int getItemCount() {
        return listaRoteiros.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tituloRoteiro;
        TextView subtituloRoteiro;
        TextView dataRoteiro;

        public MyViewHolder(View itemView) {
            super(itemView);
            tituloRoteiro = itemView.findViewById(R.id.tituloRoteiro);
            subtituloRoteiro = itemView.findViewById(R.id.subtituloRoteiro);
            dataRoteiro = itemView.findViewById(R.id.dataRoteiro);

        }
    }
}
