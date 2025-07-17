package com.example.bsautos;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.os.Handler;
import android.os.Looper;
=======
>>>>>>> e4042048eae1e8c06d952fa015a9f2e6811f5336
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

=======
>>>>>>> e4042048eae1e8c06d952fa015a9f2e6811f5336
public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewNoAccount;
    UserDBHelper dbHelper;

<<<<<<< HEAD
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

=======
>>>>>>> e4042048eae1e8c06d952fa015a9f2e6811f5336
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewNoAccount = findViewById(R.id.textViewNoAccount);
        dbHelper = new UserDBHelper(this);

        textViewNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
<<<<<<< HEAD
                    executor.execute(() -> {
                        boolean valido = dbHelper.validateUser(email, password);

                        handler.post(() -> {
                            if (valido) {
                                Toast.makeText(LoginActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
=======
                    boolean valido = dbHelper.validateUser(email, password);
                    if (valido) {
                        Toast.makeText(LoginActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
>>>>>>> e4042048eae1e8c06d952fa015a9f2e6811f5336
                }
            }
        });
    }
<<<<<<< HEAD

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
=======
>>>>>>> e4042048eae1e8c06d952fa015a9f2e6811f5336
}
