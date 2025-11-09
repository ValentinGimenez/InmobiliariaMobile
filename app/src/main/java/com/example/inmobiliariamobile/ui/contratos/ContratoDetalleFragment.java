package com.example.inmobiliariamobile.ui.contratos;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.inmobiliariamobile.R;
import com.example.inmobiliariamobile.databinding.FragmentContratoDetalleBinding;
import com.example.inmobiliariamobile.models.Contrato;
import com.google.android.material.snackbar.Snackbar;

public class ContratoDetalleFragment extends Fragment {

    private FragmentContratoDetalleBinding binding;
    private ContratoDetalleViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vm = ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())
                .create(ContratoDetalleViewModel.class);

        binding = FragmentContratoDetalleBinding.inflate(inflater, container, false);

        vm.getMError().observe(getViewLifecycleOwner(), s ->
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show()
        );

        vm.getMContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override public void onChanged(Contrato c) {

                binding.etCodigo.setText(String.valueOf(c.getId()));
                binding.etNombreCompleto.setText(c.getInquilino().getNombre() + " " + c.getInquilino().getApellido());
                binding.etFechaInicio.setText(c.getFecha_inicio());
                binding.etFechaFin.setText(c.getFecha_fin());
                binding.etDireccion.setText(c.getInmueble().getDireccion());
                binding.etMonto.setText(String.format("$ %.2f", c.getInmueble().getPrecio()));

                String urlBase = "http://10.0.2.2:5145/";
                String imagePath = c.getInmueble().getImagen() == null ? "" : c.getInmueble().getImagen().replace("\\", "/");
                String fullUrl = imagePath.startsWith("http") ? imagePath : urlBase + imagePath;

                Glide.with(requireContext())
                        .load(fullUrl)
                        .error(R.drawable.ic_menu_camera)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.imgInmueble);

                binding.btnPagos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contrato", c);
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu)
                                .navigate(R.id.action_nav_contrato_detalle_to_nav_pagos, bundle);
                    }
                });
            }
        });

        vm.obtenerContrato(getArguments());
        return binding.getRoot();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
