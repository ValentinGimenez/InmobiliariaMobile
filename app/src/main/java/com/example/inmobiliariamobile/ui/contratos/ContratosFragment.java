package com.example.inmobiliariamobile.ui.contratos;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.inmobiliariamobile.databinding.FragmentInmueblesBinding;
import com.example.inmobiliariamobile.models.Contrato;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ContratosFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private ContratosViewModel vm;
    private ContratoInmuebleAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vm = ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())
                .create(ContratosViewModel.class);

        binding = FragmentInmueblesBinding.inflate(inflater, container, false);

        adapter = new ContratoInmuebleAdapter(vm.getMContratos(), getLayoutInflater(), requireContext());
        binding.rvInmuebles.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvInmuebles.setAdapter(adapter);
        binding.fabAgregarInmueble.setVisibility(View.GONE);
        vm.getMContratos().observe(getViewLifecycleOwner(), new Observer<List<Contrato>>() {
            @Override public void onChanged(List<Contrato> contratos) {
                adapter.notifyDataSetChanged();
            }
        });

        vm.getMError().observe(getViewLifecycleOwner(), s ->
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show()
        );

        vm.getMExito().observe(getViewLifecycleOwner(), s ->
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.GREEN)
                        .setTextColor(Color.WHITE)
                        .show()
        );

        return binding.getRoot();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
