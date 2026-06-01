package com.smartgrid.dto;

public class MedidorExternoDTO {

	private String device_id;
    private double power_kw;
    private String source;
    private String timestamp;

    public MedidorExternoDTO() {
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public double getPower_kw() {
        return power_kw;
    }

    public void setPower_kw(double power_kw) {
        this.power_kw = power_kw;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
	
}
