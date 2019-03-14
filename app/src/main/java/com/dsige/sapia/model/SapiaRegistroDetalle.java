package com.dsige.sapia.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class SapiaRegistroDetalle {


    @PrimaryKey(autoGenerate = true)
    private int registroDetalleId;
    private String urlPhoto;
    private String descripcion;
    private int estado;

    public SapiaRegistroDetalle() {
    }

    @Ignore
    public SapiaRegistroDetalle(int registroDetalleId, String urlPhoto, String descripcion, int estado) {
        this.registroDetalleId = registroDetalleId;
        this.urlPhoto = urlPhoto;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getRegistroDetalleId() {
        return registroDetalleId;
    }

    public void setRegistroDetalleId(int registroDetalleId) {
        this.registroDetalleId = registroDetalleId;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
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