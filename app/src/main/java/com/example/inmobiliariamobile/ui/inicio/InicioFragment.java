package com.example.inmobiliariamobile.ui.inicio;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariamobile.R;
import com.example.inmobiliariamobile.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private InicioViewModel mv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InicioViewModel.class);
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv.getMMapa().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.MapaActual>() {
            @Override
            public void onChanged(InicioViewModel.MapaActual mapaActual) {
                SupportMapFragment mapFragment =
                        (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(mapaActual);
            }
        });

        mv.obtenerUbicacion();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}