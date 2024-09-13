//package com.example.gymapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.gymapp.KhachHang.KhachHang;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private View viewLogin;
//    private View viewRegister;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//
//        viewLogin = findViewById(R.id.viewLogin);
//        viewRegister = findViewById(R.id.viewRegister);
//
//        TextView textViewLogin = findViewById(R.id.textViewLogin);
//        TextView textViewRegister = findViewById(R.id.textViewRegister);
//        TextView textViewLogin1 = findViewById(R.id.textViewLogin1);
//        TextView textViewRegister1 = findViewById(R.id.textViewRegister1);
//        Button loginButton = findViewById(R.id.login_button);
//        Button registerButton = findViewById(R.id.register_button);
//        Button cancelButton = findViewById(R.id.huy_button);
//
//        textViewLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showLoginView();
//            }
//        });
//
//        textViewRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showRegisterView();
//            }
//        });
//
//        textViewLogin1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showLoginView();
//            }
//        });
//
//        textViewRegister1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showRegisterView();
//            }
//        });
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển đến Activity mới sau khi nhấp vào nút đăng nhập
//                Intent intent = new Intent(LoginActivity.this, KhachHang.class);
//                startActivity(intent);
//            }
//        });
//
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Có thể thực hiện hành động khác khi nhấp vào nút đăng ký
//                // Ví dụ, chuyển đến trang khác hoặc hiển thị thông báo
//                Intent intent = new Intent(LoginActivity.this, KhachHang.class);
//                startActivity(intent);
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Xử lý sự kiện khi nhấp vào nút hủy
//                // Ví dụ, quay lại màn hình đăng nhập
//                showLoginView();
//            }
//        });
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//
//    private void showLoginView() {
//        viewLogin.setVisibility(View.VISIBLE);
//        viewRegister.setVisibility(View.GONE);
//    }
//
//    private void showRegisterView() {
//        viewLogin.setVisibility(View.GONE);
//        viewRegister.setVisibility(View.VISIBLE);
//    }
//}



package com.example.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.KhachHang.KhachHang;
import com.example.gymapp.KhachHang.client_mainscreen;
import com.example.gymapp.databases.UserDatabase;
import com.example.gymapp.model.User;

public class LoginActivity extends AppCompatActivity {

    private View viewLogin;
    private View viewRegister;

    private EditText usernameLogin;
    private EditText passwordLogin;
    private EditText usernameRegister;
    private EditText passwordRegister;
    private UserDatabase userDatabase;
    private EditText fullnameRegister;
    private EditText emailRegister;
    private EditText birthdayRegister;
    private EditText genderRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        userDatabase = new UserDatabase(this);

        viewLogin = findViewById(R.id.viewLogin);
        viewRegister = findViewById(R.id.viewRegister);

        // Nhận giá trị isAdmin từ Intent
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        if (isAdmin) {
            viewRegister.setClickable(false);
        }


        TextView textViewLogin = findViewById(R.id.textViewLogin);
        TextView textViewRegister = findViewById(R.id.textViewRegister);
        TextView textViewLogin1 = findViewById(R.id.textViewLogin1);
        TextView textViewRegister1 = findViewById(R.id.textViewRegister1);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);
        Button cancelButton = findViewById(R.id.huy_button);

        // Initialize EditTexts for Login
        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);

        // Initialize EditTexts for Register
        usernameRegister = findViewById(R.id.usernameRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        fullnameRegister = findViewById(R.id.fullname);
        emailRegister = findViewById(R.id.email);
        birthdayRegister = findViewById(R.id.birthday);
        genderRegister = findViewById(R.id.gender);




        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginView();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterView();
            }
        });

        textViewLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginView();
            }
        });

        textViewRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterView();
            }
        });

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (validateLoginFields()) {
//                    // Chuyển đến Activity mới sau khi nhấp vào nút đăng nhập
//                    Intent intent = new Intent(LoginActivity.this, KhachHang.class);
//                    startActivity(intent);
//                }
//            }
//        });
        loginButton.setOnClickListener(v -> {
            if (validateLoginFields()) {
                String username = usernameLogin.getText().toString().trim();
                String password = passwordLogin.getText().toString().trim();
                if (authenticateUser(username, password)) {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("current_username", username);
                    User user = userDatabase.getUserByUsername(username);
                    if(user!=null){
                         editor.putInt("user_id",user.getId());
                         editor.apply();


                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, client_mainscreen.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "User ID not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (validateRegisterFields()) {
//                    // Chuyển đến Activity mới sau khi nhấp vào nút đăng ký
//                    Intent intent = new Intent(LoginActivity.this, KhachHang.class);
//                    startActivity(intent);
//                }
//            }
//        });
        registerButton.setOnClickListener(v -> {
            if (validateRegisterFields()) {
                String username = usernameRegister.getText().toString().trim();
                String password = passwordRegister.getText().toString().trim();
                String fullname = fullnameRegister.getText().toString().trim();
                String email = emailRegister.getText().toString().trim();
                String birthday = birthdayRegister.getText().toString().trim();
                String gender = genderRegister.getText().toString().trim();

                if (registerUser(username, password, fullname, email, birthday, gender)) {
                    Toast.makeText(LoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    showLoginView(); // Go back to login view after successful registration
                } else {
                    Toast.makeText(LoginActivity.this, "Registration failed: User already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấp vào nút hủy
                // Ví dụ, quay lại màn hình đăng nhập
                showLoginView();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showLoginView() {
        viewLogin.setVisibility(View.VISIBLE);
        viewRegister.setVisibility(View.GONE);
    }

    private void showRegisterView() {
        viewLogin.setVisibility(View.GONE);
        viewRegister.setVisibility(View.VISIBLE);
    }

    private boolean validateLoginFields() {
        String username = usernameLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateRegisterFields() {
        String username = usernameRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();
        String fullname = fullnameRegister.getText().toString().trim();
        String email = emailRegister.getText().toString().trim();
        String birthday = birthdayRegister.getText().toString().trim();
        String gender = genderRegister.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (fullname.isEmpty()) {
            Toast.makeText(this, "Full name is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (birthday.isEmpty()) {
            Toast.makeText(this, "Birthday is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gender.isEmpty()) {
            Toast.makeText(this, "Gender is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private boolean authenticateUser(String username, String password) {
        // Implement user authentication logic here
        // For now, let's assume authentication is successful if user exists in the database
        User user = userDatabase.getUserByUsername(username);
        if (user != null) {
            boolean isPasswordCorrect = user.getPassword().equals(password);
            if (isPasswordCorrect) {
                // Check if the user has the admin role
                return "user".equals(user.getRole());
            }
        }
        return false;

    }

    private boolean registerUser(String username, String password, String fullname, String email, String birthday, String gender) {
        // Implement user registration logic here
        // For now, let's assume registration is successful if username does not already exist
        User existingUser = userDatabase.getUserByUsername(username);
        if (existingUser != null) {
            return false; // User already exists
        }

        // Add user to database
        long userId = userDatabase.themNguoiDung(username, fullname, password, "user", email, birthday, gender, ""); // Assuming default avatar is empty
        return userId != -1;
    }
}
