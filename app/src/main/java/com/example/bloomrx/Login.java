package com.example.bloomrx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private TextView goToRegister;
    private TextView dayLogin;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng trong Calendar bắt đầu từ 0, nên phải cộng thêm 1
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Lấy thứ trong tuần (1 = Chủ nhật, 7 = Thứ 7)

        // Chuyển đổi số thứ trong tuần thành tên ngày
        String dayOfWeekName = "";
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                dayOfWeekName = "CN";
                break;
            case Calendar.MONDAY:
                dayOfWeekName = "Th 2";
                break;
            case Calendar.TUESDAY:
                dayOfWeekName = "Th 3";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeekName = "Th 4";
                break;
            case Calendar.THURSDAY:
                dayOfWeekName = "Th 5";
                break;
            case Calendar.FRIDAY:
                dayOfWeekName = "Th 6";
                break;
            case Calendar.SATURDAY:
                dayOfWeekName = "Th 7";
                break;
        }


        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.registerBtn);
        goToRegister = findViewById(R.id.goToLogin);

        dayLogin = findViewById(R.id.dayLogin);
        dayLogin.setText(dayOfWeekName + ", " + dayOfMonth + " thg " + month);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                if (emailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(Login.this, "Email và mật khẩu không thể trống", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(Login.this, Home.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Login.this, "Sai thông tin đăng nhập",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}