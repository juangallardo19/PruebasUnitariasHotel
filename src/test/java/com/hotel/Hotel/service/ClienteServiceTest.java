package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.mapper.ClienteMapper;
import com.hotel.Hotel.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void crearClienteLoGuardaEnElRepositorio() {
        CrearClienteRequest request = new CrearClienteRequest("Ana Ruiz", "ana@correo.com");
        ClienteResponse respuesta = new ClienteResponse(UUID.randomUUID(), "Ana Ruiz", "ana@correo.com", true, 0);
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
        when(clienteMapper.toResponse(any(Cliente.class))).thenReturn(respuesta);

        ClienteResponse resultado = clienteService.crear(request);

        assertEquals("Ana Ruiz", resultado.nombre());
        verify(clienteRepository).save(any(Cliente.class));
    }
}
