package com.neomarket.controller;

import com.neomarket.model.PedidoProveedor;
import com.neomarket.repository.PedidoProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos-proveedor")
public class PedidoProveedorController {

    @Autowired
    private PedidoProveedorRepository repo;

    /** GET /api/pedidos-proveedor → todos los pedidos desc */
    @GetMapping
    public List<PedidoProveedor> getAll() {
        return repo.findAllByOrderByFechaDesc();
    }

    /** GET /api/pedidos-proveedor/producto/{id} → historial de un producto */
    @GetMapping("/producto/{productoId}")
    public List<PedidoProveedor> getByProducto(@PathVariable Long productoId) {
        return repo.findByProductoIdOrderByFechaDesc(productoId);
    }
}