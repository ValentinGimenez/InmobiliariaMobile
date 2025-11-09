package com.example.inmobiliariamobile.models;

import android.util.Log;

import java.io.Serializable;

public class Contrato implements Serializable {
    private int id;
    private Inmueble inmueble;
    private Inquilino inquilino;
    private String fecha_inicio;
    private String fecha_fin;
    private double montoMensual;

    public int getId() { return id; }
    public Inmueble getInmueble() { return inmueble; }
    public Inquilino getInquilino() { return inquilino; }
    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }
    public double getMontoMensual() { return montoMensual; }

    public Contrato(int id, Inmueble inmueble, Inquilino inquilino, String fecha_inicio, String fecha_fin, double montoMensual) {
        this.id = id;
        this.inmueble = inmueble;
        this.inquilino = inquilino;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.montoMensual = montoMensual;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setMontoMensual(double montoMensual) {
        this.montoMensual = montoMensual;
    }
}
