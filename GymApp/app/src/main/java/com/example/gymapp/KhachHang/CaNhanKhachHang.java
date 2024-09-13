package com.example.gymapp.KhachHang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;
import com.example.gymapp.databases.UserDatabase;
import com.example.gymapp.model.User;

public class CaNhanKhachHang extends AppCompatActivity {
    private UserDatabase userDatabase;
    private Button buttonReturn;
    private TextView profileName, profileEmail, profileBirthday, profileGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ca_nhan_khach_hang);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(v -> finish());
        userDatabase = new UserDatabase(this);

        // Liên kết các view
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileBirthday = findViewById(R.id.profileBirthday);
        profileGender = findViewById(R.id.profileGender);

        // Lấy tên người dùng từ shared preferences hoặc intent
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("current_username", null);

        // Nếu tên người dùng hợp lệ, truy xuất thông tin người dùng
        if (username != null) {
            User currentUser = userDatabase.getUserByUsername(username);

            if (currentUser != null) {
                // Cập nhật các view với dữ liệu người dùng
                profileName.setText("Tên người dùng: " + currentUser.getFullname());
                profileEmail.setText("Email: " + currentUser.getEmail());
                profileBirthday.setText("Ngày sinh: " + currentUser.getFormattedBirthday());
                profileGender.setText("Giới tính: " + currentUser.getGender());
            }
        }
    }

}