package com.neomarket.repository;

import com.neomarket.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
    List<DetalleCompra> findByCompraId(Long compraId);
    List<DetalleCompra> findByCompraClienteId(Long clienteId);

    // Historial de un cliente ordenado por fecha desc
    @Query("SELECT d FROM DetalleCompra d WHERE d.compra.cliente.id = :clienteId " +
           "ORDER BY d.compra.fecha DESC")
    List<DetalleCompra> findByClienteIdOrderByFechaDesc(@Param("clienteId") Long clienteId);

    // Cuántas veces se compró cada producto (para IA)
    @Query("SELECT d.producto.id, d.producto.nombre, SUM(d.cantidad), COUNT(d.compra.id) " +
           "FROM DetalleCompra d GROUP BY d.producto.id, d.producto.nombre " +
           "ORDER BY COUNT(d.compra.id) DESC")
    List<Object[]> findProductosMasVendidos();

    // Historial de un producto específico
    @Query("SELECT d FROM DetalleCompra d WHERE d.producto.id = :productoId " +
           "ORDER BY d.compra.fecha DESC")
    List<DetalleCompra> findByProductoId(@Param("productoId") Long productoId);
}