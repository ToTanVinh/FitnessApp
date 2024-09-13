package com.example.gymapp.KhachHang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;
import com.example.gymapp.adapter.CartAdapter;
import com.example.gymapp.databases.BillDatabase;
import com.example.gymapp.databases.CartDatabase;
import com.example.gymapp.databases.UserDatabase;
import com.example.gymapp.model.Bill;
import com.example.gymapp.model.CartProduct;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class client_cart extends AppCompatActivity {
    private ListView listViewCart;
    private CartDatabase cartDatabase;
    private List<CartProduct> cartList;
    private CartAdapter cartAdapter;
    private Button buttonPayment;
    private Button buttonDelete;
    private Button buttonReturn;
    private UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", -1);
        cartDatabase = new CartDatabase(this);
        userDatabase = new UserDatabase(this);
        listViewCart = findViewById(R.id.listViewCart);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonPayment = findViewById(R.id.buttonPayment);
        buttonReturn = findViewById(R.id.buttonReturn);

        cartList = cartDatabase.getAllCartItems(user_id);
        cartAdapter = new CartAdapter(this, cartList);
        listViewCart.setAdapter(cartAdapter);

        buttonReturn.setOnClickListener(v -> finish());

        buttonPayment.setOnClickListener(v -> {
            // Get selected products from the adapter
            Set<CartProduct> selectedProducts = cartAdapter.getSelectedProducts();

            // Check if any products are selected
            if (selectedProducts != null && !selectedProducts.isEmpty()) {
                // Calculate total amount
                double totalAmount = 0;
                for (CartProduct product : selectedProducts) {
                    totalAmount += product.getPrice();
                }

                // Get current date
                String billDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                // Create a new Bill object
                Bill bill = new Bill(user_id, 0, totalAmount, billDate);

                // Insert the bill into BillDatabase
                BillDatabase billDatabase = new BillDatabase(this);
                long billId = billDatabase.addBill(bill);

                // Remove the selected items from the cart
                for (CartProduct product : selectedProducts) {
                    cartDatabase.removeFromCart(user_id);
                }

                // Notify the user and refresh the cart list
                Toast.makeText(client_cart.this, "Payment successful! Bill ID: " + billId, Toast.LENGTH_SHORT).show();

                // Refresh the ListView
                cartList = cartDatabase.getAllCartItems(user_id);
                cartAdapter = new CartAdapter(this, cartList);
                listViewCart.setAdapter(cartAdapter);
            } else {
                Toast.makeText(client_cart.this, "Please select products to pay.", Toast.LENGTH_SHORT).show();
            }
        });


        buttonDelete.setOnClickListener(v -> {
            // Get selected products from the adapter
            Set<CartProduct> selectedProducts = cartAdapter.getSelectedProducts();

            // Check if any products are selected
            if (selectedProducts != null && !selectedProducts.isEmpty()) {
                // Remove the selected items from the cart
                for (CartProduct product : selectedProducts) {
                    cartDatabase.removeProductFromCart(product.getId(), user_id); // Update to use removeProductFromCart
                }

                // Notify the user and refresh the cart list
                Toast.makeText(client_cart.this, "Selected items removed from cart.", Toast.LENGTH_SHORT).show();

                // Refresh the ListView
                cartList = cartDatabase.getAllCartItems(user_id);
                cartAdapter = new CartAdapter(this, cartList);
                listViewCart.setAdapter(cartAdapter);
            } else {
                Toast.makeText(client_cart.this, "Please select products to delete.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}