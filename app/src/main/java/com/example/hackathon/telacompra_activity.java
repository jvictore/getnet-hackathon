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

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.*;

public class telacompra_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.telacompra_activity);
    }

    public void salvarConta(View view){
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText valor = (EditText) findViewById(R.id.editTextNumber);
    Float valor_float = Float.parseFloat(valueOf(valor.getText()));

    Map<String, Object> umaReceita = new HashMap<>();
    umaReceita.put("valor", valor_float);

    db.collection("Fluxo/dividas/padealho")
            .add(umaReceita)
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
}
}