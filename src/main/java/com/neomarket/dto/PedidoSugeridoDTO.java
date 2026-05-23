package com.neomarket.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class PedidoSugeridoDTO {
    private Long productoId;
    private String nombreProducto;
    private String proveedor;
    private Integer stockActual;
    private Integer stockMinimo;
    private Integer cantidadSugerida;
    private Double costoEstimado;
    private String motivo; // STOCK_BAJO, DEMANDA_ALTA
}