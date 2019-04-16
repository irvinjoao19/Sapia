package com.dsige.sapia.data.local.repository;

import com.dsige.sapia.data.local.model.Personal;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;

public interface AppRepository {

    // TODO : CLIENTE

    LiveData<List<Personal>> populatPersonal();

    Completable insertPersonal(Personal p);

    Completable updatePersonal(Personal p);

    Completable deletePersonal(Personal p);

    LiveData<Personal> getPersonalById(int id);

    void closeRoom();
}
