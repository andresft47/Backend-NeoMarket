package com.neomarket.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido_proveedor")
public class PedidoProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(name = "cantidad_solicitada", nullable = false)
    private Integer cantidadSolicitada;

    /** precio_cliente × 0.85 */
    @Column(name = "precio_unitario_proveedor", nullable = false)
    private Double precioUnitarioProveedor;

    /** cantidadSolicitada × precioUnitarioProveedor */
    @Column(name = "total_pedido", nullable = false)
    private Double totalPedido;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String estado = "REALIZADO";

    @Column(name = "nivel_demanda", nullable = false)
    private String nivelDemanda; // ALTA | MEDIA | BAJA

    @Column(length = 500)
    private String motivo;

    public PedidoProveedor() {}

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    public Integer getCantidadSolicitada() { return cantidadSolicitada; }
    public void setCantidadSolicitada(Integer v) { this.cantidadSolicitada = v; }
    public Double getPrecioUnitarioProveedor() { return precioUnitarioProveedor; }
    public void setPrecioUnitarioProveedor(Double v) { this.precioUnitarioProveedor = v; }
    public Double getTotalPedido() { return totalPedido; }
    public void setTotalPedido(Double v) { this.totalPedido = v; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNivelDemanda() { return nivelDemanda; }
    public void setNivelDemanda(String nivelDemanda) { this.nivelDemanda = nivelDemanda; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}