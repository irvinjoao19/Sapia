package com.dsige.sapia.data.local.dao;

import com.dsige.sapia.data.local.model.Personal;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PersonalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersonal(Personal personal);

    @Update
    void updatePersonal(Personal p);

    @Query("SELECT * FROM Personal")
    LiveData<List<Personal>> getPersonal();

    @Query("SELECT * FROM Personal WHERE personalId =:id")
    LiveData<Personal> getPersonalById(int id);

    @Delete
    void deletePersonal(Personal p);

}