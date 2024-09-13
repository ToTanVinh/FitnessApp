package com.example.gymapp.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;
import com.example.gymapp.adapter.ProductAdapter;
import com.example.gymapp.databases.ProductDatabase;
import com.example.gymapp.model.Product;

import java.util.List;

public class client_product extends AppCompatActivity {

    private ListView listViewProducts;
    private ProductDatabase productDatabase;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private Button buttonReturn;
    private Button buttonFil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        productDatabase = new ProductDatabase(this);

        listViewProducts = findViewById(R.id.listViewProducts);
        buttonReturn = findViewById(R.id.buttonReturn);
        buttonFil = findViewById(R.id.buttonFilter);

        productList = productDatabase.getAllProducts();
        productAdapter = new ProductAdapter(this,productList);
        listViewProducts.setAdapter(productAdapter);

        buttonReturn.setOnClickListener(v->{
            finish();
        });

        listViewProducts.setOnItemLongClickListener((parent, view, position, id) ->{
            Product selectedItem = productList.get(position);

            Intent intent = new Intent(client_product.this, client_product_detail.class);
            intent.putExtra("product_id", selectedItem.getId());
            startActivity(intent);

            return true;
        });


    }
}