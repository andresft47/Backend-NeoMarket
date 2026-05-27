package com.neomarket.repository;

import com.neomarket.model.PedidoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoProveedorRepository
        extends JpaRepository<PedidoProveedor, Long> {

    List<PedidoProveedor> findByProductoIdOrderByFechaDesc(Long productoId);
    List<PedidoProveedor> findAllByOrderByFechaDesc();
}