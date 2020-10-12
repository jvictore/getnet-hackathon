package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class relatorio_activity extends AppCompatActivity {
    private RecyclerView  recGanhos;
    private RecyclerView  recGastos;
    private List<itemRelatorio> listaGanhos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio_activity);
        this.gerarRelatorio();
    }

    private void gerarRelatorio() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Categorias")
                .orderBy("categoria")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Map map = new HashMap();
                        String valor = "", categoria = "";
                        List<itemRelatorio> listaGanhos = new ArrayList<>();
                        List<itemRelatorio> listaGastos = new ArrayList<>();
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            map = snapshot.getData();
                            valor = map.get("valor_total").toString();
                            categoria = map.get("categoria").toString();
                            itemRelatorio item = new itemRelatorio(categoria, valor);
                            if (map.get("tipo").toString() == "true") {
                                listaGanhos.add(item);
                            } else {
                                listaGastos.add(item);
                            }
                            Log.d("RELATORIO", snapshot.getData().toString());
                        }

                        recGanhos = findViewById(R.id.recGanhos);
                        recGastos = findViewById(R.id.recGastos);
                        //Adapter
                        AdapterRelatorio adapterGanhos = new AdapterRelatorio(listaGanhos);
                        AdapterRelatorioGastos adapterGastos = new AdapterRelatorioGastos(listaGastos);

                        //RecGanhos
                        RecyclerView.LayoutManager managerGanhos = new LinearLayoutManager(getApplicationContext());
                        recGanhos.setLayoutManager(managerGanhos);
                        recGanhos.setHasFixedSize(true);
                        recGanhos.setAdapter(adapterGanhos);

                        //RecPerdas
                        RecyclerView.LayoutManager managerGastos = new LinearLayoutManager(getApplicationContext());
                        recGastos.setLayoutManager(managerGastos);
                        recGastos.setHasFixedSize(true);
                        recGastos.setAdapter(adapterGastos);
                    }
                });

    }

    public void fecharRelatorio(View view)
    {
        finish();
    }
};
