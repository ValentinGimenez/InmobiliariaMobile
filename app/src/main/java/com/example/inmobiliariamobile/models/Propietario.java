package com.example.inmobiliariamobile.models;

import java.io.Serializable;

public class Propietario implements Serializable {
    private int idPropietario;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String clave;

    public Propietario(String apellido, String telefono, String nombre, int idPropietario, String email, String dni, String clave) {
        this.apellido = apellido;
        this.telefono = telefono;
        this.nombre = nombre;
        this.idPropietario = idPropietario;
        this.email = email;
        this.dni = dni;
        this.clave = clave;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
