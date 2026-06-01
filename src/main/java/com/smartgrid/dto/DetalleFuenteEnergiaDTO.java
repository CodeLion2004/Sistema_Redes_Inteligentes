package com.smartgrid.dto;

public class DetalleFuenteEnergiaDTO {

    private String fuente;
    private int cantidadRegistros;
    private double consumoTotal;
    private double consumoPromedio;
    private double porcentajeConsumo;

    public DetalleFuenteEnergiaDTO() {
    }

    public DetalleFuenteEnergiaDTO(String fuente,
                                   int cantidadRegistros,
                                   double consumoTotal,
                                   double consumoPromedio,
                                   double porcentajeConsumo) {
        this.fuente            = fuente;
        this.cantidadRegistros = cantidadRegistros;
        this.consumoTotal      = consumoTotal;
        this.consumoPromedio   = consumoPromedio;
        this.porcentajeConsumo = porcentajeConsumo;
    }

    public String getFuente()             { return fuente; }
    public int getCantidadRegistros()     { return cantidadRegistros; }
    public double getConsumoTotal()       { return consumoTotal; }
    public double getConsumoPromedio()    { return consumoPromedio; }
    public double getPorcentajeConsumo()  { return porcentajeConsumo; }
}