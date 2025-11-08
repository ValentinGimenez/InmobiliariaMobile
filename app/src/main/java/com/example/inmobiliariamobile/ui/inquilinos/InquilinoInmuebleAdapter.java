package com.example.inmobiliariamobile.ui.inquilinos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

import java.text.DecimalFormat;
import java.util.List;

public class InquilinoInmuebleAdapter extends RecyclerView.Adapter<InquilinoInmuebleAdapter.ContratoViewHolder> {

    private final LiveData<List<Contrato>> liveData;
    private final Context context;
    private final LayoutInflater li;

    public InquilinoInmuebleAdapter(LiveData<List<Contrato>> liveData, LayoutInflater li, Context context) {
        this.liveData = liveData;
        this.li = li;
        this.context = context;
    }

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item, parent, false);
        return new ContratoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoViewHolder holder, int position) {
        List<Contrato> listado = liveData.getValue();
        if (listado == null || listado.isEmpty()) return;

        Contrato contratoActual = listado.get(position);
        Inmueble inmuebleActual = contratoActual.getInmueble();
        Inquilino inquilinoActual = contratoActual.getInquilino();

        if (inmuebleActual == null) return;

        String urlBase = "http://10.0.2.2:5145/";

        holder.direccion.setText(inmuebleActual.getDireccion());
        holder.valor.setText("$ " + new DecimalFormat("#,##0.00").format(inmuebleActual.getPrecio()));

        String estado = inmuebleActual.getEstado();
        String tipo = inmuebleActual.getTipo();
        String uso = inmuebleActual.getUso();

        holder.estado.setText("Estado: " + (estado == null ? "" : estado));
        holder.tipo.setText("Tipo: " + (tipo == null ? "" : tipo));
        holder.uso.setText("Uso: " + (uso == null ? "" : uso));

        String imagePath = inmuebleActual.getImagen() == null ? "" : inmuebleActual.getImagen().replace("\\", "/");
        String fullUrl = imagePath.startsWith("http") ? imagePath : urlBase + imagePath;

        Glide.with(context)
                .load(fullUrl)
                .error(R.drawable.ic_menu_camera)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.foto);

        holder.cardView.setOnClickListener(v -> {
            if (inquilinoActual == null) return;
            Bundle bundle = new Bundle();
            bundle.putSerializable("inquilino", inquilinoActual);
            Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment_content_menu)
                    .navigate(R.id.nav_inquilino_detalle, bundle);
        });
    }

    @Override
    public int getItemCount() {
        List<Contrato> listado = liveData.getValue();
        return (listado == null) ? 0 : listado.size();
    }

    public static class ContratoViewHolder extends RecyclerView.ViewHolder {
        TextView direccion, valor, estado, tipo, uso;
        ImageView foto;
        CardView cardView;

        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            valor = itemView.findViewById(R.id.tvPrecio);
            estado = itemView.findViewById(R.id.tvEstado);
            tipo = itemView.findViewById(R.id.tvTipo);
            uso = itemView.findViewById(R.id.tvUso);
            foto = itemView.findViewById(R.id.ivFoto);
            cardView = itemView.findViewById(R.id.cItem);
        }
    }
}
