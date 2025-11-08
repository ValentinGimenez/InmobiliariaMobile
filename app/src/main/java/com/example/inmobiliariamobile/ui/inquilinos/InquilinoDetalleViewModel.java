package com.example.inmobiliariamobile.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.models.Inquilino;

public class InquilinoDetalleViewModel extends AndroidViewModel {

    private final MutableLiveData<Inquilino> mInquilino = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();

    public InquilinoDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getMInquilino() { return mInquilino; }
    public LiveData<String> getMError() { return mError; }

    public void obtenerInquilino(Bundle bundle) {
        if (bundle == null) {
            mError.setValue("No se recibieron datos del inquilino.");
            return;
        }
        Inquilino inq = (Inquilino) bundle.getSerializable("inquilino");
        if (inq == null) {
            mError.setValue("Inquilino inválido.");
        } else {
            mInquilino.setValue(inq);
        }
    }
}
