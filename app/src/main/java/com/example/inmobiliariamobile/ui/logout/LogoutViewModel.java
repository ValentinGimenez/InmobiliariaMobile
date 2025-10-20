package com.example.inmobiliariamobile.ui.logout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamobile.request.ApiClient;

public class LogoutViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<String> mExito = new MutableLiveData<>();
    private final MutableLiveData<Void> mMostrarDialogo = new MutableLiveData<>();
    private final MutableLiveData<Void> mNavegarLogin   = new MutableLiveData<>();
    private final MutableLiveData<Void> mCerrarPantalla = new MutableLiveData<>();

    public LogoutViewModel(@NonNull Application application) {
        super(application);
        mMostrarDialogo.setValue(null);
    }

    public LiveData<String> getMError() { return mError; }
    public LiveData<String> getMExito() { return mExito; }

    public LiveData<Void> getMMostrarDialogo() { return mMostrarDialogo; }
    public LiveData<Void> getMNavegarLogin()   { return mNavegarLogin; }
    public LiveData<Void> getMCerrarPantalla() { return mCerrarPantalla; }

    public void confirmarLogout() {
        ApiClient.borrarToken(getApplication());
        mExito.setValue("Sesión cerrada correctamente.");
        mNavegarLogin.setValue(null);
    }
    public void cancelarLogout() {
        mCerrarPantalla.setValue(null);
    }
}
