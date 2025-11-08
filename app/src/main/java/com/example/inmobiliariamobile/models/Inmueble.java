package com.example.inmobiliariamobile.models;

import android.util.Log;

import java.io.Serializable;

public class Inmueble implements Serializable {
    private int id;
    private String direccion;
    private String uso;
    private String tipo;
    private int ambientes;
    private int superficie;
    private double eje_x;
    private double eje_y;
    private double precio;
    private String imagen;
    private String estado;
    private int idPropietario;

    public Inmueble() {
    }

    public Inmueble(int id, String direccion, String uso, String tipo, int ambientes, int superficie, double eje_x, double eje_y, double precio, String imagen, String estado, int idPropietario) {
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

    public String getUso() {
        switch (uso) {
            case "1":
                return "Comercial";
            case "2":
                return "Residencial";
        }
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        switch (tipo) {
            case "1":
                return "Casa";
            case "2":
                return "Departamento";
            case "3":
                return "Oficina";
            case "4":
                return "Local";
        }
        return tipo;

    }

    public void setTipo(String tipo) {
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

    public String getEstado() {
        Log.d("InmuebleModel", "Estado: " + estado);
        switch (estado) {
            case "1":
                return "Disponible";
            case "2":
                return "No Disponible";
            case "3":
                return "Alquilado";
        }
        return estado;

    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }
}
