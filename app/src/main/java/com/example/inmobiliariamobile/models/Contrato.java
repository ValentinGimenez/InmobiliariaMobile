package com.example.inmobiliariamobile.models;

import java.io.Serializable;

public class Contrato implements Serializable {
    private int id;
    private Inmueble inmueble;
    private Inquilino inquilino;
    private String fecha_inicio;
    private String fecha_fin;
    private double monto_mensual;

    public int getId() { return id; }
    public Inmueble getInmueble() { return inmueble; }
    public Inquilino getInquilino() { return inquilino; }
    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }
    public double getMonto_mensual() { return monto_mensual; }

    public Contrato(int id, Inmueble inmueble, Inquilino inquilino, String fecha_inicio, String fecha_fin, double monto_mensual) {
        this.id = id;
        this.inmueble = inmueble;
        this.inquilino = inquilino;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.monto_mensual = monto_mensual;
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

    public void setMonto_mensual(double monto_mensual) {
        this.monto_mensual = monto_mensual;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", inmueble=" + inmueble +
                ", inquilino=" + inquilino +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_fin='" + fecha_fin + '\'' +
                ", montoMensual=" + monto_mensual +
                '}';
    }
}
