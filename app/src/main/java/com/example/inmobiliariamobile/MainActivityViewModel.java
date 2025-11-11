package com.example.inmobiliariamobile;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.inmobiliariamobile.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel implements SensorEventListener {

    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAgitar = new MutableLiveData<>();
    private long lastShakeTime = 0;
    private static final long SHAKE_THRESHOLD = 1000;
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        sensorManager = (SensorManager) application.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    public LiveData<String> getMError() {
        return mError;
    }

    public LiveData<Boolean> getMAgitar() {
        return mAgitar;
    }

    public void login(String email, String clave) {
        if (email == null || email.isEmpty() || clave == null || clave.isEmpty()) {
            mError.setValue("Debe completar todos los campos.");
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(email, clave);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    Intent intent = new Intent(getApplication(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplication().startActivity(intent);
                } else {
                    mError.postValue("Usuario o contraseña incorrectos.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mError.postValue("Error de conexión con el servidor.");
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gForce = (float) Math.sqrt(x * x + y * y + z * z) / SensorManager.GRAVITY_EARTH;

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastShakeTime > SHAKE_THRESHOLD) {
                    lastShakeTime = currentTime;
                    mAgitar.setValue(true);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void hacerLlamadaInmobiliaria() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:2664553747"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    public void detenerSensor() {
        sensorManager.unregisterListener(this);
    }

    public void reiniciarSensor() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }
}
