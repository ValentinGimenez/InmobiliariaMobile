package com.example.inmobiliariamobile.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.models.Inmueble;
import com.example.inmobiliariamobile.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleNuevoViewModel extends AndroidViewModel {

    private final MutableLiveData<Uri> mUri = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<String> mExito = new MutableLiveData<>();

    public InmuebleNuevoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getMUri() { return mUri; }
    public LiveData<String> getMError() { return mError; }
    public LiveData<String> getMExito() { return mExito; }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getData() != null) {
                mUri.setValue(data.getData());
            } else {
                mError.setValue("No se pudo obtener la imagen.");
            }
        } else {
            mError.setValue("Selección de imagen cancelada.");
        }
    }

    public void cargarInmueble(String direccion,
                               String tipo,
                               String uso,
                               String ambientes,
                               String superficie,
                               String latitud,
                               String longitud,
                               String valor,
                               boolean disponible) {

        if (direccion.isEmpty() || tipo.isEmpty() || uso.isEmpty() ||
                ambientes.isEmpty() || superficie.isEmpty() ||
                latitud.isEmpty() || longitud.isEmpty() || valor.isEmpty()) {
            mError.setValue("Debe ingresar todos los campos.");
            return;
        }

        if (mUri.getValue() == null) {
            mError.setValue("Debe seleccionar una foto.");
            return;
        }

        int ambientesPars, superficiePars;
        double precio, lat, lon;
        try {
            ambientesPars = Integer.parseInt(ambientes);
            superficiePars = Integer.parseInt(superficie);
            precio = Double.parseDouble(valor);
            lat = Double.parseDouble(latitud);
            lon = Double.parseDouble(longitud);
        } catch (NumberFormatException nfe) {
            mError.setValue("Ingrese números válidos en los campos numéricos.");
            return;
        }

        Inmueble inmueble = new Inmueble();
        inmueble.setDireccion(direccion);
        inmueble.setTipo(tipo);
        inmueble.setUso(uso);
        inmueble.setAmbientes(ambientesPars);
        inmueble.setSuperficie(superficiePars);
        inmueble.setEje_x(lat);
        inmueble.setEje_y(lon);
        inmueble.setPrecio(precio);
        inmueble.setEstado(disponible ? "Disponible" : "Suspendido");

        Log.d("InmuebleData", "Inmueble creado: " + new Gson().toJson(inmueble));

        byte[] imagen = transformarImagen();
        if (imagen.length == 0) {
            mError.setValue("No se pudo procesar la imagen.");
            return;
        }

        String inmuebleJson = new Gson().toJson(inmueble);
        RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

        Log.d("InmuebleData", "Inmueble JSON: " + inmuebleJson);

        String token = ApiClient.leerToken(getApplication());
        Log.d("InmuebleData", "Token: " + token);

        Call<Inmueble> call = ApiClient.getApiInmobiliaria()
                .cargarInmueble("Bearer " + token, imagenPart, inmuebleBody);

        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    mExito.setValue("Inmueble cargado exitosamente.");
                    Log.d("InmuebleData", "Inmueble cargado exitosamente.");
                } else {
                    mError.setValue("Error al cargar inmueble. Código: " + response.code());
                    Log.e("InmuebleData", "Error al cargar inmueble. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                mError.setValue("Error de conexión con el servidor.");
                Log.e("InmuebleData", "Error de conexión con el servidor.", t);
            }
        });
    }


    private byte[] transformarImagen() {
        try {
            Uri uri = mUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException er) {
            mError.setValue("No se pudo abrir la imagen seleccionada.");
            return new byte[]{};
        }
    }
}
