package com.neomarket.dto;

import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class CompraRequestDTO {
    private Long clienteId;
    private String metodoPago;
    private String cajero;
    private List<DetalleCompraDTO> detalles;
}