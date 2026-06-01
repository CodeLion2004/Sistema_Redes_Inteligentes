package com.smartgrid.patterns.bridge;

public class AlertaConsumoAlto extends AlertaEnergetica {
	
	private final String idDispositivo;
    private final double consumo;
    
    public AlertaConsumoAlto(CanalNotificacion canalNotificacion, String idDispositivo, double consumo) {
        super(canalNotificacion);
        this.idDispositivo = idDispositivo;
        this.consumo = consumo;
    }
    
    @Override
    public String generarMensaje() {
        return "Consumo alto detectado en el dispositivo " + idDispositivo + ": " + consumo + " kWh";
    }

}
