package com.hotel.Hotel.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PruebasBasicasTest {

    private Reserva nuevaReserva() {
        Cliente cliente = new Cliente("Ana Ruiz", "ana@correo.com");
        Habitacion habitacion = new HabitacionEstandar("101", 2, 100.0, 1);
        RangoFechas periodo = new RangoFechas(
                LocalDateTime.of(2026, 11, 1, 15, 0),
                LocalDateTime.of(2026, 11, 4, 15, 0));
        return new Reserva(cliente, habitacion, periodo);
    }

    @Test
    void crearClienteQuedaActivoSinPenalizaciones() {
        Cliente cliente = new Cliente("Ana Ruiz", "ana@correo.com");

        assertTrue(cliente.isActivo());
        assertEquals(0, cliente.getPenalizaciones());
    }

    @Test
    void crearReservaCalculaCostoYQuedaPendiente() {
        Reserva reserva = nuevaReserva();

        assertEquals(300.0, reserva.getCostoTotal());
        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
    }

    @Test
    void confirmarReservaOcupaLaHabitacion() {
        Reserva reserva = nuevaReserva();

        reserva.confirmar();

        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        assertEquals(EstadoHabitacion.OCUPADA, reserva.getHabitacion().getEstado());
    }

    @Test
    void cancelarReservaLiberaLaHabitacion() {
        Reserva reserva = nuevaReserva();
        reserva.confirmar();

        reserva.cancelar();

        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
        assertEquals(EstadoHabitacion.DISPONIBLE, reserva.getHabitacion().getEstado());
    }

    @Test
    void clienteConTresPenalizacionesQuedaInactivo() {
        Cliente cliente = new Cliente("Ana Ruiz", "ana@correo.com");

        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();

        assertFalse(cliente.isActivo());
        assertFalse(cliente.puedeRealizarReservas());
    }
}
