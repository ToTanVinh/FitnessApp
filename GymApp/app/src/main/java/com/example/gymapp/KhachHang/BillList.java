package com.example.gymapp.KhachHang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;
import com.example.gymapp.adapter.BillAdapter;
import com.example.gymapp.databases.BillDatabase;
import com.example.gymapp.model.Bill;

import java.util.List;

public class BillList extends AppCompatActivity {
    private ListView listViewBills;
    private BillAdapter billAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewBills = findViewById(R.id.listViewBills);
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", -1);
        BillDatabase billDatabase = new BillDatabase(this);
        List<Bill> bills = billDatabase.getAllBills(user_id);

        billAdapter = new BillAdapter(this, bills);
        listViewBills.setAdapter(billAdapter);
    }
}