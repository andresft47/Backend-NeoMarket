package com.neomarket.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductoPrediccionDTO {
    private Long productoId;
    private String nombreProducto;
    private Long vecesComprado;
    private Double promedioUnidadesPorCompra;
    private Double probabilidadCompra; // 0.0 a 1.0
    private String nivelPrioridad;     // ALTA, MEDIA, BAJA
    private String descripcion;
}