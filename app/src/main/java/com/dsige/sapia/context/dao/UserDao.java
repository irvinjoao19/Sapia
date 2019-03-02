package com.dsige.sapia.context.dao;

import com.dsige.sapia.model.Usuario;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserTask(Usuario user);

//    @Query("SELECT * FROM Usuario ORDER BY created_at desc")
//    LiveData<List<UserDao>> fetchAllTasks();


    @Query("SELECT * FROM Usuario")
    LiveData<Usuario> getUserTask();

    @Query("SELECT * FROM Usuario WHERE usuarioId =:usuarioId")
    LiveData<Usuario> getUserTaskById(int usuarioId);

    @Update
    void updateUserTask(Usuario user);

    @Delete
    void deleteUserTask(Usuario user);
}