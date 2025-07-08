package com.example.users;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.users.databinding.ActivityListaUsuariosBinding;

import java.util.ArrayList;

public class ListaUsuarios extends AppCompatActivity {
    ListView listUsuarios;
    ArrayAdapter<String> adapter;
    ArrayList<String> lista;
    ArrayList<Usuario> usuarios;
    DBHelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        listUsuarios = findViewById(R.id.listUsuarios);
        dBhelper = new DBHelper(this);
        cargarUsuarios();

        listUsuarios.setOnItemClickListener((parent,view,position,id)->{
            Usuario usuario =usuarios.get(position);
            mostrarDialogo(usuario);
        });
    }

    private void cargarUsuarios(){
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SLECT * FROM usuarios", null);
        lista = new ArrayList<>();
        usuarios = new ArrayList<>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String email = cursor.getString(2);
            usuarios.add(new Usuario(id, nombre, email));
            lista.add(nombre + " - " + email);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listUsuarios.setAdapter(adapter);
    }

    private void mostrarDialogo(Usuario usuario){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar o Eliminar");

        final EditText inputNombre = new EditText(this);
        inputNombre.setText(usuario.getNombre());
        final EditText inputEmail = new EditText(this);
        inputEmail.setText(usuario.getEmail());

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputNombre);
        layout.addView(inputEmail);
        builder.setView(layout);

        builder.setPositiveButton("Actualizar", (dialog, which)->{
            actualizarUsario(usuario.id,inputNombre.getText().toString(), inputEmail.getText().toString());
        });

        builder.setNegativeButton("Eliminar", (dialog, which)->{
            eliminarUsuario(usuario.id);
        });

        builder.setNeutralButton("Cancelar", null);
        builder.show();
    }

    private void actualizarUsario(int id, String nombre, String email){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("nombre", nombre);
        values.put("email",email);
        db.update("usuarios", values, "id=?", new String[]{String.valueOf(id)});
        cargarUsuarios();
    }

    private void eliminarUsuario(int id){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        db.delete("usuarios","id=?", new String[]{String.valueOf(id)});
        cargarUsuarios();
    }
}