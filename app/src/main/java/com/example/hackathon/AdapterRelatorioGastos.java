package com.example.hackathon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRelatorioGastos extends RecyclerView.Adapter<AdapterRelatorioGastos.MyViewHolder> {
    private List<itemRelatorio> lista;

    public AdapterRelatorioGastos(List<itemRelatorio> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Cria numero fixo de Views
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_relatorio_gastos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Troca os dados de cada view
        itemRelatorio item = lista.get(position);

        holder.categoria.setText(item.getCategoria());
        holder.valor_total.setText("R$ "+item.getValor());
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoria;
        TextView valor_total;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoria = itemView.findViewById(R.id.textCat);
            valor_total = itemView.findViewById(R.id.textValor);
        }
    }
}
