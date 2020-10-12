package com.example.hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Map;

public class venda_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venda_activity);

        fillSpinner();
    }

    protected void onResume() {
        super.onResume();
        fillSpinner();
    }

    public void fillSpinner() {
        final Spinner s = (Spinner) findViewById(R.id.categorias);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference collRef = database.collection("Categorias");

        collRef.whereEqualTo("tipo", true).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        List<String> spinnerItens = new ArrayList<>();

                        for (DocumentSnapshot snapshot : snapshotList) {
                            spinnerItens.add(snapshot.get("categoria").toString());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(venda_activity.this, android.R.layout.simple_spinner_item, spinnerItens);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s.setAdapter(adapter);

                    }
                });
    }

    public void submitVenda(View view) {

        Spinner s = (Spinner) findViewById(R.id.categorias);
        String selectedItem = s.getSelectedItem().toString();

        final Date now = new Date();
        final DecimalFormat df = new DecimalFormat("0.00");

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        EditText strValor = (EditText) findViewById(R.id.valor);
        final Double valor = Double.parseDouble((java.lang.String.valueOf(strValor.getText())));

        final Map<String, Object> v_individual = new HashMap<>();
        v_individual.put("data", dateFormat.format(now));
        v_individual.put("valor_individual", df.format(valor));

        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference collRef = database.collection("Categorias");

        collRef.whereEqualTo("categoria", selectedItem).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot snapshot : snapshotList) {
                            String idDoc = snapshot.getId();
                            Double total = Double.parseDouble(snapshot.get("valor_total").toString());
                            Double incremento = (total + valor);

                            final Map<String, Object> att_geral = new HashMap<>();
                            att_geral.put("valor_total", df.format(incremento));
                            att_geral.put("att", dateFormat.format(now));

                            database.collection("Categorias").document(idDoc).update(att_geral);
                            database.collection("Categorias").document(idDoc).collection("individual").add(v_individual);
                        }
                    }
                });

        finish();
    }

    public void addCategoria(View view)
    {
        Intent intent = new Intent(this, addcategoria_activity.class);
        intent.putExtra("TYPE", "VENDA");
        startActivity(intent);
    }
}