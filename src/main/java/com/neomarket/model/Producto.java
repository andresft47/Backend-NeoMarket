package com.neomarket.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "producto")
@Data @NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(name = "codigo_barras", unique = true)
    private String codigoBarras;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @Column(nullable = false)
    private Boolean activo = true;
}