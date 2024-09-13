package com.example.gymapp.KhachHang;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.gymapp.R;
import com.example.gymapp.admin.ProductDetail;
import com.example.gymapp.admin.ProductUpdate;
import com.example.gymapp.databases.CartDatabase;
import com.example.gymapp.databases.CategoryDatabase;
import com.example.gymapp.databases.ProductDatabase;
import com.example.gymapp.model.CartProduct;
import com.example.gymapp.model.Product;

public class client_product_detail extends AppCompatActivity {
    private ProductDatabase productDatabase;
    private CategoryDatabase categoryDatabase;
    private CartDatabase cartDatabase;
    private int productID;
    private Product product;

    private TextView textViewName;
    private TextView textViewPrice;
    private TextView textViewQuantity;
    private TextView textViewCategory;
    private ImageView imageView;

    private Button buttonAdd;
    private Button buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productDatabase = new ProductDatabase(this);
        categoryDatabase = new CategoryDatabase(this);
        cartDatabase = new CartDatabase(this);
        textViewName = findViewById(R.id.textViewName);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewQuantity = findViewById(R.id.textViewQuantity);
        textViewCategory = findViewById(R.id.textViewCategory);
        imageView = findViewById(R.id.imageView);
        buttonBack = findViewById(R.id.buttonBack);
        buttonAdd = findViewById(R.id.buttonAdd);

        productID = getIntent().getIntExtra("product_id", -1);
        product = productDatabase.getProductById(productID);

        if(product != null){
            textViewName.setText(product.getName());
            textViewPrice.setText(String.format("$%.2f", product.getPrice()));
            textViewQuantity.setText(String.valueOf(product.getQuantity()));

            // Retrieve category name and display it
            String categoryName = categoryDatabase.getCategoryNameById(product.getCategoryID());
            textViewCategory.setText(categoryName);

            // Load product image using Glide
            Glide.with(this)
                    .load(product.getImage()) // URL or resource ID
                    .into(imageView);
        }

        buttonBack.setOnClickListener(v -> finish());

        buttonAdd.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            int user_id = sharedPreferences.getInt("user_id", -1);
            // Thêm sản phẩm vào giỏ hàng
            CartProduct cartProduct = new CartProduct(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    1 // Số lượng mặc định là 1
            );
            long result = cartDatabase.addProductToCart(cartProduct,user_id);
            if (result != -1) {
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Sản phẩm đã được thêm vào giỏ hàng.")
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Lỗi")
                        .setMessage("Không thể thêm sản phẩm vào giỏ hàng.")
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }
}