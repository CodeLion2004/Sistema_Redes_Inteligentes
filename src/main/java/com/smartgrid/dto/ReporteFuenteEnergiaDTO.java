package com.smartgrid.dto;

import java.util.List;
import java.util.Map;

public class ReporteFuenteEnergiaDTO {

    private int totalRegistros;
    private double consumoTotal;
    private double consumoPromedio;
    private String fuenteDominante;
    private List<DetalleFuenteEnergiaDTO> fuentes;
    private Map<String, Double> consumoPorFuente;
    private List<String> recomendaciones;

    public ReporteFuenteEnergiaDTO() {
    }

    public ReporteFuenteEnergiaDTO(int totalRegistros,
                                   double consumoTotal,
                                   double consumoPromedio,
                                   String fuenteDominante,
                                   List<DetalleFuenteEnergiaDTO> fuentes,
                                   Map<String, Double> consumoPorFuente,
                                   List<String> recomendaciones) {
        this.totalRegistros    = totalRegistros;
        this.consumoTotal      = consumoTotal;
        this.consumoPromedio   = consumoPromedio;
        this.fuenteDominante   = fuenteDominante;
        this.fuentes           = fuentes;
        this.consumoPorFuente  = consumoPorFuente;
        this.recomendaciones   = recomendaciones;
    }

    public int getTotalRegistros()                   { return totalRegistros; }
    public double getConsumoTotal()                  { return consumoTotal; }
    public double getConsumoPromedio()               { return consumoPromedio; }
    public String getFuenteDominante()               { return fuenteDominante; }
    public List<DetalleFuenteEnergiaDTO> getFuentes(){ return fuentes; }
    public Map<String, Double> getConsumoPorFuente() { return consumoPorFuente; }
    public List<String> getRecomendaciones()         { return recomendaciones; }
}