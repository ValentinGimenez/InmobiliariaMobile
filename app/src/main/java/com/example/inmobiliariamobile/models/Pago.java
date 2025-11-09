package com.example.inmobiliariamobile.models;

import android.util.Log;

import java.io.Serializable;

public class Pago implements Serializable {

    private int id;
    private int id_contrato;
    private int nro_pago;
    private String fecha_pago;
    private String estado;
    private String concepto;

    public Pago(int id, int id_contrato, int nro_pago, String fecha_pago, String estado, String concepto) {
        this.id = id;
        this.id_contrato = id_contrato;
        this.nro_pago = nro_pago;
        this.fecha_pago = fecha_pago;
        this.estado = estado;
        this.concepto = concepto;
    }
    public Pago(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public int getNro_pago() {
        return nro_pago;
    }

    public void setNro_pago(int nro_pago) {
        this.nro_pago = nro_pago;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getEstado() {
        switch (estado) {
            case "1":
                return "pendiente";
            case "2":
                return "recibido";
            case "3":
                return "anulado";
        }
        return estado;

    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
