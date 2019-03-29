package com.dsige.sapia.context.dao;

import com.dsige.sapia.model.Migracion;
import com.dsige.sapia.model.Personal;
import com.dsige.sapia.model.SapiaRegistro;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface ConfigurationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserMigrationTask(Migracion migracion);

    @Delete
    void deleteMigrationTask(Migracion migracion);

    @Query("SELECT * FROM Migracion")
    Flowable<Migracion> getMigracionTask();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRegistro(SapiaRegistro registro);

    @Query("SELECT * FROM SapiaRegistro WHERE registroId =:id")
    LiveData<SapiaRegistro> getRegistro(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersonal(Personal personal);

    @Query("SELECT * FROM Personal")
    LiveData<List<Personal>> getPersonal();

    @Delete
    void deletePersonal(Personal p);

}