package com.dsige.sapia.data.local.repository;


import com.dsige.sapia.data.local.AppDataBase;
import com.dsige.sapia.data.local.model.Personal;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.functions.Action;

public class AppRepoImp implements AppRepository {

    private ApiService apiService;
    private AppDataBase dataBase;

    public AppRepoImp(ApiService apiService, AppDataBase dataBase) {
        this.apiService = apiService;
        this.dataBase = dataBase;
    }

    @Override
    public LiveData<List<Personal>> populatPersonal() {
        return dataBase.personalDao().getPersonal();
    }

    @Override
    public Completable insertPersonal(Personal p) {
        return Completable.fromAction(() -> dataBase.personalDao().insertPersonal(p));
    }

    @Override
    public Completable updatePersonal(Personal p) {
        return Completable.fromAction(() -> dataBase.personalDao().updatePersonal(p));
    }

    @Override
    public Completable deletePersonal(Personal p) {
        return Completable.fromAction(() -> dataBase.personalDao().deletePersonal(p));
    }

    @Override
    public LiveData<Personal> getPersonalById(int id) {
        return dataBase.personalDao().getPersonalById(id);
    }

    @Override
    public void closeRoom() {
        dataBase.close();
    }
}
