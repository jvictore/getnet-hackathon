package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class index_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);
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