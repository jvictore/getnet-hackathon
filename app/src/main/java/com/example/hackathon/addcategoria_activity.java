package com.example.hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class addcategoria_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcategoria_activity);
    }

    public void nova_categoria(View view) {
        Boolean tipo = null;
        String GV = getIntent().getStringExtra("TYPE");
        Date now = new Date();
        DecimalFormat df = new DecimalFormat("0.00");

        String init = "0.0";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        Map<String, Object> mapa = null;

        if (GV.equals("VENDA")) {
            tipo = true;
        }
        else {
            tipo = false;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        EditText texto = (EditText) findViewById(R.id.editTextTextPersonName);
        String nome_categoria = texto.getText().toString();
        Map<String, Object> individuais = new HashMap<>();

        Map<String, Object> categoria = new HashMap<>();
        categoria.put("categoria", nome_categoria);
        categoria.put("tipo", tipo);
        categoria.put("att", dateFormat.format(now));
        categoria.put("valor_total", init);

        db.collection("Categorias")
                .add(categoria)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("addcategoria", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addcategoria", "Error adding document", e);
                    }
                });

        finish();
    }
}