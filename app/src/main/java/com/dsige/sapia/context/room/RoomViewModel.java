package com.dsige.sapia.context.room;

import android.app.Application;

import com.dsige.sapia.context.repository.RoomRepository;
import com.dsige.sapia.model.Migracion;
import com.dsige.sapia.model.Personal;
import com.dsige.sapia.model.SapiaRegistro;
import com.dsige.sapia.model.SapiaRegistroDetalle;
import com.dsige.sapia.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class RoomViewModel extends AndroidViewModel {

    private RoomRepository roomRepository;

    public RoomViewModel(@NonNull Application application) {
        super(application);
        roomRepository = new RoomRepository(application);
    }

    public void CloseRoom() {
        roomRepository.closeRoom();
    }


    public void insertUser(Usuario user) {
        roomRepository.insertUser(user);
    }

    public LiveData<Usuario> getUsuario() {
        return roomRepository.getUsuario();
    }

    public Completable deleteUser(Usuario user) {
        return roomRepository.deleteUser(user);
    }


    // TODO: SINCRONIZACION

    public void insertMigracion(Migracion migracion) {
        roomRepository.insertMigracion(migracion);
    }


    public Flowable<Migracion> getMigracion() {
        return roomRepository.getMigracion();
    }


    // TODO : INSERT REGISTRO

    public LiveData<SapiaRegistro> getRegistro(int id) {
        return roomRepository.getRegistro(id);
    }

    public Completable insertRegisterWithDetail(LifecycleOwner owner, int id, SapiaRegistroDetalle d) {
        return roomRepository.insertRegisterWithDetail(owner, id, d);
    }

    // TODO : PERSONAL

    public LiveData<List<Personal>> getPersonals() {
        return roomRepository.getPersonals();
    }

    public Completable insertPersonal(Personal p) {
        return roomRepository.insertPersonal(p);
    }

    public Completable deletePersonal(Personal p) {
        return roomRepository.deletePersonal(p);
    }
}