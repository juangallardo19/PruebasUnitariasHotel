package com.hotel.Hotel.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RangoFechasTest {

    @Test
    void calculaLosDiasEntreLasFechas() {
        RangoFechas rango = new RangoFechas(
                LocalDateTime.of(2026, 11, 1, 15, 0),
                LocalDateTime.of(2026, 11, 4, 15, 0));

        assertEquals(3, rango.getDias());
    }

    @Test
    void fechaFinAnteriorAInicioLanzaExcepcion() {
        LocalDateTime inicio = LocalDateTime.of(2026, 11, 4, 15, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 11, 1, 15, 0);

        assertThrows(IllegalArgumentException.class, () -> new RangoFechas(inicio, fin));
    }
}
