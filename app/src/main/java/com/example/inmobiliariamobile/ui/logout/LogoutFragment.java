package com.example.inmobiliariamobile.ui.logout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.inmobiliariamobile.MainActivity;
import com.example.inmobiliariamobile.R;
import com.example.inmobiliariamobile.databinding.FragmentLogoutBinding;
import com.example.inmobiliariamobile.ui.inicio.InicioFragment;
import com.google.android.material.snackbar.Snackbar;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;
    private LogoutViewModel mv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        mv.getMMostrarDialogo().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(Void v) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Cierre de sesión")
                        .setMessage("¿Está seguro de que desea cerrar la sesión?")
                        .setCancelable(false)
                        .setNegativeButton("CANCELAR", (dialog, which) -> mv.cancelarLogout())
                        .setPositiveButton("ACEPTAR", (dialog, which) -> mv.confirmarLogout())
                        .show();
            }
        });

        mv.getMNavegarLogin().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(Void v) {
                Intent i = new Intent(requireContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        mv.getMCerrarPantalla().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(Void v) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_inicio);
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
