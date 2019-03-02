package com.dsige.sapia.context.dao;

import com.dsige.sapia.model.Migracion;
import com.dsige.sapia.model.Personal;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface MigrationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserMigrationTask(Migracion migracion);

    @Delete
    void deleteMigrationTask(Migracion migracion);


    @Query("SELECT personalId,cargoId,nombrePersonal,nombreCargo,estado FROM Migracion,Personal where Personal.cargoId = :cargoId")
    LiveData<List<Personal>> getPersonalsTask(int cargoId);

    @Query("SELECT * FROM Migracion")
    Flowable<Migracion> getMigracionTask();

}