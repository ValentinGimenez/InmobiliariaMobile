package com.example.inmobiliariamobile.ui.inmuebles;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariamobile.R;
import com.example.inmobiliariamobile.databinding.FragmentInmuebleNuevoBinding;
import com.example.inmobiliariamobile.ui.perfil.PerfilViewModel;
import com.google.android.material.snackbar.Snackbar;

public class InmuebleNuevoFragment extends Fragment {

    private InmuebleNuevoViewModel mv;
    private FragmentInmuebleNuevoBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static InmuebleNuevoFragment newInstance() {
        return new InmuebleNuevoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InmuebleNuevoViewModel.class);
        binding = FragmentInmuebleNuevoBinding.inflate(getLayoutInflater());

        ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.tipos_inmueble, android.R.layout.simple_spinner_item);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spTipo.setAdapter(tipoAdapter);

        ArrayAdapter<CharSequence> usoAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.usos_inmueble, android.R.layout.simple_spinner_item);
        usoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spUso.setAdapter(usoAdapter);

        abrirGaleria();

        binding.btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });

        mv.getMUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                if (uri != null) {
                    binding.imgView.setImageURI(uri);
                }
            }
        });

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED).setTextColor(Color.WHITE).show();
            }
        });

        mv.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.GREEN).setTextColor(Color.WHITE).show();
                androidx.navigation.Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.nav_inmuebles);
            }
        });

        binding.btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.cargarInmueble(
                        binding.etDireccion.getText().toString(),
                        binding.spTipo.getSelectedItem().toString(),
                        binding.spUso.getSelectedItem().toString(),
                        binding.etAmbientes.getText().toString(),
                        binding.etSuperficie.getText().toString(),
                        binding.etLatitud.getText().toString(),
                        binding.etLongitud.getText().toString(),
                        binding.etValor.getText().toString(),
                        binding.cbDisp.isChecked()
                );


            }
        });

        return binding.getRoot();
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("InmuebleNuevoFragment", "Result: " + result);
                        mv.recibirFoto(result);
                    }
                });
    }
}
