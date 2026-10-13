package com.hotel.Hotel.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void crearClienteQuedaActivoSinPenalizaciones() {
        Cliente cliente = new Cliente("Ana Ruiz", "ana@correo.com");

        assertTrue(cliente.isActivo());
        assertEquals(0, cliente.getPenalizaciones());
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
