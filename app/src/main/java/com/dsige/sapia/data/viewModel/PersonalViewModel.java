package com.dsige.sapia.data.viewModel;


import com.dsige.sapia.data.local.model.Personal;
import com.dsige.sapia.data.local.repository.AppRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;

public class PersonalViewModel extends ViewModel {

    private AppRepository roomRepository;

    @Inject
    public PersonalViewModel(AppRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void CloseRoom() {
        roomRepository.closeRoom();
    }


    // TODO : PERSONAL

    public LiveData<List<Personal>> getPersonals() {
        return roomRepository.populatPersonal();
    }

    public Completable insertPersonal(Personal p) {
        return roomRepository.insertPersonal(p);
    }

    public Completable updatePersonal(Personal p) {
        return roomRepository.updatePersonal(p);
    }

    public Completable deletePersonal(Personal p) {
        return roomRepository.deletePersonal(p);
    }
}