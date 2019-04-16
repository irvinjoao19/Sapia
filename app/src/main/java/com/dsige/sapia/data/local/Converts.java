package com.dsige.sapia.data.local;

import com.dsige.sapia.data.local.model.Personal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class Converts {

    @TypeConverter
    public String personals(List<Personal> p) {
        if (p == null) {
            return (null);
        }
        Type type = new TypeToken<List<Personal>>() {
        }.getType();
        return new Gson().toJson(p, type);
    }

    @TypeConverter
    public List<Personal> personals(String p) {
        if (p == null) {
            return (null);
        }
        Type type = new TypeToken<List<Personal>>() {
        }.getType();

        return new Gson().fromJson(p, type);
    }
}