package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class gasto_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gasto_activity);
    }

    public void addCategoria(View view)
    {
        startActivity(new Intent(this, addcategoria_activity.class));
    }
}