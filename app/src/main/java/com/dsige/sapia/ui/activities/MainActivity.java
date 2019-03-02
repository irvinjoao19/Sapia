package com.dsige.sapia.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dsige.sapia.R;
import com.dsige.sapia.context.repository.RoomRepository;
import com.dsige.sapia.context.retrofit.ApiRetrofit;
import com.dsige.sapia.context.retrofit.apiInterfaces.SapiaInterfaces;
import com.dsige.sapia.helper.MessageError;
import com.dsige.sapia.helper.Util;
import com.dsige.sapia.model.MenuPrincipal;
import com.dsige.sapia.model.Migracion;
import com.dsige.sapia.model.Usuario;
import com.dsige.sapia.ui.adapters.MenuAdapter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public RoomRepository roomRepository;
    public SapiaInterfaces sapiaInterfaces;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        roomRepository.closeRoom();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AppTheme));
                builder.setTitle(R.string.message);
                builder.setMessage(R.string.description_message);
                builder.setPositiveButton(R.string.aceptar, (d, id) -> {
                    LiveData<Usuario> u = roomRepository.getUsuario();
                    u.observe(this, usuario -> {
                        Completable logout = roomRepository.deleteUser(usuario);
                        logout.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d1) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        d.dismiss();
                                        logOut();
                                        System.exit(0);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.i("TAG", e.toString());
                                    }
                                });
                    });
                });
                builder.setNegativeButton(R.string.cancel, (d, i) -> d.dismiss());
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        roomRepository = new RoomRepository(this);
        existsUser(roomRepository.getUsuario());
    }

    private void existsUser(LiveData<Usuario> usuario) {
        usuario.observe(this, u -> {
            if (u == null) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                bindToolbar();
                bindUI();
            }
        });
    }

    private void bindToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_menu);
    }

    private void bindUI() {
        roomRepository = new RoomRepository(this);
        sapiaInterfaces = new ApiRetrofit().getAPI().create(SapiaInterfaces.class);
        List<MenuPrincipal> menus = new ArrayList<>();
        menus.add(new MenuPrincipal(1, getResources().getString(R.string.sincronizar), R.mipmap.ic_launcher));
        menus.add(new MenuPrincipal(2, getResources().getString(R.string.title_services), R.mipmap.ic_launcher));
        menus.add(new MenuPrincipal(3, getResources().getString(R.string.sincronizar), R.mipmap.ic_launcher));
        menus.add(new MenuPrincipal(5, getResources().getString(R.string.sincronizar), R.mipmap.ic_launcher));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        MenuAdapter menuAdapter = new MenuAdapter(menus, (m, view, position) -> {
            switch (m.getId()) {
                case 1:
                    sync();
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, ServicesActivity.class));
                    break;
                case 3:

                    break;
                case 4:
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    break;
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(menuAdapter);
    }

    private void sync() {
        AlertDialog.Builder builderSync = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AppTheme));
        builderSync.setTitle(R.string.message);
        builderSync.setMessage(R.string.seguro_sincronizar);
        builderSync.setPositiveButton(R.string.aceptar, (d, id) -> {
            syncRx();
            d.dismiss();
        });
        builderSync.setNegativeButton(R.string.cancel, (d, i) -> d.dismiss());
        AlertDialog alertDialogSync = builderSync.create();
        alertDialogSync.setCanceledOnTouchOutside(false);
        alertDialogSync.show();
    }

    private void syncRx() {

        AlertDialog.Builder builder = new AlertDialog.Builder(new android.view.ContextThemeWrapper(this, R.style.AppTheme));
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.dialog_login, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

//        String json = new Gson().toJson(f);
//        Log.i("TAG", json);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        String[] mensaje = {""};
        Observable<Migracion> migracionResultadoObservable = sapiaInterfaces.getSincronation();
        migracionResultadoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Migracion>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Migracion m) {
                        mensaje[0] = m.getMensaje();
                        if (mensaje[0].contains("Sincronización")) {
                            roomRepository.insertMigracion(m);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Converter<ResponseBody, MessageError> errorConverter = new ApiRetrofit().getAPI().responseBodyConverter(MessageError.class, new Annotation[0]);
                            try {
                                MessageError error = errorConverter.convert(Objects.requireNonNull(body));
                                if (error.getMessage().contains("sincronizar")) {
                                    Util.toastMensaje(MainActivity.this, getString(R.string.no_sincronizar));
                                } else if (error.getMessage().contains("permisos")) {
                                    Util.toastMensaje(MainActivity.this, getString(R.string.permission));
                                } else {
                                    Util.toastMensaje(MainActivity.this, error.getMessage());
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            Util.toastMensaje(MainActivity.this, e.toString());
                        }
                        Log.i("TAG", e.toString());
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        if (mensaje[0].contains("Sincronización")) {
                            Util.toastMensaje(MainActivity.this, getString(R.string.synchronization_complet));
                        } else {
                            Util.toastMensaje(MainActivity.this, getString(R.string.new_update));
                        }
                        dialog.dismiss();
                    }
                });
    }

    private void logOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}