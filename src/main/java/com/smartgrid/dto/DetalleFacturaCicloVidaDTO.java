package com.smartgrid.dto;

import java.util.List;

public class DetalleFacturaCicloVidaDTO {

    private String id;
    private String nombreUsuario;
    private String zona;
    private double total;
    private String estado;
    private String fechaEmision;
    private List<String> transicionesPermitidas;
    private List<String> transicionesBloqueadas;

    public DetalleFacturaCicloVidaDTO() {}

    public DetalleFacturaCicloVidaDTO(String id,
                                      String nombreUsuario,
                                      String zona,
                                      double total,
                                      String estado,
                                      String fechaEmision,
                                      List<String> transicionesPermitidas,
                                      List<String> transicionesBloqueadas) {
        this.id                    = id;
        this.nombreUsuario         = nombreUsuario;
        this.zona                  = zona;
        this.total                 = total;
        this.estado                = estado;
        this.fechaEmision          = fechaEmision;
        this.transicionesPermitidas  = transicionesPermitidas;
        this.transicionesBloqueadas  = transicionesBloqueadas;
    }

    public String getId()                              { return id; }
    public String getNombreUsuario()                   { return nombreUsuario; }
    public String getZona()                            { return zona; }
    public double getTotal()                           { return total; }
    public String getEstado()                          { return estado; }
    public String getFechaEmision()                    { return fechaEmision; }
    public List<String> getTransicionesPermitidas()    { return transicionesPermitidas; }
    public List<String> getTransicionesBloqueadas()    { return transicionesBloqueadas; }
}