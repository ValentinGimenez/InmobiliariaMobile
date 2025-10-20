package com.example.inmobiliariamobile.ui.perfil;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariamobile.R;
import com.example.inmobiliariamobile.databinding.FragmentPerfilBinding;
import com.example.inmobiliariamobile.models.Propietario;
import com.google.android.material.snackbar.Snackbar;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel mv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etCodigo.setText(String.valueOf(propietario.getIdPropietario()));
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etDni.setText(propietario.getDni());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etEmail.setText(propietario.getEmail());
            }
        });

        mv.getMCamposEditables().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean habilitar) {
                binding.etNombre.setEnabled(habilitar);
                binding.etApellido.setEnabled(habilitar);
                binding.etDni.setEnabled(habilitar);
                binding.etTelefono.setEnabled(habilitar);
                binding.etEmail.setEnabled(habilitar);
                binding.etCodigo.setEnabled(false);
            }
        });

        mv.getMTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String texto) {
                binding.btnEditarGuardar.setText(texto);
                binding.btnEditarGuardar.setTag(texto);
            }
        });

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mensaje) {
                Snackbar.make(binding.getRoot(), mensaje, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mensaje) {
                Snackbar.make(binding.getRoot(), mensaje, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.GREEN)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        binding.btnEditarGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.editarGuardar(
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etDni.getText().toString(),
                        binding.etTelefono.getText().toString(),
                        binding.etEmail.getText().toString(),
                        (String) binding.btnEditarGuardar.getTag()
                );
            }
        });

        binding.btnCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                androidx.navigation.Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_nav_perfil_to_nav_cambiarclave);
            }
        });

        mv.leerPropietario();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
