package com.dsige.sapia.context.repository;

import android.content.Context;
import android.util.Log;

import com.dsige.sapia.context.room.AppDataBase;
import com.dsige.sapia.model.Migracion;
import com.dsige.sapia.model.Personal;
import com.dsige.sapia.model.SapiaRegistro;
import com.dsige.sapia.model.SapiaRegistroDetalle;
import com.dsige.sapia.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RoomRepository extends LifecycleRegistry {

    private AppDataBase appDataBase;
    private LifecycleOwner provider;

    /**
     * Creates a new LifecycleRegistry for the given provider.
     * <p>
     * You should usually create this inside your LifecycleOwner class's constructor and hold
     * onto the same instance.
     *
     * @param provider The owner LifecycleOwner
     */
    public RoomRepository(@NonNull LifecycleOwner provider, Context context) {
        super(provider);
        this.provider = provider;
        this.appDataBase = AppDataBase.getDatabase(context);
    }

//    public RoomRepository(Context context) {
//        this.appDataBase = AppDataBase.getDatabase(context);
//    }

    public void closeRoom() {
        appDataBase.close();
    }


    public void insertUser(Usuario user) {
        Completable.fromAction(() -> appDataBase.userDao().insertUserTask(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG", "COMPLETADO");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG", e.toString());
                    }
                });
    }

    public LiveData<Usuario> getUsuario() {
        return appDataBase.userDao().getUserTask();
    }

    public Completable deleteUser(Usuario user) {
        return Completable.fromAction(() -> appDataBase.userDao().deleteUserTask(user));
    }


    // TODO: SINCRONIZACION

    public void insertMigracion(Migracion migracion) {
        Completable.fromAction(() -> appDataBase.configurationDao().inserMigrationTask(migracion))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG", "COMPLETADO");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG", e.toString());
                    }
                });
    }


    public Flowable<Migracion> getMigracion() {
        return appDataBase.configurationDao().getMigracionTask();
    }


    // TODO : INSERT REGISTRO

    public LiveData<SapiaRegistro> getRegistro(int id) {
        return appDataBase.configurationDao().getRegistro(id);
    }

    public Completable insertRegisterWithDetail(int id, SapiaRegistroDetalle d) {
        return Completable.fromAction(() -> {
            LiveData<SapiaRegistro> registro = appDataBase.configurationDao().getRegistro(id);
            registro.observe(provider, s -> {
                List<SapiaRegistroDetalle> sapiaRegistroDetalles = new ArrayList<>();
                sapiaRegistroDetalles.add(d);
                s.setSapiaRegistroDetalles(sapiaRegistroDetalles);
                appDataBase.configurationDao().insertRegistro(s);
            });
        });
    }

    // TODO : PERSONAL

    public LiveData<List<Personal>> getPersonals() {
        return appDataBase.configurationDao().getPersonal();
    }

    public Completable insertPersonal(Personal p) {
        return Completable.fromAction(() -> appDataBase.configurationDao().insertPersonal(p));
    }

}