package com.bodegajh.bodegabackend.dto;

import java.math.BigDecimal;
import java.util.List;

public class VentaRequest {

    private String documentoCliente;
    private String tipoComprobante;
    private BigDecimal total;
    private Boolean enviarSunat;
    private List<DetalleRequest> detalles;
    private BigDecimal pagoEfectivo;
    private BigDecimal pagoYape;
    private BigDecimal pagoPlin;
    private BigDecimal pagoTarjeta;

    // 👇 NUEVO: necesarios para conectar la venta con el flujo de fiados
    private Integer idCliente;
    private Boolean esCredito;
    private Integer diasPlazo; // solo se usa si esCredito = true

    // --- GETTERS Y SETTERS MANUALES PARA VentaRequest ---

    public String getDocumentoCliente() { return documentoCliente; }
    public void setDocumentoCliente(String documentoCliente) { this.documentoCliente = documentoCliente; }

    public String getTipoComprobante() { return tipoComprobante; }
    public void setTipoComprobante(String tipoComprobante) { this.tipoComprobante = tipoComprobante; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Boolean getEnviarSunat() { return enviarSunat; }
    public void setEnviarSunat(Boolean enviarSunat) { this.enviarSunat = enviarSunat; }

    public List<DetalleRequest> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleRequest> detalles) { this.detalles = detalles; }
    public BigDecimal getPagoEfectivo() { return pagoEfectivo; }
    public void setPagoEfectivo(BigDecimal pagoEfectivo) { this.pagoEfectivo = pagoEfectivo; }

    public BigDecimal getPagoYape() { return pagoYape; }
    public void setPagoYape(BigDecimal pagoYape) { this.pagoYape = pagoYape; }

    public BigDecimal getPagoPlin() { return pagoPlin; }
    public void setPagoPlin(BigDecimal pagoPlin) { this.pagoPlin = pagoPlin; }

    public BigDecimal getPagoTarjeta() { return pagoTarjeta; }
    public void setPagoTarjeta(BigDecimal pagoTarjeta) { this.pagoTarjeta = pagoTarjeta; }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public Boolean getEsCredito() { return esCredito; }
    public void setEsCredito(Boolean esCredito) { this.esCredito = esCredito; }

    public Integer getDiasPlazo() { return diasPlazo; }
    public void setDiasPlazo(Integer diasPlazo) { this.diasPlazo = diasPlazo; }

    // --- SUB-CLASE DetalleRequest ---
    public static class DetalleRequest {
        private Integer idProducto;
        private Integer cantidad;
        private BigDecimal precioUnitario;

        public Integer getIdProducto() { return idProducto; }
        public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

        public BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    }
}