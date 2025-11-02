package com.example.inmobiliariamobile.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.models.Inmueble;
import com.example.inmobiliariamobile.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble() {
        return mInmueble;
    }

    public LiveData<String> getMError() {
        return mError;
    }

    public LiveData<String> getMExito() {
        return mExito;
    }

    public void obtenerInmueble(Bundle inmuebleBundle) {
        Inmueble inmueble = (Inmueble) inmuebleBundle.getSerializable("inmueble");
        if (inmueble != null) {
            this.mInmueble.setValue(inmueble);
        }
    }

    public void actualizarDisponibilidad(Boolean disponible) {
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(disponible);
        inmueble.setIdInmueble(this.mInmueble.getValue().getIdInmueble());
        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> llamada = ApiClient.getApiInmobiliaria().actualizarInmueble("Bearer " + token, inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    mExito.setValue("Inmueble actualizado correctamente");
                } else {
                    mError.setValue("Error al actualizar el inmueble. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                mError.setValue("Error al contactar con el servidor.");
            }
        });
    }
}
