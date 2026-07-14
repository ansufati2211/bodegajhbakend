package com.bodegajh.bodegabackend.services;

import com.bodegajh.bodegabackend.dto.VentaRequest;
import com.bodegajh.bodegabackend.models.Credito;
import com.bodegajh.bodegabackend.models.DetalleVenta;
import com.bodegajh.bodegabackend.models.TurnoCaja;
import com.bodegajh.bodegabackend.models.Usuario;
import com.bodegajh.bodegabackend.models.Venta;
import com.bodegajh.bodegabackend.repositories.CreditoRepository;
import com.bodegajh.bodegabackend.repositories.DetalleVentaRepository;
import com.bodegajh.bodegabackend.repositories.TurnoCajaRepository;
import com.bodegajh.bodegabackend.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private DetalleVentaRepository detalleRepository;
    @Autowired private CreditoRepository creditoRepository;
    @Autowired private TurnoCajaRepository turnoCajaRepository;

    @Transactional
    public void procesarNuevaVenta(VentaRequest request) {

        // Validaciones iniciales para ventas a crédito
        if (Boolean.TRUE.equals(request.getEsCredito())) {
            if (request.getIdCliente() == null) {
                throw new IllegalArgumentException("Una venta a crédito necesita un cliente asociado.");
            }
            if (request.getDiasPlazo() == null || request.getDiasPlazo() <= 0) {
                throw new IllegalArgumentException("Una venta a crédito necesita días de plazo válidos.");
            }
        }

        // Obtenemos el usuario autenticado
        Integer idUsuarioActual = obtenerIdUsuarioAutenticado();

        // Validar y obtener el turno abierto del usuario actual
        TurnoCaja turnoActivo = turnoCajaRepository.findByIdUsuarioAndEstado(idUsuarioActual, "ABIERTA")
                .orElseThrow(() -> new IllegalStateException("No puedes registrar una venta sin tener un turno de caja abierto."));

        // 1. Guardar cabecera de la venta
        Venta nuevaVenta = new Venta();
        nuevaVenta.setIdUsuario(idUsuarioActual);
        nuevaVenta.setIdCliente(request.getIdCliente());
        nuevaVenta.setTurnoCaja(turnoActivo); // Vinculamos el turno activo a la venta
        nuevaVenta.setTipoComprobante(request.getTipoComprobante());
        nuevaVenta.setNumeroComprobante(generarNumeroComprobante());
        nuevaVenta.setTotal(BigDecimal.ZERO);
        nuevaVenta.setGananciaNeta(BigDecimal.ZERO);
        nuevaVenta.setEsCredito(Boolean.TRUE.equals(request.getEsCredito()));
        nuevaVenta.setPagoEfectivo(request.getPagoEfectivo() != null ? request.getPagoEfectivo() : BigDecimal.ZERO);
        nuevaVenta.setPagoYape(request.getPagoYape() != null ? request.getPagoYape() : BigDecimal.ZERO);
        nuevaVenta.setPagoPlin(request.getPagoPlin() != null ? request.getPagoPlin() : BigDecimal.ZERO);
        nuevaVenta.setPagoTarjeta(request.getPagoTarjeta() != null ? request.getPagoTarjeta() : BigDecimal.ZERO);
        
        Venta ventaGuardada = ventaRepository.save(nuevaVenta);

        // 2. Guardar detalles y calcular el total en memoria para evitar problemas de caché con Hibernate
        BigDecimal totalCalculadoVenta = BigDecimal.ZERO;
        
        for (VentaRequest.DetalleRequest det : request.getDetalles()) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdVenta(ventaGuardada.getIdVenta());
            detalle.setIdProducto(det.getIdProducto());
            detalle.setCantidad(det.getCantidad());
            detalle.setPrecioUnitario(det.getPrecioUnitario());
            detalleRepository.save(detalle);
            
            // Sumamos el subtotal al acumulador en memoria
            BigDecimal subtotalDetalle = det.getPrecioUnitario().multiply(BigDecimal.valueOf(det.getCantidad()));
            totalCalculadoVenta = totalCalculadoVenta.add(subtotalDetalle);
        }

        // 3. Si es venta a crédito, creamos el registro en creditos.
        if (Boolean.TRUE.equals(request.getEsCredito())) {
            Credito credito = new Credito();
            credito.setIdVenta(ventaGuardada.getIdVenta());
            credito.setIdCliente(request.getIdCliente());
            
            // Utilizamos el total que acabamos de calcular, no el del objeto cacheado inicial
            credito.setMontoDeuda(totalCalculadoVenta);
            credito.setSaldoPendiente(totalCalculadoVenta);
            credito.setDiasPlazo(request.getDiasPlazo());
            credito.setFechaVencimiento(LocalDate.now().plusDays(request.getDiasPlazo()));
            
            creditoRepository.save(credito);
        }
    }

    /**
     * Se toma del usuario autenticado que JwtAuthenticationFilter
     * ya dejó en el SecurityContext.
     */
    private Integer obtenerIdUsuarioAutenticado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Usuario usuario) {
            return usuario.getIdUsuario();
        }
        throw new IllegalStateException("No se pudo determinar el usuario autenticado para registrar la venta.");
    }

    /**
     * Correlativo simple basado en el siguiente id_venta.
     */
    private String generarNumeroComprobante() {
        long siguienteId = ventaRepository.count() + 1;
        return String.format("T-%05d", siguienteId);
    }
}