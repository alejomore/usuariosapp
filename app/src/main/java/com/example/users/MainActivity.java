package com.example.users;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText etNombre, etEmail;
    Button btnGuardar, btnView;
    DBHelper dbHelper; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnView = findViewById(R.id.btnView);
        dbHelper = new DBHelper(this);

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String email = etEmail.getText().toString();
            guardarUsuario(nombre, email);
        });

        btnView.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, ListaUsuarios.class));
        });
    }
    private void guardarUsuario(String nombre, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("email", email);
        db.insert("usuarios",null,values);
        Toast.makeText(this,"Usuario guardado", Toast.LENGTH_SHORT).show();
        etNombre.setText("");
        etEmail.setText("");
    }
    
}