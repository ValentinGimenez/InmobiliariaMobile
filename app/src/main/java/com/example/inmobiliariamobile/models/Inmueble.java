package com.example.inmobiliariamobile.models;

import android.util.Log;

import java.io.Serializable;

public class Inmueble implements Serializable {
    private int id;
    private String direccion;
    private int uso;
    private int tipo;
    private int ambientes;
    private int superficie;
    private double eje_x;
    private double eje_y;
    private double precio;
    private String imagen;
    private int estado;
    private int idPropietario;

    public Inmueble() {
    }

    public Inmueble(int id, String direccion, int uso, int tipo, int ambientes, int superficie, double eje_x, double eje_y, double precio, String imagen, int estado, int idPropietario) {
        this.id = id;
        this.direccion = direccion;
        this.uso = uso;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.superficie = superficie;
        this.eje_x = eje_x;
        this.eje_y = eje_y;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
        this.idPropietario = idPropietario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getUso() {
        return uso; // Ahora devuelve un valor entero
    }

    public void setUso(int uso) {
        this.uso = uso;
    }

    public int getTipo() {
        return tipo; // Ahora devuelve un valor entero
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public double getEje_x() {
        return eje_x;
    }

    public void setEje_x(double eje_x) {
        this.eje_x = eje_x;
    }

    public double getEje_y() {
        return eje_y;
    }

    public void setEje_y(double eje_y) {
        this.eje_y = eje_y;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getEstado() {
        return estado; // Ahora devuelve un valor entero
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }


    public String estadoToString() {
        switch (estado) {
            case 1:
                return "Disponible";
            case 2:
                return "No Disponible";
            case 3:
                return "Alquilado";
            default:
                return "Desconocido";
        }
    }

    public String tipoToString() {
        switch (tipo) {
            case 1:
                return "Casa";
            case 2:
                return "Departamento";
            case 3:
                return "Oficina";
            case 4:
                return "Local";
            default:
                return "Desconocido";
        }
    }

    public String usoToString() {
        switch (uso) {
            case 1:
                return "Comercial";
            case 2:
                return "Residencial";
            default:
                return "Desconocido";
        }
    }
}
