package com.bodegajh.bodegabackend.services;

import com.bodegajh.bodegabackend.dto.VentaRequest;
import com.bodegajh.bodegabackend.models.DetalleVenta;
import com.bodegajh.bodegabackend.models.Venta;
import com.bodegajh.bodegabackend.repositories.DetalleVentaRepository;
import com.bodegajh.bodegabackend.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private DetalleVentaRepository detalleRepository;

    @Transactional
    public void procesarNuevaVenta(VentaRequest request) {
        // 1. Guardar cabecera
        Venta nuevaVenta = new Venta();
        nuevaVenta.setIdUsuario(1);
        nuevaVenta.setTipoComprobante(request.getTipoComprobante());
        nuevaVenta.setTotal(request.getTotal());
        nuevaVenta.setGananciaNeta(BigDecimal.ZERO);
        nuevaVenta.setEsCredito(false);
        Venta ventaGuardada = ventaRepository.save(nuevaVenta);

        // 2. Guardar detalles (El Trigger de la BD se encargará del stock y las ganancias)
        for (VentaRequest.DetalleRequest det : request.getDetalles()) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdVenta(ventaGuardada.getIdVenta());
            detalle.setIdProducto(det.getIdProducto());
            detalle.setCantidad(det.getCantidad());
            detalle.setPrecioUnitario(det.getPrecioUnitario());
            detalleRepository.save(detalle);
        }
    }
}