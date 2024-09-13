package com.example.gymapp.admin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.Manifest;
import android.widget.Toast;

import com.example.gymapp.R;
import com.example.gymapp.databases.CategoryDatabase;
import com.example.gymapp.databases.ProductDatabase;
import com.example.gymapp.model.Product;

import java.io.InputStream;
import java.util.List;

public class ProductUpdate extends AppCompatActivity {

    private ProductDatabase productDatabase;
    private CategoryDatabase categoryDatabase;

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextQuantity;
    private Spinner spinnerCategory;
    private Button buttonSave;
    private Button buttonCancel;
    private Button buttonUpload;

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_PERMISSION = 2;
    private Uri imageUri;
    private ImageView imageViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_product_update);

        productDatabase = new ProductDatabase(this);
        categoryDatabase = new CategoryDatabase(this);

        loadCategories();

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonCancel = findViewById(R.id.buttonCancel);
        Button buttonUpload = findViewById(R.id.buttonUploadImage);
        imageViewProduct = findViewById(R.id.imageViewProduct);

        buttonSave.setOnClickListener(v -> saveProduct());
        buttonCancel.setOnClickListener(v -> finish());
        buttonUpload.setOnClickListener(v -> checkPermissionsAndPickImage());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveProduct() {
        String name = editTextName.getText().toString();
        String priceStr = editTextPrice.getText().toString();
        String quantityStr = editTextQuantity.getText().toString();
        String categoryName = (String) spinnerCategory.getSelectedItem();

        // Convert selected category name to ID
        int categoryId = categoryDatabase.getCategoryIdByName(categoryName); // Assume this method returns category ID

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);

        // Create or update product object
        Product product = new Product(); // Fill product with values from input
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategoryID(categoryId);

        // Save product to database
        productDatabase.addProduct(product);

        finish();
    }

    private void loadCategories() {
        List<String> categories = categoryDatabase.getAllCategoryNames(); // Assume this method returns a list of category names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void checkPermissionsAndPickImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            openImagePicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                // Handle permission denial
                Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageViewProduct.setImageBitmap(bitmap);
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}