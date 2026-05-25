package com.neomarket.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidadEstanteria() {
        return cantidadEstanteria;
    }

    public void setCantidadEstanteria(Integer cantidadEstanteria) {
        this.cantidadEstanteria = cantidadEstanteria;
    }

    public Integer getCantidadBodega() {
        return cantidadBodega;
    }

    public void setCantidadBodega(Integer cantidadBodega) {
        this.cantidadBodega = cantidadBodega;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Integer getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(Integer stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    // Stock total calculado
    public Integer getStockTotal() {
        return cantidadEstanteria + cantidadBodega;
    }

    // Indica si el stock está bajo el mínimo
    public Boolean isStockBajo() {
        return getStockTotal() <= stockMinimo;
    }
}
