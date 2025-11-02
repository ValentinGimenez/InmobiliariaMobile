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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.inmobiliariamobile.R;
import com.example.inmobiliariamobile.databinding.FragmentInmueblesBinding;
import com.example.inmobiliariamobile.models.Inmueble;
import com.example.inmobiliariamobile.ui.inquilinos.InmuebleAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class InmueblesFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private InmueblesViewModel mv;
    private InmuebleAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mv = ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())
                .create(InmueblesViewModel.class);

        binding = FragmentInmueblesBinding.inflate(inflater, container, false);

        adapter = new InmuebleAdapter(mv.getMLista(), getLayoutInflater(), getContext());
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        binding.rvInmuebles.setLayoutManager(glm);
        binding.rvInmuebles.setAdapter(adapter);

        mv.getMLista().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                adapter.notifyDataSetChanged();
            }
        });

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.GREEN)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        binding.fabAgregarInmueble.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_nav_inmuebles_to_nav_inmueble_nuevo)
        );

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}