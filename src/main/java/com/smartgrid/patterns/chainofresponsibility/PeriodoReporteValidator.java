package com.smartgrid.patterns.chainofresponsibility;

import java.util.Locale;
import java.util.Set;

public class PeriodoReporteValidator extends ReporteValidator {

    private static final Set<String> PERIODOS_PERMITIDOS = Set.of(
            "dia", "hoy", "semana", "7dias", "ultimos7dias",
            "mes", "30dias", "ultimos30dias", "anio", "año",
            "365dias", "todo", "historico", "histórico"
    );

    @Override
    protected void validarActual(ReporteRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud del reporte es obligatoria.");
        }

        String periodo = request.getPeriodo();

        if (periodo == null || periodo.isBlank()) {
            return;
        }

        String periodoNormalizado = periodo.toLowerCase(Locale.ROOT).trim();

        if (!PERIODOS_PERMITIDOS.contains(periodoNormalizado)) {
            throw new IllegalArgumentException("Periodo no permitido para el reporte: " + periodo);
        }
    }
}