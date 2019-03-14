package com.dsige.sapia.context.room;

import android.content.Context;

import com.dsige.sapia.context.dao.ConfigurationDao;
import com.dsige.sapia.context.dao.UserDao;
import com.dsige.sapia.model.Cliente;
import com.dsige.sapia.model.EstadoTicket;
import com.dsige.sapia.model.Migracion;
import com.dsige.sapia.model.Personal;
import com.dsige.sapia.model.StatusTicket;
import com.dsige.sapia.model.Usuario;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {
        Usuario.class,
        Migracion.class,
        Cliente.class,
        Personal.class,
        EstadoTicket.class,
        StatusTicket.class},
        version = 2,
        exportSchema = false)
@TypeConverters({Converts.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract ConfigurationDao migrationDao();

    private static volatile AppDataBase INSTANCE;

    public static AppDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "sapia_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}