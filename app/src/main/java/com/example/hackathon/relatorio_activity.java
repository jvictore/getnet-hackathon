package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.text.DecimalFormat;
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
        gerarLucro();
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


    public void gerarLucro(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Calendar calendar = Calendar.getInstance();
        //TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        //calendar.set(Calendar.DAY_OF_MONTH, 1);
        ///Date now = calendar.getTime();

        //Log.i("OPPA", now.toString());
        //Timestamp currentTime = new Timestamp(now);

        //.whereGreaterThan("att", currentTime)

        db.collection("Categorias")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Double valor_total_venda = 0.0 , valor_total_gasto = 0.0, lucro = 0.0;
                        Map map;
                        String valor_total_categoria;
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            map = snapshot.getData();

                            if(map.get("tipo").toString() == "true") {
                                valor_total_categoria = map.get("valor_total").toString();
                                valor_total_venda = valor_total_venda + Double.parseDouble(valor_total_categoria);
                            }else {
                                valor_total_categoria = map.get("valor_total").toString();
                                valor_total_gasto = valor_total_gasto + Double.parseDouble(valor_total_categoria);
                            }
                            //Log.i("AQUIAsDatas", map.get("att").toString());
                        }


                        lucro = valor_total_venda - valor_total_gasto;

                        DecimalFormat df = new DecimalFormat("0.00");
                        String vendaS = df.format((valor_total_venda));

                        String rcifrao = new String ("R$: ");
                        rcifrao = rcifrao.concat(vendaS);
                        TextView venda_mes_index= (TextView)findViewById(R.id.vendaMesRelatorio);
                        venda_mes_index.setText(rcifrao);

                        String gastoS = df.format((valor_total_gasto));

                        rcifrao = new String ("R$: ");
                        rcifrao = rcifrao.concat(gastoS);
                        TextView gasto_mes_index= (TextView)findViewById(R.id.gastoMesRelatorio);
                        gasto_mes_index.setText(rcifrao);

                        rcifrao = new String ("R$: ");

                        String lucroS = df.format((lucro));

                        lucro = valor_total_venda - valor_total_gasto;

                        rcifrao = rcifrao.concat( lucroS);
                        TextView lucro_mes_index= (TextView)findViewById(R.id.lucroMesRelatorio);
                        lucro_mes_index.setText(rcifrao);

                    }
                });
    }
};
