package com.example.bsautos;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AutoDetalleActivity extends AppCompatActivity {

    UserDBHelper dbHelper;
    long autoId;
    TextView textMarcaModelo, textColor, textEstado, textValor, textKm;
    ImageView imageFotoDetalle;
    Button buttonVolver, buttonInteres, buttonEliminar;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_detalle);

        dbHelper = new UserDBHelper(this);

        imageFotoDetalle = findViewById(R.id.imageFotoDetalle);
        textMarcaModelo = findViewById(R.id.textMarcaModelo);
        textColor = findViewById(R.id.textColor);
        textEstado = findViewById(R.id.textEstado);
        textValor = findViewById(R.id.textValor);
        textKm = findViewById(R.id.textKm);
        buttonVolver = findViewById(R.id.buttonVolver);
        buttonInteres = findViewById(R.id.buttonInteres);
        buttonEliminar = findViewById(R.id.buttonEliminar);

        autoId = getIntent().getLongExtra("AUTO_ID", -1);
        if (autoId == -1) {
            Toast.makeText(this, "Error cargando auto", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Carregar dados do auto em thread separada
        executor.execute(() -> {
            Cursor cursor = dbHelper.getAutoById(autoId);
            if (cursor.moveToFirst()) {
                String marca = cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_MARCA));
                String modelo = cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_MODELO));
                String color = cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_COLOR));
                String estado = cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_ESTADO));
                double valor = cursor.getDouble(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_VALOR));
                int km = cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_KM));

                NumberFormat formatoPeso = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
                String precioFormateado = formatoPeso.format(valor);

                handler.post(() -> {
                    textMarcaModelo.setText(marca + " " + modelo);
                    textColor.setText("Color: " + color);
                    textEstado.setText("Estado: " + estado);
                    textValor.setText("Valor: " + precioFormateado);
                    textKm.setText("Kilometraje: " + km + " km");

                    if (imageFotoDetalle != null) {
                        imageFotoDetalle.setImageResource(R.drawable.ic_placeholder);
                    }
                });
            }
            cursor.close();
        });

        buttonVolver.setOnClickListener(v -> finish());

        buttonInteres.setOnClickListener(v -> {
            Toast.makeText(this, "¡Gracias por tu interés! Un asesor se contactará contigo.", Toast.LENGTH_LONG).show();
        });

        buttonEliminar.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(AutoDetalleActivity.this)
                    .setTitle("Eliminar auto")
                    .setMessage("¿Seguro que deseas eliminar este auto?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        executor.execute(() -> {
                            dbHelper.deleteAuto(autoId);
                            handler.post(() -> {
                                Toast.makeText(AutoDetalleActivity.this, "Auto eliminado", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            });
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
