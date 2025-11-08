package com.example.inmobiliariamobile.models;

import java.io.Serializable;

public class Contrato implements Serializable {
    private int id;
    private Inmueble inmueble;
    private Inquilino inquilino;
    private String fechaInicio;
    private String fechaFin;
    private double montoMensual;

    public int getId() { return id; }
    public Inmueble getInmueble() { return inmueble; }
    public Inquilino getInquilino() { return inquilino; }
    public String getFechaInicio() { return fechaInicio; }
    public String getFechaFin() { return fechaFin; }
    public double getMontoMensual() { return montoMensual; }

    public Contrato(int id, Inmueble inmueble, Inquilino inquilino, String fechaInicio, String fechaFin, double montoMensual) {
        this.id = id;
        this.inmueble = inmueble;
        this.inquilino = inquilino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setMontoMensual(double montoMensual) {
        this.montoMensual = montoMensual;
    }
}
