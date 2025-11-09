package com.example.inmobiliariamobile.ui.pagos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliariamobile.R;
import com.example.inmobiliariamobile.models.Contrato;
import com.example.inmobiliariamobile.models.Inmueble;
import com.example.inmobiliariamobile.models.Inquilino;
import com.example.inmobiliariamobile.models.Pago;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.PagoViewHolder> {

    private final LiveData<List<Pago>> liveData;
    private final Context context;
    private final LayoutInflater li;

    private Double precioInmueble;
    public PagosAdapter(LiveData<List<Pago>> liveData, LayoutInflater li, Context context) {
        this.liveData = liveData;
        this.li = li;
        this.context = context;
        this.precioInmueble = 0.0;
    }
    public void setPrecioInmueble(Double precioInmueble) {
        this.precioInmueble = precioInmueble;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_pagos, parent, false);
        return new PagoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        List<Pago> listado = liveData.getValue();
        if (listado == null || listado.isEmpty()) return;
        Pago pagoActual = listado.get(position);
        String fechaPago = pagoActual.getFecha_pago();

        Log.d("PagoFecha", "Fecha de pago recibida: " + fechaPago);
        if (listado == null) return;

        holder.idPago.setText("ID: "+ pagoActual.getId());
        holder.idContrato.setText("ID CONTRATO: "+ pagoActual.getId_contrato());
        holder.nro.setText("NRO DE PAGO: "+ pagoActual.getNro_pago());
        try {
            holder.fecha.setText("FECHA: " + new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                            .parse(pagoActual.getFecha_pago())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.concepto.setText(pagoActual.getConcepto());
        holder.precio.setText("IMPORTE: $ " + new DecimalFormat("#,##0.00").format(precioInmueble));
    }

    @Override
    public int getItemCount() {
        List<Pago> listado = liveData.getValue();
        return (listado == null) ? 0 : listado.size();
    }

    public static class PagoViewHolder extends RecyclerView.ViewHolder {
        TextView idPago, idContrato, nro, fecha,precio,concepto;
        CardView cardView;

        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            idPago = itemView.findViewById(R.id.tvIdPago);
            idContrato = itemView.findViewById(R.id.tvIdContrato);
            nro = itemView.findViewById(R.id.tvNroPago);
            fecha = itemView.findViewById(R.id.tvFechaPago);
            precio = itemView.findViewById(R.id.tvPrecio);
            concepto = itemView.findViewById(R.id.tvConcepto);
            cardView = itemView.findViewById(R.id.cItemPagos);
        }
    }
}
