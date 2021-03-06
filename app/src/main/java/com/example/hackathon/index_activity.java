package com.example.hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class index_activity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);

        gerarLucro();
    }
    @Override
    protected void onResume() {
        super.onResume();
        gerarLucro();
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
                        TextView venda_mes_index= (TextView)findViewById(R.id.vendaMesIndex);
                        venda_mes_index.setText(rcifrao);

                        String gastoS = df.format((valor_total_gasto));

                        rcifrao = new String ("R$: ");
                        rcifrao = rcifrao.concat(gastoS);
                        TextView gasto_mes_index= (TextView)findViewById(R.id.gastoMesIndex);
                        gasto_mes_index.setText(rcifrao);

                        rcifrao = new String ("R$: ");

                        String lucroS = df.format((lucro));

                        lucro = valor_total_venda - valor_total_gasto;

                        rcifrao = rcifrao.concat( lucroS);
                        TextView lucro_mes_index= (TextView)findViewById(R.id.lucroMesIndex);
                        lucro_mes_index.setText(rcifrao);

                    }
                });
    }



    public void gerarRelatorio(View view) {
        startActivity(new Intent(this, relatorio_activity.class));
    }

    public void lancarGasto(View view) {
        startActivity(new Intent(this, gasto_activity.class));
    }

    public void lancarVenda(View view) {
        startActivity(new Intent(this, venda_activity.class));
    }
}