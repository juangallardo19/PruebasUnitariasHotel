package com.hotel.Hotel.exception;

import com.hotel.Hotel.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    void recursoNoEncontradoDevuelve404() {
        when(request.getRequestURI()).thenReturn("/api/clientes/1");
        RecursoNoEncontradoException excepcion = new RecursoNoEncontradoException("Cliente no encontrado");

        ResponseEntity<ErrorResponse> respuesta = handler.manejarRecursoNoEncontrado(excepcion, request);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertEquals("Cliente no encontrado", respuesta.getBody().mensaje());
        assertEquals("/api/clientes/1", respuesta.getBody().ruta());
    }

    @Test
    void reglaDeNegocioDevuelve400() {
        when(request.getRequestURI()).thenReturn("/api/reservas");
        ReglaNegocioException excepcion = new ReglaNegocioException("Cliente inactivo");

        ResponseEntity<ErrorResponse> respuesta = handler.manejarReglaNegocio(excepcion, request);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("Cliente inactivo", respuesta.getBody().mensaje());
    }
}
