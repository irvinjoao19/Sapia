package com.dsige.sapia.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class StatusTicket {

    @PrimaryKey(autoGenerate = true)
    private int statusId;
    private String descripcion;
    private int estado;

    public StatusTicket() {
    }

    @Ignore
    public StatusTicket(int statusId, String descripcion, int estado) {
        this.statusId = statusId;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}