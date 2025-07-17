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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

=======
>>>>>>> e4042048eae1e8c06d952fa015a9f2e6811f5336
public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonRegister;
    UserDBHelper dbHelper;

<<<<<<< HEAD
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

=======
>>>>>>> e4042048eae1e8c06d952fa015a9f2e6811f5336
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        dbHelper = new UserDBHelper(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
<<<<<<< HEAD
                    executor.execute(() -> {
                        boolean registrado = dbHelper.insertUser(email, password);

                        handler.post(() -> {
                            if (registrado) {
                                Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
=======
                    boolean registrado = dbHelper.insertUser(email, password);
                    if (registrado) {
                        Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
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
