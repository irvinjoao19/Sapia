package com.dsige.sapia.context.repository;

import android.content.Context;
import android.util.Log;

import com.dsige.sapia.context.room.AppDataBase;
import com.dsige.sapia.model.Migracion;
import com.dsige.sapia.model.Personal;
import com.dsige.sapia.model.Usuario;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RoomRepository {

    private AppDataBase appDataBase;

    public RoomRepository(Context context) {
        this.appDataBase = AppDataBase.getDatabase(context);
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


    // TODO: SINCRONIZACION

    public void insertMigracion(Migracion migracion) {

        Completable.fromAction(() -> appDataBase.migrationDao().inserMigrationTask(migracion))
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

    public LiveData<List<Personal>> getListPersonal(int cargoID) {
        return appDataBase.migrationDao().getPersonalsTask(cargoID);
    }
}