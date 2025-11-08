package com.example.inmobiliariamobile.ui.inquilinos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.models.Contrato;
import com.example.inmobiliariamobile.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Contrato>> mContratos = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<String> mExito = new MutableLiveData<>();

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        cargarContratosVigentes();
    }

    public LiveData<List<Contrato>> getMContratos() { return mContratos; }
    public LiveData<String> getMError() { return mError; }
    public LiveData<String> getMExito() { return mExito; }

    public void cargarContratosVigentes() {
        String token = ApiClient.leerToken(getApplication());
        Call<List<Contrato>> call = ApiClient.getApiInmobiliaria().obtenerContratos("Bearer " + token);
        call.enqueue(new Callback<List<Contrato>>() {
            @Override public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mContratos.postValue(response.body());
                    if (response.body().isEmpty()) mError.setValue("No tenés contratos vigentes.");
                } else {
                    mError.setValue("No se pudieron obtener contratos vigentes. Código: " + response.code());
                }
            }
            @Override public void onFailure(Call<List<Contrato>> call, Throwable t) {
                mError.setValue("Error de conexión con el servidor.");
            }
        });
    }
}
