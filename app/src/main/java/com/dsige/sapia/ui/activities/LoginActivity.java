package com.dsige.sapia.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.dsige.sapia.R;
import com.dsige.sapia.context.retrofit.ApiRetrofit;
import com.dsige.sapia.context.retrofit.apiInterfaces.SapiaInterfaces;
import com.dsige.sapia.context.room.RoomViewModel;
import com.dsige.sapia.helper.MessageError;
import com.dsige.sapia.helper.Permission;
import com.dsige.sapia.helper.Util;
import com.dsige.sapia.model.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        int cantidad = 0;
        switch (requestCode) {
            case 1: {
                for (int valor : grantResults) {
                    if (valor == PackageManager.PERMISSION_DENIED) {
                        cantidad++;
                    }
                }
                if (cantidad >= 1) {
                    buttonEnviar.setVisibility(View.GONE);
                    messagePermission();
                } else {
                    buttonEnviar.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEnviar:
                Util.hideKeyboard(this);
                String user = Objects.requireNonNull(editTextUser.getText()).toString();
                String pass = Objects.requireNonNull(editTextPass.getText()).toString();
                if (!user.isEmpty()) {
                    if (!pass.isEmpty()) {
                        enterMain(user, pass);
                    } else {
                        Util.snackBarMensaje(v, "Ingrese Contraseña");
                    }
                } else {
                    Util.snackBarMensaje(v, "Ingrese Usuario");
                }
                break;
        }
    }

    @BindView(R.id.editTextUser)
    TextInputEditText editTextUser;
    @BindView(R.id.editTextPass)
    TextInputEditText editTextPass;
    @BindView(R.id.buttonEnviar)
    MaterialButton buttonEnviar;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    SapiaInterfaces sapiaInterfaces;
    RoomViewModel roomRepository;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        roomRepository.CloseRoom();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        bindUI();
        if (Build.VERSION.SDK_INT >= 23) {
            permision();
        }
    }

    private void bindUI() {
        roomRepository = ViewModelProviders.of(this).get(RoomViewModel.class);
        sapiaInterfaces = new ApiRetrofit().getAPI().create(SapiaInterfaces.class);
        buttonEnviar.setOnClickListener(this);
    }

    private void permision() {
        if (!Permission.hasPermissions(this, Permission.PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, Permission.PERMISSIONS, Permission.PERMISSION_ALL);
        }
    }

    private void enterMain(String usuario, String pass) {
        builder = new AlertDialog.Builder(new ContextThemeWrapper(LoginActivity.this, R.style.AppTheme));
        @SuppressLint("InflateParams") View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_login, null);
        builder.setView(view);
        dialog = builder.create();

        Usuario u = new Usuario(usuario, pass);
        String json = new Gson().toJson(u);
        Log.i("TAG", json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Observable<Usuario> userObservable = sapiaInterfaces.getUser(body);

        String[] mensaje = {""};

        userObservable.subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Usuario>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Usuario user) {
                        mensaje[0] = user.getMensaje();
                        if (mensaje[0].equals("Go")) {
                            roomRepository.insertUser(user);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Converter<ResponseBody, MessageError> errorConverter = new ApiRetrofit().getAPI().responseBodyConverter(MessageError.class, new Annotation[0]);
                            try {
                                MessageError error = errorConverter.convert(Objects.requireNonNull(body));
                                if (error.getMessage().contains("Usuario")) {
                                    Util.toastMensaje(LoginActivity.this, getString(R.string.error_user));
                                } else {
                                    Util.toastMensaje(LoginActivity.this, error.getMessage());
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            Util.toastMensaje(LoginActivity.this, e.toString());
                        }
                        Log.i("TAG", e.toString());
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        switch (mensaje[0]) {
                            case "Go":
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                break;
                            case "Pass":
                                Util.toastMensaje(LoginActivity.this, getString(R.string.error_pass));
                                break;
                            default:
                                Util.toastMensaje(LoginActivity.this, getString(R.string.permission));
                                break;
                        }
                    }
                });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void messagePermission() {
        builder = new AlertDialog.Builder(new ContextThemeWrapper(LoginActivity.this, R.style.AppTheme));
        builder.setTitle(R.string.message_permission);
        builder.setMessage(R.string.content_permission);
        builder.setPositiveButton(R.string.aceptar, (dialog, id) -> permision());
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
}