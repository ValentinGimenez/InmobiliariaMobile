package com.example.inmobiliariamobile.ui.pagos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.models.Contrato;
import com.example.inmobiliariamobile.models.Inmueble;
import com.example.inmobiliariamobile.models.Pago;
import com.example.inmobiliariamobile.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Pago>> mPagos = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<String> mExito = new MutableLiveData<>();
    private final MutableLiveData<Double> mPrecioInmueble = new MutableLiveData<>();
    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getMPagos() { return mPagos; }
    public LiveData<String> getMError() { return mError; }
    public LiveData<String> getMExito() { return mExito; }
    public LiveData<Double> getMPrecioInmueble() { return mPrecioInmueble; }
    public void cargarPagos(Bundle args) {
        try {
            Contrato contrato = (Contrato) args.getSerializable("contrato");

            if (contrato == null || contrato.getId() == 0) {
                mError.setValue("No se pudo obtener el contrato o su ID.");
                return;
            }

            Double valorInmueble = contrato.getInmueble().getPrecio();

            mPrecioInmueble.setValue(valorInmueble);

            String token = ApiClient.leerToken(getApplication());

            Call<List<Pago>> call = ApiClient.getApiInmobiliaria()
                    .obtenerPagos("Bearer " + token, contrato.getId());

            call.enqueue(new Callback<List<Pago>>() {
                @Override
                public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Pago> pagos = response.body();
                        mPagos.postValue(pagos);
                        if (pagos.isEmpty()) {
                            mError.setValue("No hay pagos asociados al contrato.");
                        }
                    } else {
                        mError.setValue("Error al obtener pagos. Código: " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<List<Pago>> call, Throwable t) {
                    mError.setValue("Error de conexión con el servidor: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            mError.setValue("Error al procesar los datos del contrato: " + e.getMessage());
        }
    }


}