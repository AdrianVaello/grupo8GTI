package com.example.navigationdrawtest;

public class Bicicletas   {

    private String base;
    private String IdBici;
    private boolean disponible;

    public Bicicletas(String b, String I, boolean d) {
        this.base = b;
        this.IdBici = I;
        this.disponible = d;

    }
    public Bicicletas(){}
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getIdBici() {
        return IdBici;
    }

    public void setIdBici(String idBici) {
        IdBici = idBici;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
