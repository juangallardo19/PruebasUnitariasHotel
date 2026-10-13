package com.hotel.Hotel.dto.response;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void deErrorCreaRespuestaSinDetalles() {
        ErrorResponse error = ErrorResponse.deError(404, "Not Found", "Cliente no encontrado", "/api/clientes/1");

        assertEquals(404, error.codigoHttp());
        assertEquals("Cliente no encontrado", error.mensaje());
        assertNull(error.detallesValidacion());
    }

    @Test
    void deValidacionGuardaLosErroresPorCampo() {
        Map<String, String> errores = Map.of("email", "El correo es obligatorio");

        ErrorResponse error = ErrorResponse.deValidacion(400, "Bad Request", "Error de validacion", "/api/clientes", errores);

        assertEquals(400, error.codigoHttp());
        assertEquals("El correo es obligatorio", error.detallesValidacion().get("email"));
    }
}
