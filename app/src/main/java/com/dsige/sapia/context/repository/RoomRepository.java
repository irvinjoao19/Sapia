package com.dsige.sapia.context.repository;

import android.app.Application;
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

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RoomRepository {

    private AppDataBase appDataBase;

    public RoomRepository(Application application) {
        this.appDataBase = AppDataBase.getDatabase(application);
    }

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

    public Completable insertRegisterWithDetail(LifecycleOwner owner, int id, SapiaRegistroDetalle d) {
        return Completable.fromAction(() -> {
            LiveData<SapiaRegistro> registro = appDataBase.configurationDao().getRegistro(id);
            registro.observe(owner, s -> {
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

    public Completable deletePersonal(Personal p) {
        return Completable.fromAction(() -> appDataBase.configurationDao().deletePersonal(p));
    }

}