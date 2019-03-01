package com.dsige.sapia.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class EstadoTicket {

    @PrimaryKey(autoGenerate = true)
    private int estadoId;
    private String descripcion;
    private int estado;

    public EstadoTicket() {
    }

    @Ignore
    public EstadoTicket(int estadoId, String descripcion, int estado) {
        this.estadoId = estadoId;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
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