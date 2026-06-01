package com.smartgrid.dto;

import java.util.List;
import java.util.Map;

public class ReporteCicloVidaFacturaDTO {

    private int totalFacturas;
    private double montoTotal;
    private double montoRecuperable;   // facturas VENCIDA que aún pueden pagarse
    private double montoBloqueado;     // facturas ANULADA — irrecuperables
    private String estadoDominante;
    private Map<String, Integer> conteoporEstado;
    private Map<String, Double>  montoPorEstado;
    private List<DetalleFacturaCicloVidaDTO> facturas;
    private List<String> recomendaciones;

    public ReporteCicloVidaFacturaDTO() {}

    public ReporteCicloVidaFacturaDTO(int totalFacturas,
                                      double montoTotal,
                                      double montoRecuperable,
                                      double montoBloqueado,
                                      String estadoDominante,
                                      Map<String, Integer> conteoPorEstado,
                                      Map<String, Double> montoPorEstado,
                                      List<DetalleFacturaCicloVidaDTO> facturas,
                                      List<String> recomendaciones) {
        this.totalFacturas    = totalFacturas;
        this.montoTotal       = montoTotal;
        this.montoRecuperable = montoRecuperable;
        this.montoBloqueado   = montoBloqueado;
        this.estadoDominante  = estadoDominante;
        this.conteoporEstado  = conteoPorEstado;
        this.montoPorEstado   = montoPorEstado;
        this.facturas         = facturas;
        this.recomendaciones  = recomendaciones;
    }

    public int getTotalFacturas()                           { return totalFacturas; }
    public double getMontoTotal()                           { return montoTotal; }
    public double getMontoRecuperable()                     { return montoRecuperable; }
    public double getMontoBloqueado()                       { return montoBloqueado; }
    public String getEstadoDominante()                      { return estadoDominante; }
    public Map<String, Integer> getConteoPorEstado()        { return conteoporEstado; }
    public Map<String, Double>  getMontoPorEstado()         { return montoPorEstado; }
    public List<DetalleFacturaCicloVidaDTO> getFacturas()   { return facturas; }
    public List<String> getRecomendaciones()                { return recomendaciones; }
}