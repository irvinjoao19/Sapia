package com.dsige.sapia.context.room;

import com.dsige.sapia.model.Cliente;
import com.dsige.sapia.model.EstadoTicket;
import com.dsige.sapia.model.Personal;
import com.dsige.sapia.model.StatusTicket;
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

    @TypeConverter
    public String clientes(List<Cliente> c) {
        if (c == null) {
            return (null);
        }
        Type type = new TypeToken<List<Cliente>>() {
        }.getType();
        return new Gson().toJson(c, type);
    }

    @TypeConverter
    public List<Cliente> clientes(String p) {
        if (p == null) {
            return (null);
        }
        Type type = new TypeToken<List<Cliente>>() {
        }.getType();

        return new Gson().fromJson(p, type);
    }

    @TypeConverter
    public String estadoTickets(List<EstadoTicket> e) {
        if (e == null) {
            return (null);
        }
        Type type = new TypeToken<List<EstadoTicket>>() {
        }.getType();
        return new Gson().toJson(e, type);
    }


    @TypeConverter
    public List<EstadoTicket> estadoTickets(String e) {
        if (e == null) {
            return (null);
        }
        Type type = new TypeToken<List<EstadoTicket>>() {
        }.getType();

        return new Gson().fromJson(e, type);
    }

    @TypeConverter
    public String statusTickets(List<StatusTicket> s) {
        if (s == null) {
            return (null);
        }
        Type type = new TypeToken<List<StatusTicket>>() {
        }.getType();
        return new Gson().toJson(s, type);
    }

    @TypeConverter
    public static List<StatusTicket> statusTickets(String s) {
        Type type = new TypeToken<List<StatusTicket>>() {
        }.getType();

        return new Gson().fromJson(s, type);
    }
}
