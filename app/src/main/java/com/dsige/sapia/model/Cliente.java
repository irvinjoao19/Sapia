package com.dsige.sapia.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

@Entity
public class Cliente {

    @PrimaryKey(autoGenerate = true)
    private int clienteId;
    private String nombre;
    private String direccion;
    private String telefono;
    private String contacto;
    private String documento;
    private String descripcion;
    private int estado;

    public Cliente() {
    }

    @Ignore
    public Cliente(int clienteId, String nombre, String direccion, String telefono, String contacto, String documento, String descripcion, int estado) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.contacto = contacto;
        this.documento = documento;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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