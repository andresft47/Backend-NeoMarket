package com.neomarket.service;

import com.neomarket.model.Inventario;
import com.neomarket.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventarioService {
    @Autowired private InventarioRepository repo;

    public List<Inventario> findAll() { return repo.findAll(); }

    public Inventario findByProducto(Long productoId) {
        return repo.findByProductoId(productoId)
            .orElseThrow(() -> new RuntimeException("Inventario no encontrado para producto: " + productoId));
    }

    public List<Inventario> getAlertas() {
        return repo.findInventariosBajoStockMinimo();
    }

    public Inventario actualizarStock(Long productoId, Integer estanteria, Integer bodega) {
        Inventario inv = findByProducto(productoId);
        if (estanteria != null) inv.setCantidadEstanteria(estanteria);
        if (bodega != null) inv.setCantidadBodega(bodega);
        return repo.save(inv);
    }

    public Inventario restarStock(Long productoId, Integer cantidad) {
        Inventario inv = findByProducto(productoId);
        int total = inv.getStockTotal();
        if (total < cantidad)
            throw new RuntimeException("Stock insuficiente para producto: " + productoId);

        // Restar de estantería primero, luego de bodega
        int restante = cantidad;
        if (inv.getCantidadEstanteria() >= restante) {
            inv.setCantidadEstanteria(inv.getCantidadEstanteria() - restante);
        } else {
            restante -= inv.getCantidadEstanteria();
            inv.setCantidadEstanteria(0);
            inv.setCantidadBodega(inv.getCantidadBodega() - restante);
        }
        return repo.save(inv);
    }

    public Inventario save(Inventario inv) { return repo.save(inv); }
}