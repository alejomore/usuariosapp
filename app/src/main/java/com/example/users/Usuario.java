package com.example.users;

public class Usuario {
    int id;
    String nombre;
    String emial;

    public Usuario(int id, String nombre, String emial) {
        this.id = id;
        this.nombre = nombre;
        this.emial = emial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }
}
