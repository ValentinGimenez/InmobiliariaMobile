package com.example.inmobiliariamobile.ui.perfil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariamobile.databinding.FragmentCambiarClaveBinding;
import com.google.android.material.snackbar.Snackbar;

public class CambiarClaveFragment extends Fragment {

    private FragmentCambiarClaveBinding binding;
    private CambiarClaveViewModel mv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(CambiarClaveViewModel.class);

        binding = FragmentCambiarClaveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        mv.getMVolver().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override public void onChanged(Void v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mv.cambiarClave(
                        binding.etActual.getText().toString(),
                        binding.etNueva.getText().toString(),
                        binding.etConfirmar.getText().toString()
                );
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
