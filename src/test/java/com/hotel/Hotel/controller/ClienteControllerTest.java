package com.hotel.Hotel.controller;

import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void buscarPorIdDevuelveElCliente() {
        UUID id = UUID.randomUUID();
        ClienteResponse cliente = new ClienteResponse(id, "Ana Ruiz", "ana@correo.com", true, 0);
        when(clienteService.obtenerPorId(id)).thenReturn(cliente);

        ResponseEntity<ClienteResponse> respuesta = clienteController.buscarPorId(id);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Ana Ruiz", respuesta.getBody().nombre());
        verify(clienteService).obtenerPorId(id);
    }

    @Test
    void listarDevuelveTodosLosClientes() {
        ClienteResponse ana = new ClienteResponse(UUID.randomUUID(), "Ana Ruiz", "ana@correo.com", true, 0);
        ClienteResponse luis = new ClienteResponse(UUID.randomUUID(), "Luis Pérez", "luis@correo.com", true, 0);
        when(clienteService.listarTodos()).thenReturn(List.of(ana, luis));

        ResponseEntity<List<ClienteResponse>> respuesta = clienteController.listar();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, respuesta.getBody().size());
        verify(clienteService).listarTodos();
    }
}
