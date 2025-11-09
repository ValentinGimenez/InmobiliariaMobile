package com.example.inmobiliariamobile.ui.pagos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariamobile.databinding.FragmentInmueblesBinding;
import com.example.inmobiliariamobile.databinding.FragmentPagosBinding;
import com.example.inmobiliariamobile.models.Contrato;
import com.example.inmobiliariamobile.models.Pago;
import com.example.inmobiliariamobile.ui.contratos.ContratoInmuebleAdapter;
import com.example.inmobiliariamobile.ui.pagos.PagosViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PagosFragment extends Fragment {

    private FragmentPagosBinding binding;
    private PagosViewModel vm;
    private PagosAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vm = ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())
                .create(PagosViewModel.class);

        binding = FragmentPagosBinding.inflate(inflater, container, false);
        //Contrato contrato = (Contrato) getArguments().getSerializable("contrato");
        adapter = new PagosAdapter(vm.getMPagos(), getLayoutInflater(), requireContext());
        binding.rvPagos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        binding.rvPagos.setAdapter(adapter);
        vm.getMPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override public void onChanged(List<Pago> pagos) {
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
        vm.getMPrecioInmueble().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double precioInmueble) {
                if (adapter != null) {
                    adapter.setPrecioInmueble(precioInmueble);
                }
            }
        });
        vm.cargarPagos(getArguments());
        return binding.getRoot();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}