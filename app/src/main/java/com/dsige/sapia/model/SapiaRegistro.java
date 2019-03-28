package com.dsige.sapia.model;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class SapiaRegistro {

    @PrimaryKey(autoGenerate = true)
    private int registroId;
    private String nombre;
    private int estado;
    @Ignore
    private List<SapiaRegistroDetalle> sapiaRegistroDetalles;

    public SapiaRegistro() {
    }

    @Ignore
    public SapiaRegistro(int registroId, String nombre, int estado) {
        this.registroId = registroId;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getRegistroId() {
        return registroId;
    }

    public void setRegistroId(int registroId) {
        this.registroId = registroId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<SapiaRegistroDetalle> getSapiaRegistroDetalles() {
        return sapiaRegistroDetalles;
    }

    public void setSapiaRegistroDetalles(List<SapiaRegistroDetalle> sapiaRegistroDetalles) {
        this.sapiaRegistroDetalles = sapiaRegistroDetalles;
    }
}