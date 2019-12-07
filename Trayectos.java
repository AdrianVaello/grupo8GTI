package com.example.navigationdrawtest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trayectos implements Serializable {

    private String idBici;
    private String email;
    private boolean movimiento;
    private String base;
    private long tiempo;


    public Trayectos(String email, String idBici, boolean movimiento, String base, long tiempo) {
        this.email = email;
        this.movimiento = movimiento;
        this.base = base;
        this.idBici = idBici;
        this.tiempo = tiempo;
    }

    public Trayectos(){}
    //public static final List<Trayectos> TRAYECTOS = new ArrayList<Trayectos>();
    public  long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public  String getIdBici() {
        return idBici;
    }

    public void setIdBici(String idBici) {
        this.idBici = idBici;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMovimiento() {
        return movimiento;
    }

    public void setMovimiento(boolean movimiento) {
        this.movimiento = movimiento;
    }

    public String getBase() {
        return base;
    }

    @Override
    public String toString() {
        return "Trayectos{" +
                "idBici=" + idBici +
                ", email='" + email + '\'' +
                ", movimiento=" + movimiento +
                ", base='" + base + '\'' +
                '}';
    }
}
