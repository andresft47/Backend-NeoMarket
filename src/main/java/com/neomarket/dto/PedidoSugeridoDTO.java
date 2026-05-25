package com.neomarket.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class PedidoSugeridoDTO {

    private Long productoId;
    private String nombreProducto;
    private String proveedor;
    private Integer stockActual;
    private Integer stockMinimo;
    private Integer cantidadSugerida;
    private Double costoEstimado;
    private String motivo; // STOCK_BAJO, DEMANDA_ALTA

    public PedidoSugeridoDTO(Long productoId, String nombreProducto, String proveedor, Integer stockActual, Integer stockMinimo, Integer cantidadSugerida, Double costoEstimado, String motivo) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.proveedor = proveedor;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.cantidadSugerida = cantidadSugerida;
        this.costoEstimado = costoEstimado;
        this.motivo = motivo;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Integer getCantidadSugerida() {
        return cantidadSugerida;
    }

    public void setCantidadSugerida(Integer cantidadSugerida) {
        this.cantidadSugerida = cantidadSugerida;
    }

    public Double getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(Double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
