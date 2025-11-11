package com.example.inmobiliariamobile;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariamobile.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        solicitarPermisos();

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv.getMError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMAgitar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    mv.hacerLlamadaInmobiliaria();
                }
            }
        });

        binding.btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.login(binding.etUsuario.getText().toString(), binding.etPassword.getText().toString());
            }
        });
    }

    public void solicitarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mv.reiniciarSensor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv.detenerSensor();
    }
}
