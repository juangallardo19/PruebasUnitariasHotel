package com.hotel.Hotel.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HabitacionTest {

    @Test
    void asignarAReservaDejaLaHabitacionOcupada() {
        Habitacion habitacion = new HabitacionEstandar("101", 2, 100.0, 1);

        habitacion.asignarAReserva();

        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado());
    }

    @Test
    void asignarHabitacionOcupadaLanzaExcepcion() {
        Habitacion habitacion = new HabitacionEstandar("101", 2, 100.0, 1);
        habitacion.asignarAReserva();

        assertThrows(IllegalStateException.class, habitacion::asignarAReserva);
    }
}
