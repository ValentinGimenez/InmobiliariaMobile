package com.example.inmobiliariamobile.ui.inmuebles;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.models.Inmueble;
import com.example.inmobiliariamobile.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Inmueble>> mLista = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<String> mExito = new MutableLiveData<>();

    private final MutableLiveData<Void> mNuevo = new MutableLiveData<>();

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        cargarInmuebles();
    }

    public LiveData<List<Inmueble>> getMLista() { return mLista; }
    public LiveData<String> getMError() { return mError; }
    public LiveData<String> getMExito() { return mExito; }
    public LiveData<Void> getMNuevo(){ return mNuevo; }

    public void onClickAgregar(){
        mNuevo.setValue(null);
    }

    public void cargarInmuebles() {
        String token = ApiClient.leerToken(getApplication());
        Call<List<Inmueble>> llamada = ApiClient.getApiInmobiliaria().listarInmuebles("Bearer " + token);

        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    for (Inmueble inmueble : response.body()) {
                        inmueble.setEstado(inmueble.getEstado());
                        inmueble.setTipo(inmueble.getTipo());
                        inmueble.setUso(inmueble.getUso());
                    }
                    mLista.postValue(response.body());
                    if (response.body().isEmpty()) {
                        mError.setValue("No hay inmuebles registrados.");
                    } else {
                        // mExito.setValue("Inmuebles cargados correctamente.");
                    }
                } else {
                    mError.setValue("Error al obtener los inmuebles.");
                }
            }
            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                mError.setValue("Error de conexión con el servidor.");
            }
        });
    }
}
