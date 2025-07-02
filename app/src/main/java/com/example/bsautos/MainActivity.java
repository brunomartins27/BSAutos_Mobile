package com.example.bsautos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.text.NumberFormat;
import java.util.Locale;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    UserDBHelper dbHelper;
    ArrayList<String> listaAutos;
    ArrayList<Long> idsAutos;
    ArrayAdapter<String> adapter;
    ListView listViewAutos;
    Button buttonAgregarAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new UserDBHelper(this);
        listViewAutos = findViewById(R.id.listViewAutos);
        buttonAgregarAuto = findViewById(R.id.buttonAgregarAuto);

        listaAutos = new ArrayList<>();
        idsAutos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaAutos);
        listViewAutos.setAdapter(adapter);

        cargarAutos();

        listViewAutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long autoId = idsAutos.get(position);
                Intent intent = new Intent(MainActivity.this, AutoDetalleActivity.class);
                intent.putExtra("AUTO_ID", autoId);
                startActivityForResult(intent, 1); // Cambiado a startActivityForResult
            }
        });

        listViewAutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarDialogoOpciones(position);
                return true;
            }
        });

        buttonAgregarAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarAuto();
            }
        });
    }

    private void cargarAutos() {
        listaAutos.clear();
        idsAutos.clear();
        Cursor cursor = dbHelper.getAllAutos();
        NumberFormat formatoPeso = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_ID));
                String marca = cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_MARCA));
                String modelo = cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_MODELO));
                double valor = cursor.getDouble(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_VALOR));
                String precioFormateado = formatoPeso.format(valor);
                listaAutos.add(marca + " " + modelo + "     " + precioFormateado);
                idsAutos.add(id);
            } while (cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }


    private void mostrarDialogoAgregarAuto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar Auto");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_auto, null, false);
        final EditText inputMarca = viewInflated.findViewById(R.id.inputMarca);
        final EditText inputModelo = viewInflated.findViewById(R.id.inputModelo);
        final EditText inputColor = viewInflated.findViewById(R.id.inputColor);
        final EditText inputEstado = viewInflated.findViewById(R.id.inputEstado);
        final EditText inputValor = viewInflated.findViewById(R.id.inputValor);
        final EditText inputKm = viewInflated.findViewById(R.id.inputKm);

        builder.setView(viewInflated);

        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String marca = inputMarca.getText().toString().trim();
                String modelo = inputModelo.getText().toString().trim();
                String color = inputColor.getText().toString().trim();
                String estado = inputEstado.getText().toString().trim();
                String valorStr = inputValor.getText().toString().trim();
                String kmStr = inputKm.getText().toString().trim();

                if (!marca.isEmpty() && !modelo.isEmpty() && !color.isEmpty()
                        && !estado.isEmpty() && !valorStr.isEmpty() && !kmStr.isEmpty()) {
                    double valor = Double.parseDouble(valorStr);
                    int km = Integer.parseInt(kmStr);
                    long res = dbHelper.insertAuto(marca, modelo, color, estado, valor, km);
                    if (res != -1) {
                        Toast.makeText(MainActivity.this, "Auto guardado correctamente", Toast.LENGTH_SHORT).show();
                        cargarAutos();
                    } else {
                        Toast.makeText(MainActivity.this, "Error al guardar auto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarDialogoOpciones(final int position) {
        final CharSequence[] opciones = {"Editar", "Eliminar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final long idAuto = idsAutos.get(position);
                if (which == 0) {
                    mostrarDialogoEditarAuto(idAuto, position);
                } else if (which == 1) {
                    dbHelper.deleteAuto(idAuto);
                    cargarAutos();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            cargarAutos();
        }
    }
    private void mostrarDialogoEditarAuto(final long id, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Auto");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_auto, null, false);
        final EditText inputMarca = viewInflated.findViewById(R.id.inputMarca);
        final EditText inputModelo = viewInflated.findViewById(R.id.inputModelo);
        final EditText inputColor = viewInflated.findViewById(R.id.inputColor);
        final EditText inputEstado = viewInflated.findViewById(R.id.inputEstado);
        final EditText inputValor = viewInflated.findViewById(R.id.inputValor);
        final EditText inputKm = viewInflated.findViewById(R.id.inputKm);

        Cursor cursor = dbHelper.getAutoById(id);
        if (cursor.moveToFirst()) {
            inputMarca.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_MARCA)));
            inputModelo.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_MODELO)));
            inputColor.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_COLOR)));
            inputEstado.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_ESTADO)));
            inputValor.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_VALOR))));
            inputKm.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_AUTO_KM))));
        }
        cursor.close();

        builder.setView(viewInflated);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String marca = inputMarca.getText().toString().trim();
                String modelo = inputModelo.getText().toString().trim();
                String color = inputColor.getText().toString().trim();
                String estado = inputEstado.getText().toString().trim();
                String valorStr = inputValor.getText().toString().trim();
                String kmStr = inputKm.getText().toString().trim();

                if (!marca.isEmpty() && !modelo.isEmpty() && !color.isEmpty()
                        && !estado.isEmpty() && !valorStr.isEmpty() && !kmStr.isEmpty()) {
                    double valor = Double.parseDouble(valorStr);
                    int km = Integer.parseInt(kmStr);
                    dbHelper.updateAuto(id, marca, modelo, color, estado, valor, km);
                    cargarAutos();
                } else {
                    Toast.makeText(MainActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
