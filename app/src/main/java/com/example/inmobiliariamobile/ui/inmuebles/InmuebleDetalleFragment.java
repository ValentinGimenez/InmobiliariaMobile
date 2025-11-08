package com.example.inmobiliariamobile.ui.inmuebles;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.inmobiliariamobile.R;
import com.example.inmobiliariamobile.databinding.FragmentInmuebleDetalleBinding;
import com.example.inmobiliariamobile.models.Inmueble;
import com.google.android.material.snackbar.Snackbar;

public class InmuebleDetalleFragment extends Fragment {

    private FragmentInmuebleDetalleBinding binding;
    private InmuebleDetalleViewModel mv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(InmuebleDetalleViewModel.class);

        binding = FragmentInmuebleDetalleBinding.inflate(inflater, container, false);

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.GREEN)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override public void onChanged(Inmueble i) {
                binding.tvIdInmueble.setText(String.valueOf(i.getId()));
                binding.tvDireccionI.setText(i.getDireccion());
                binding.tvAmbientesI.setText(String.valueOf(i.getAmbientes()));
                binding.tvSuperficieI.setText(String.valueOf(i.getSuperficie()));
                binding.tvLatitudI.setText(String.valueOf(i.getEje_x()));
                binding.tvLongitudI.setText(String.valueOf(i.getEje_y()));
                binding.tvValorI.setText(String.format("$ %.2f", i.getPrecio()));
                binding.tvTipoI.setText(String.valueOf(i.getTipo()));
                binding.tvUsoI.setText(String.valueOf(i.getUso()));
                String urlBase = "http://10.0.2.2:5145/";
                String fullUrl = urlBase + (i.getImagen() == null ? "" : i.getImagen().replace("\\", "/"));
                Glide.with(requireContext())
                        .load(fullUrl)
                        .error(R.drawable.ic_menu_camera)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.imgInmueble);

                binding.checkDisponible.setChecked(i.getEstado().equalsIgnoreCase("Disponible"));
            }
        });
        binding.checkDisponible.setOnCheckedChangeListener((button, isChecked) -> {
            mv.actualizarDisponibilidad(isChecked);
        });
        mv.getMEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean habilitar) {
                binding.checkDisponible.setEnabled(habilitar);
            }
        });

        mv.obtenerInmueble(getArguments());

        return binding.getRoot();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
