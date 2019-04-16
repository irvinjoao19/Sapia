package com.dsige.sapia.data.local;

import com.dsige.sapia.data.local.dao.PersonalDao;
import com.dsige.sapia.data.local.model.Personal;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Personal.class},
        version = 1,
        exportSchema = false)
@TypeConverters({Converts.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract PersonalDao personalDao();

    public static volatile AppDataBase INSTANCE;

    public static final String DB_NAME = "sapia_db";


    /**
     *
     *
     *   private static volatile AppDataBase INSTANCE;
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
     */
}