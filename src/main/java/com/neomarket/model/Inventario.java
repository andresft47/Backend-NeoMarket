package com.neomarket.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "inventario")
@Data @NoArgsConstructor @AllArgsConstructor
public class Inventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false, unique = true)
    private Producto producto;

    @Column(name = "cantidad_estanteria", nullable = false)
    private Integer cantidadEstanteria = 0;

    @Column(name = "cantidad_bodega", nullable = false)
    private Integer cantidadBodega = 0;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo = 10;

    @Column(name = "stock_maximo", nullable = false)
    private Integer stockMaximo = 100;

    // Stock total calculado
    public Integer getStockTotal() {
        return cantidadEstanteria + cantidadBodega;
    }

    // Indica si el stock está bajo el mínimo
    public Boolean isStockBajo() {
        return getStockTotal() <= stockMinimo;
    }
}