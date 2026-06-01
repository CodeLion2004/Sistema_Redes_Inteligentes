package com.smartgrid.patterns.templatemethod;

/**
 * Template Method para estandarizar la generación de reportes.
 *
 * @param <R> tipo de respuesta final del reporte
 * @param <D> tipo de datos base consultados
 * @param <M> tipo de métricas procesadas
 */
public abstract class ReporteTemplate<R, D, M> {

    public final R generarReporte() {
        D datos = obtenerDatos();
        M metricas = procesarDatos(datos);
        return construirRespuesta(metricas);
    }

    protected abstract D obtenerDatos();

    protected abstract M procesarDatos(D datos);

    protected abstract R construirRespuesta(M metricas);
}