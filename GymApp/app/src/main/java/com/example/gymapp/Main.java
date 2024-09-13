package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        CardView cardViewAdmin = findViewById(R.id.cardViewAdmin);
        CardView cardViewPT = findViewById(R.id.cardViewPT);
        CardView cardViewCustomer = findViewById(R.id.cardViewCustomer);

        cardViewAdmin.setOnClickListener(v->openLoginActivity(true));
        cardViewPT.setOnClickListener(v -> openLoginActivity(false));
        cardViewCustomer.setOnClickListener(v -> openLoginActivity(false));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openLoginActivity(boolean isAdmin) {
        // Tạo Intent để mở LoginActivity
        Intent intent = new Intent(Main.this, LoginActivity.class);
        intent.putExtra("isAdmin", isAdmin);
        startActivity(intent);
    }
}