package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.*;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.request.CrearReservaRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.exception.ReglaNegocioException;
import com.hotel.Hotel.mapper.ClienteMapper;
import com.hotel.Hotel.mapper.ReservaMapper;
import com.hotel.Hotel.repository.ClienteRepository;
import com.hotel.Hotel.repository.HabitacionRepository;
import com.hotel.Hotel.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PruebasBasicasMockTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ClienteService clienteService;

    @InjectMocks
    private ReservaService reservaService;

    private final LocalDateTime inicio = LocalDateTime.of(2026, 11, 1, 15, 0);
    private final LocalDateTime fin = LocalDateTime.of(2026, 11, 4, 15, 0);

    private Reserva nuevaReserva() {
        Cliente cliente = new Cliente("Ana Ruiz", "ana@correo.com");
        Habitacion habitacion = new HabitacionEstandar("101", 2, 100.0, 1);
        return new Reserva(cliente, habitacion, new RangoFechas(inicio, fin));
    }

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

    @Test
    void crearReservaLaGuardaEnElRepositorio() {
        Cliente cliente = new Cliente("Ana Ruiz", "ana@correo.com");
        Habitacion habitacion = new HabitacionEstandar("101", 2, 100.0, 1);
        CrearReservaRequest request = new CrearReservaRequest(cliente.getId(), habitacion.getId(), inicio, fin);
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(habitacionRepository.findById(habitacion.getId())).thenReturn(Optional.of(habitacion));

        reservaService.crear(request);

        verify(reservaRepository).save(any(Reserva.class));
    }

    @Test
    void confirmarReservaCambiaElEstadoYGuarda() {
        Reserva reserva = nuevaReserva();
        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));

        reservaService.confirmar(reserva.getId());

        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        verify(reservaRepository).save(reserva);
    }

    @Test
    void cancelarReservaCambiaElEstadoYGuarda() {
        Reserva reserva = nuevaReserva();
        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));

        reservaService.cancelar(reserva.getId());

        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
        verify(reservaRepository).save(reserva);
    }

    @Test
    void clienteInactivoNoPuedeCrearReserva() {
        Cliente cliente = new Cliente("Ana Ruiz", "ana@correo.com");
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        Habitacion habitacion = new HabitacionEstandar("101", 2, 100.0, 1);
        CrearReservaRequest request = new CrearReservaRequest(cliente.getId(), habitacion.getId(), inicio, fin);
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(habitacionRepository.findById(habitacion.getId())).thenReturn(Optional.of(habitacion));

        assertThrows(ReglaNegocioException.class, () -> reservaService.crear(request));
        verify(reservaRepository, never()).save(any());
    }
}
