package com.neomarket.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class ProductoPrediccionDTO {

    private Long productoId;
    private String nombreProducto;
    private Long vecesComprado;
    private Double promedioUnidadesPorCompra;
    private Double probabilidadCompra; // 0.0 a 1.0
    private String nivelPrioridad;     // ALTA, MEDIA, BAJA
    private String descripcion;

    public ProductoPrediccionDTO(Long productoId, String nombreProducto, Long vecesComprado, Double promedioUnidadesPorCompra, Double probabilidadCompra, String nivelPrioridad, String descripcion) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.vecesComprado = vecesComprado;
        this.promedioUnidadesPorCompra = promedioUnidadesPorCompra;
        this.probabilidadCompra = probabilidadCompra;
        this.nivelPrioridad = nivelPrioridad;
        this.descripcion = descripcion;
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

    public Long getVecesComprado() {
        return vecesComprado;
    }

    public void setVecesComprado(Long vecesComprado) {
        this.vecesComprado = vecesComprado;
    }

    public Double getPromedioUnidadesPorCompra() {
        return promedioUnidadesPorCompra;
    }

    public void setPromedioUnidadesPorCompra(Double promedioUnidadesPorCompra) {
        this.promedioUnidadesPorCompra = promedioUnidadesPorCompra;
    }

    public Double getProbabilidadCompra() {
        return probabilidadCompra;
    }

    public void setProbabilidadCompra(Double probabilidadCompra) {
        this.probabilidadCompra = probabilidadCompra;
    }

    public String getNivelPrioridad() {
        return nivelPrioridad;
    }

    public void setNivelPrioridad(String nivelPrioridad) {
        this.nivelPrioridad = nivelPrioridad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
