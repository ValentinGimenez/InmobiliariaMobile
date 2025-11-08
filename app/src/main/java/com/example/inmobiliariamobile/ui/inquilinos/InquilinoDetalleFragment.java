package com.example.inmobiliariamobile.ui.inquilinos;

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
import com.example.inmobiliariamobile.databinding.FragmentInquilinoDetalleBinding;
import com.example.inmobiliariamobile.models.Inquilino;
import com.google.android.material.snackbar.Snackbar;

public class InquilinoDetalleFragment extends Fragment {

    private FragmentInquilinoDetalleBinding binding;
    private InquilinoDetalleViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vm = ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())
                .create(InquilinoDetalleViewModel.class);

        binding = FragmentInquilinoDetalleBinding.inflate(inflater, container, false);

        vm.getMError().observe(getViewLifecycleOwner(), s ->
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show()
        );

        vm.getMInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override public void onChanged(Inquilino i) {

                binding.etCodigo.setText(String.valueOf(i.getId()));
                binding.etDni.setText(i.getDni());
                binding.etNombre.setText(i.getNombre());
                binding.etApellido.setText(i.getApellido());
                binding.etEmail.setText(i.getEmail());
                binding.etTelefono.setText(i.getTelefono());

                String urlBase = "http://10.0.2.2:5145/";
                String imagePath = i.getImagen() == null ? "" : i.getImagen().replace("\\", "/");
                String fullUrl = imagePath.startsWith("http") ? imagePath : urlBase + imagePath;

                Glide.with(requireContext())
                        .load(fullUrl)
                        .error(R.drawable.ic_menu_camera)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.imgInquilino);
            }
        });

        vm.obtenerInquilino(getArguments());
        return binding.getRoot();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
