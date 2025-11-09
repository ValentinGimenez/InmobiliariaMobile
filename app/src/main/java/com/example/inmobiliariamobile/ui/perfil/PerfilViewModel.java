package com.example.inmobiliariamobile.ui.perfil;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.models.Propietario;
import com.example.inmobiliariamobile.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<String> mExito = new MutableLiveData<>();
    private final MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCamposEditables = new MutableLiveData<>();
    private final MutableLiveData<String> mTextoBoton = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        mCamposEditables.setValue(false);
        mTextoBoton.setValue("EDITAR");
    }

    public LiveData<String> getMError() { return mError; }
    public LiveData<String> getMExito() { return mExito; }
    public LiveData<Propietario> getMPropietario() { return mPropietario; }
    public LiveData<Boolean> getMCamposEditables() { return mCamposEditables; }
    public LiveData<String> getMTextoBoton() { return mTextoBoton; }

    public void leerPropietario() {
        String token = ApiClient.leerToken(getApplication());
        Call<Propietario> llamada = ApiClient.getApiInmobiliaria().obtenerPropietario("Bearer " + token);

        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Propietario p = response.body();
                    mPropietario.postValue(response.body());
                } else {
                    mError.setValue("Error al obtener los datos del perfil.");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                mError.setValue("Error de conexión con el servidor.");
            }
        });
    }

    public void editarGuardar(String nombre, String apellido, String dni, String telefono, String email, String accion) {
        if (accion.equalsIgnoreCase("EDITAR")) {
            mCamposEditables.setValue(true);
            mTextoBoton.setValue("GUARDAR");
            return;
        }

        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            mError.setValue("No deje campos vacíos.");
            return;
        }

        if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,50}$")) {
            mError.setValue("Nombre inválido.");
            return;
        }

        if (!apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,50}$")) {
            mError.setValue("Apellido inválido.");
            return;
        }

        if (!dni.matches("^\\d{7,8}$")) {
            mError.setValue("DNI inválido (7-8 dígitos).");
            return;
        }

        if (!telefono.matches("^\\d{6,15}$")) {
            mError.setValue("Teléfono inválido.");
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            mError.setValue("Correo electrónico inválido.");
            return;
        }

        Propietario actual = mPropietario.getValue();
        if (actual == null) {
            mError.setValue("Error interno: no hay propietario cargado.");
            return;
        }

        Propietario actualizado = new Propietario(
                apellido,
                telefono,
                nombre,
                actual.getId(),
                email,
                dni,
                null
        );

        String token = ApiClient.leerToken(getApplication());
        Call<Propietario> llamada = ApiClient.getApiInmobiliaria().actualizarPropietario("Bearer " + token, actualizado);

        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mPropietario.postValue(response.body());
                    mExito.setValue("Perfil actualizado correctamente.");
                    mCamposEditables.setValue(false);
                    mTextoBoton.setValue("EDITAR");
                } else {
                    mError.setValue("Error al actualizar el perfil.");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                mError.setValue("Error de conexión al servidor.");
            }
        });
    }
}
