package com.example.gymapp.KhachHang;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;

public class client_mainscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_mainscreen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.CardViewInfo).setOnClickListener(V->{
            Intent intent = new Intent(client_mainscreen.this, CaNhanKhachHang.class);
            startActivity(intent);
        });
        findViewById(R.id.CardViewCart).setOnClickListener(V->{
            Intent intent = new Intent(client_mainscreen.this, client_cart.class);
            startActivity(intent);
        });
        findViewById(R.id.cardViewHelp).setOnClickListener(V->{
            Intent intent = new Intent(client_mainscreen.this, BillList.class);
            startActivity(intent);
        });
        findViewById(R.id.cardViewProduct).setOnClickListener(V->{
            Intent intent = new Intent(client_mainscreen.this, client_product.class);
            startActivity(intent);
        });

    }


}