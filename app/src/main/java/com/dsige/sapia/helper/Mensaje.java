package com.dsige.sapia.helper;


public class Mensaje {

    private String mensaje;
    private String codigo;
    private int header;
    private int body;

    public Mensaje() {
    }

    public Mensaje(String mensaje, int header) {
        this.mensaje = mensaje;
        this.header = header;
    }

    public Mensaje(String mensaje, String codigo, int header, int body) {
        this.mensaje = mensaje;
        this.codigo = codigo;
        this.header = header;
        this.body = body;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }
}
