package com.dsige.sapia.data.module;

import android.app.Application;

import com.dsige.sapia.data.local.AppDataBase;
import com.dsige.sapia.data.local.dao.PersonalDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @Provides
    @Singleton
    AppDataBase provideRoomDatabase(Application application) {
        if (AppDataBase.INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (AppDataBase.INSTANCE == null) {
                    AppDataBase.INSTANCE = Room.databaseBuilder(application.getApplicationContext(),
                            AppDataBase.class, AppDataBase.DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return AppDataBase.INSTANCE;
    }

    @Provides
    PersonalDao provideConfigurationDao(AppDataBase appDataBase) {
        return appDataBase.personalDao();
    }
}