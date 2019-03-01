package com.dsige.sapia.model;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Migracion {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private List<Personal> personals;
    private List<Cliente> clientes;
    private List<EstadoTicket> estadoTickets;
    private List<StatusTicket> statusTickets;
    private String mensaje;

    public Migracion() {
    }

    @Ignore
    public Migracion(int id, List<Personal> personals, List<Cliente> clientes, List<EstadoTicket> estadoTickets, List<StatusTicket> statusTickets, String mensaje) {
        this.id = id;
        this.personals = personals;
        this.clientes = clientes;
        this.estadoTickets = estadoTickets;
        this.statusTickets = statusTickets;
        this.mensaje = mensaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Personal> getPersonals() {
        return personals;
    }

    public void setPersonals(List<Personal> personals) {
        this.personals = personals;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<EstadoTicket> getEstadoTickets() {
        return estadoTickets;
    }

    public void setEstadoTickets(List<EstadoTicket> estadoTickets) {
        this.estadoTickets = estadoTickets;
    }

    public List<StatusTicket> getStatusTickets() {
        return statusTickets;
    }

    public void setStatusTickets(List<StatusTicket> statusTickets) {
        this.statusTickets = statusTickets;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}