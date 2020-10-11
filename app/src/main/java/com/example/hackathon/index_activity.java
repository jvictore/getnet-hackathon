package com.example.hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class index_activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);

        Button btn_venda = findViewById(R.id.btnVenda);
        btn_venda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(index_activity.this, telacompra_activity.class);
                startActivity(it);
            }
        });

/*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> umFluxo = new HashMap<>();
        umFluxo.put("User", "Acknowlogia");
        umFluxo.put("senha", "alex07");
        umFluxo.put("email", "alexmomi@hotmail.com");
        db.collection("Usuarios")
                .add(umFluxo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });

 */
    }
}