package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class gasto_activity extends AppCompatActivity {

    private FirebaseFirestore ref = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gasto_activity);
        showDataSpinner();
    }

    public void showDataSpinner() {
        CollectionReference refcolecao = ref.collection("Categorias");
        refcolecao.whereEqualTo("tipo", false).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Spinner spinner = (Spinner) findViewById(R.id.spinner2);
                List<String> arrayList = new ArrayList<>();

                if (task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String nomeCat  = document.get("categoria").toString();
                        arrayList.add(nomeCat);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(gasto_activity.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void adicionarGasto(View v) {
        CollectionReference refcolecao = ref.collection("Categorias");

        Spinner s = (Spinner) findViewById(R.id.spinner2);
        String aCategoria = s.getSelectedItem().toString();

        EditText valor_inicial = (EditText) findViewById(R.id.editTextNumberDecimal);
        final Double valor = Double.parseDouble((java.lang.String.valueOf(valor_inicial.getText())));

        final Date now = new Date();
        final DecimalFormat df = new DecimalFormat("0.00");

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        final Map<String, Object> individual = new HashMap<>();
        individual.put("data", dateFormat.format(now));
        individual.put("valor_individual", df.format(valor));

        refcolecao.whereEqualTo("categoria", aCategoria).get()
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

                            ref.collection("Categorias").document(idDoc).update(att_geral);
                            ref.collection("Categorias").document(idDoc).collection("individual").add(individual);
                        }
                    }
                });

        finish();
    }

    public void addCategoria(View view)
    {
        startActivity(new Intent(this, addcategoria_activity.class));
    }
}