package com.example.hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Map;

public class venda_activity extends AppCompatActivity {
    public static final String TAG = "venda_activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venda_activity);
    }

    public void addCategoria(View view)
    {
        Intent intent = new Intent(this, addcategoria_activity.class);
        intent.putExtra("TYPE", "VENDA");
        startActivity(intent);
    }
}