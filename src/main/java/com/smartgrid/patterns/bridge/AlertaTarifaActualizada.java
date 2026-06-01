package com.smartgrid.patterns.bridge;

public class AlertaTarifaActualizada extends AlertaEnergetica{
	
	private final String tipoUsuario;
    private final double nuevaTarifa;
    
    
    
	public AlertaTarifaActualizada(CanalNotificacion canalNotificacion, String tipoUsuario, double nuevaTarifa) {
		super(canalNotificacion);
		this.tipoUsuario = tipoUsuario;
		this.nuevaTarifa = nuevaTarifa;
	}
    
    
	@Override
    public String generarMensaje() {
        return "La tarifa para " + tipoUsuario + " fue actualizada a " + nuevaTarifa;
    }
    

}
