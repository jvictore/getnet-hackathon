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

        Button btn_venda = findViewById(R.id.btnVenda);
        btn_venda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(index_activity.this, telavenda_activity.class);
                startActivity(it);
            }
        });
    }

    public void gerarRelatorio(View view) {
        startActivity(new Intent(this, relatorio_activity.class));
    }
}