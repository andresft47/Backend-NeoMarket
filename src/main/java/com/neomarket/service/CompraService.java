package com.neomarket.service;

import com.neomarket.dto.CompraRequestDTO;
import com.neomarket.dto.DetalleCompraDTO;
import com.neomarket.model.*;
import com.neomarket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CompraService {
    @Autowired private CompraRepository compraRepo;
    @Autowired private DetalleCompraRepository detalleRepo;
    @Autowired private ClienteService clienteService;
    @Autowired private ProductoService productoService;
    @Autowired private InventarioService inventarioService;

    public List<Compra> findAll() { return compraRepo.findAll(); }

    public Compra findById(Long id) {
        return compraRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Compra no encontrada: " + id));
    }

    public List<Compra> findByCliente(Long clienteId) {
        return compraRepo.findByClienteId(clienteId);
    }

    public List<Compra> findByRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return compraRepo.findByFechaBetween(inicio, fin);
    }

    @Transactional
    public Compra registrarCompra(CompraRequestDTO dto) {
        Cliente cliente = clienteService.findById(dto.getClienteId());

        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setFecha(LocalDateTime.now());
        compra.setMetodoPago(dto.getMetodoPago());
        compra.setCajero(dto.getCajero());
        compra.setTotal(0.0);
        Compra compraGuardada = compraRepo.save(compra);

        double total = 0.0;
        List<DetalleCompra> detalles = new ArrayList<>();

        for (DetalleCompraDTO d : dto.getDetalles()) {
            Producto producto = productoService.findById(d.getProductoId());
            double subtotal = producto.getPrecio() * d.getCantidad();

            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compraGuardada);
            detalle.setProducto(producto);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);
            detalles.add(detalleRepo.save(detalle));

            // Descontar del inventario
            inventarioService.restarStock(producto.getId(), d.getCantidad());
            total += subtotal;
        }

        compraGuardada.setTotal(total);
        compraGuardada.setDetalles(detalles);
        return compraRepo.save(compraGuardada);
    }

    public Map<String, Object> getResumenHoy() {
        LocalDateTime inicio = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        Double total = compraRepo.sumTotalByFechaBetween(inicio, fin);
        Long cantidad = compraRepo.countByFechaBetween(inicio, fin);
        Map<String, Object> resumen = new HashMap<>();
        resumen.put("totalVentas", total != null ? total : 0.0);
        resumen.put("cantidadCompras", cantidad != null ? cantidad : 0L);
        resumen.put("fecha", LocalDateTime.now().toLocalDate().toString());
        return resumen;
    }
}