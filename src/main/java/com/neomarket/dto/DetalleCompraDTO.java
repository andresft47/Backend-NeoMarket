package com.neomarket.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class DetalleCompraDTO {
    private Long productoId;
    private Integer cantidad;
}