package com.neomarket.service;

import com.neomarket.dto.PedidoSugeridoDTO;
import com.neomarket.dto.ProductoPrediccionDTO;
import com.neomarket.model.*;
import com.neomarket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MotorRecomendacion {

    @Autowired private DetalleCompraRepository detalleRepo;
    @Autowired private InventarioRepository inventarioRepo;
    @Autowired private ProductoRepository productoRepo;

    /**
     * Analiza el historial de un cliente y predice sus próximas compras.
     * Usa análisis de frecuencia: cuántas veces y cuánto compró cada producto.
     */
    public List<ProductoPrediccionDTO> predecirComprasFuturas(Long clienteId) {
        List<DetalleCompra> historial =
            detalleRepo.findByClienteIdOrderByFechaDesc(clienteId);

        if (historial.isEmpty()) return Collections.emptyList();

        // Agrupar por producto y calcular métricas
        Map<Producto, List<DetalleCompra>> porProducto = historial.stream()
            .collect(Collectors.groupingBy(DetalleCompra::getProducto));

        long totalCompras = historial.stream()
            .map(d -> d.getCompra().getId())
            .distinct().count();

        List<ProductoPrediccionDTO> predicciones = new ArrayList<>();

        for (Map.Entry<Producto, List<DetalleCompra>> entry : porProducto.entrySet()) {
            Producto producto = entry.getKey();
            List<DetalleCompra> detalles = entry.getValue();

            long vecesComprado = detalles.stream()
                .map(d -> d.getCompra().getId())
                .distinct().count();

            double promedioUnidades = detalles.stream()
                .mapToInt(DetalleCompra::getCantidad)
                .average()
                .orElse(0.0);

            // Probabilidad = frecuencia relativa del producto en las compras del cliente
            double probabilidad = (double) vecesComprado / totalCompras;

            String nivel;
            if (probabilidad >= 0.6) nivel = "ALTA";
            else if (probabilidad >= 0.3) nivel = "MEDIA";
            else nivel = "BAJA";

            String descripcion = String.format(
                "Comprado en %d de %d visitas. Promedio %.1f unidades por compra.",
                vecesComprado, totalCompras, promedioUnidades);

            predicciones.add(new ProductoPrediccionDTO(
                producto.getId(),
                producto.getNombre(),
                vecesComprado,
                promedioUnidades,
                Math.round(probabilidad * 100.0) / 100.0,
                nivel,
                descripcion
            ));
        }

        // Ordenar por probabilidad descendente
        predicciones.sort(Comparator.comparingDouble(
            ProductoPrediccionDTO::getProbabilidadCompra).reversed());

        return predicciones;
    }

    /**
     * Analiza el inventario vs la demanda histórica para sugerir pedidos.
     * Cruza stock bajo con productos de alta demanda.
     */
    public List<PedidoSugeridoDTO> sugerirPedidosProveedor() {
        List<Inventario> inventarios = inventarioRepo.findAll();
        List<Object[]> masVendidos = detalleRepo.findProductosMasVendidos();

        // Mapa productoId → unidades totales vendidas
        Map<Long, Long> ventas = new HashMap<>();
        for (Object[] row : masVendidos) {
            Long productoId = (Long) row[0];
            Long totalVendido = (Long) row[3]; // COUNT de compras
            ventas.put(productoId, totalVendido);
        }

        List<PedidoSugeridoDTO> pedidos = new ArrayList<>();

        for (Inventario inv : inventarios) {
            boolean stockBajo = inv.isStockBajo();
            long demandalHistorica = ventas.getOrDefault(inv.getProducto().getId(), 0L);
            boolean altaDemanda = demandalHistorica >= 3;

            if (stockBajo || altaDemanda) {
                int cantidadSugerida = inv.getStockMaximo() - inv.getStockTotal();
                if (cantidadSugerida <= 0) continue;

                double costoEstimado = cantidadSugerida * inv.getProducto().getPrecio() * 0.6;

                String motivo = stockBajo && altaDemanda
                    ? "STOCK_BAJO + DEMANDA_ALTA"
                    : stockBajo ? "STOCK_BAJO" : "DEMANDA_ALTA";

                pedidos.add(new PedidoSugeridoDTO(
                    inv.getProducto().getId(),
                    inv.getProducto().getNombre(),
                    inv.getProducto().getProveedor() != null
                        ? inv.getProducto().getProveedor().getNombre() : "Sin proveedor",
                    inv.getStockTotal(),
                    inv.getStockMinimo(),
                    cantidadSugerida,
                    Math.round(costoEstimado * 100.0) / 100.0,
                    motivo
                ));
            }
        }

        pedidos.sort(Comparator.comparingInt(PedidoSugeridoDTO::getStockActual));
        return pedidos;
    }

    /**
     * Resumen de productos más vendidos en toda la tienda (para el panel admin).
     */
    public List<Map<String, Object>> getProductosMasVendidos(int top) {
        List<Object[]> resultados = detalleRepo.findProductosMasVendidos();
        List<Map<String, Object>> lista = new ArrayList<>();

        int limite = Math.min(top, resultados.size());
        for (int i = 0; i < limite; i++) {
            Object[] row = resultados.get(i);
            Map<String, Object> item = new HashMap<>();
            item.put("productoId", row[0]);
            item.put("nombre", row[1]);
            item.put("totalUnidades", row[2]);
            item.put("vecesComprado", row[3]);
            lista.add(item);
        }
        return lista;
    }
}