package com.neomarket.service;

import com.neomarket.model.Compra;
import com.neomarket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AdminDashboardService {

    @Autowired private ClienteRepository   clienteRepo;
    @Autowired private CompraRepository    compraRepo;
    @Autowired private InventarioRepository inventarioRepo;
    @Autowired private ProductoRepository  productoRepo;

    /**
     * Resumen general para el panel principal del administrador.
     * Devuelve: clientesActivos, comprasHoy, ventasHoy,
     *           comprasMes, ventasMes, productosStockBajo, totalSKUsActivos
     */
    public Map<String, Object> getEstadoTienda() {
        Map<String, Object> estado = new LinkedHashMap<>();

        // Clientes activos
        estado.put("clientesActivos", clienteRepo.findByActivoTrue().size());

        // Límites temporales
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia    = inicioDia.plusDays(1);
        LocalDateTime inicioMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        // Compras hoy
        List<Compra> comprasHoy = compraRepo.findByFechaBetween(inicioDia, finDia);
        double ventasHoy = comprasHoy.stream().mapToDouble(Compra::getTotal).sum();
        estado.put("comprasHoy", comprasHoy.size());
        estado.put("ventasHoy",  Math.round(ventasHoy * 100.0) / 100.0);

        // Compras este mes
        List<Compra> comprasMes = compraRepo.findByFechaBetween(inicioMes, finDia);
        double ventasMes = comprasMes.stream().mapToDouble(Compra::getTotal).sum();
        estado.put("comprasMes", comprasMes.size());
        estado.put("ventasMes",  Math.round(ventasMes * 100.0) / 100.0);

        // Inventario
        long stockBajo = inventarioRepo.findAll().stream()
            .filter(inv -> inv.isStockBajo()).count();
        long totalSKUs = productoRepo.findAll().stream()
            .filter(p -> p.getActivo()).count();
        estado.put("productosStockBajo", stockBajo);
        estado.put("totalSKUsActivos",   totalSKUs);

        return estado;
    }
}