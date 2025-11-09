package com.example.inmobiliariamobile.ui.contratos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.models.Contrato;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ContratoDetalleViewModel extends AndroidViewModel {

    private final MutableLiveData<Contrato> mContrato = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();

    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getMContrato() { return mContrato; }
    public LiveData<String> getMError() { return mError; }

    public void obtenerContrato(Bundle bundle) {
        if (bundle == null) {
            mError.setValue("No se recibieron datos del contrato.");
            return;
        }
        Contrato contrato = (Contrato) bundle.getSerializable("contrato");
        try {
            contrato.setFecha_inicio(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                            .parse(contrato.getFecha_inicio())));
            contrato.setFecha_fin(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                            .parse(contrato.getFecha_fin())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (contrato == null) {
            mError.setValue("Contrato inválido.");
        } else {
            mContrato.setValue(contrato);
        }
    }

}
