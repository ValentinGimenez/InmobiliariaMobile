package com.example.inmobiliariamobile.ui.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<String> mExito = new MutableLiveData<>();
    private final MutableLiveData<Void>   mVolver = new MutableLiveData<>();

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError() { return mError; }
    public LiveData<String> getMExito() { return mExito; }
    public LiveData<Void>   getMVolver(){ return mVolver; }

    public void cambiarClave(String actual, String nueva, String confirmar) {
        if (actual == null || actual.trim().isEmpty()
                || nueva == null || nueva.trim().isEmpty()
                || confirmar == null || confirmar.trim().isEmpty()) {
            mError.setValue("No deje campos vacíos.");
            return;
        }
        if (nueva.length() < 6) {
            mError.setValue("La nueva clave debe tener al menos 6 caracteres.");
            return;
        }
        if (!nueva.equals(confirmar)) {
            mError.setValue("La confirmación no coincide.");
            return;
        }
        if (nueva.equals(actual)) {
            mError.setValue("La nueva clave no puede ser igual a la actual.");
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        Call<Void> call = ApiClient.getApiInmobiliaria()
                .cambiarPassword("Bearer " + token, actual, nueva);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mExito.setValue("Clave cambiada correctamente.");
                    mVolver.setValue(null);
                } else {
                    mError.setValue("No se pudo cambiar la clave. Verifique la actual.");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mError.setValue("Error de conexión con el servidor.");
            }
        });
    }
}
