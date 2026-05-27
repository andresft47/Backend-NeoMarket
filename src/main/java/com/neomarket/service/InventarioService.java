package com.neomarket.service;

import com.neomarket.model.*;
import com.neomarket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventarioService {

    // ── Constantes ───────────────────────────────────────────────────────────
    private static final double ESTANTERIA_MAX_PCT   = 0.20; // 20% del stockMaximo
    private static final double BODEGA_MIN_PCT        = 0.05; //  5% del stockMaximo
    private static final int    UMBRAL_RECARGA_ESTANT = 10;   // Regla 1
    private static final double DESCUENTO_PROVEEDOR   = 0.85; // 15% más barato
    private static final double PCT_ALTA              = 1.00;
    private static final double PCT_MEDIA             = 0.60;
    private static final double PCT_BAJA              = 0.35;
    private static final int    DEMANDA_ALTA_MIN      = 50;
    private static final int    DEMANDA_MEDIA_MIN     = 20;

    @Autowired private InventarioRepository      repo;
    @Autowired private PedidoProveedorRepository pedidoRepo;
    @Autowired private DetalleCompraRepository   detalleRepo;

    // ── API pública ──────────────────────────────────────────────────────────

    public List<Inventario> findAll() { return repo.findAll(); }

    public Inventario findByProducto(Long productoId) {
        return repo.findByProductoId(productoId)
            .orElseThrow(() -> new RuntimeException(
                "Inventario no encontrado para producto: " + productoId));
    }

    public List<Inventario> getAlertas() {
        return repo.findInventariosBajoStockMinimo();
    }

    public Inventario actualizarStock(Long productoId,
                                      Integer estanteria, Integer bodega) {
        Inventario inv = findByProducto(productoId);
        if (estanteria != null) inv.setCantidadEstanteria(estanteria);
        if (bodega     != null) inv.setCantidadBodega(bodega);
        return repo.save(inv);
    }

    @Transactional
    public Inventario restarStock(Long productoId, Integer cantidad) {
        Inventario inv = findByProducto(productoId);

        if (inv.getStockTotal() < cantidad) {
            throw new RuntimeException(
                "Stock insuficiente para producto ID " + productoId
                + ". Disponible: " + inv.getStockTotal()
                + ", solicitado: " + cantidad);
        }

        // Paso 1 – descontar (estantería → bodega)
        int restante = cantidad;
        if (inv.getCantidadEstanteria() >= restante) {
            inv.setCantidadEstanteria(inv.getCantidadEstanteria() - restante);
        } else {
            restante -= inv.getCantidadEstanteria();
            inv.setCantidadEstanteria(0);
            inv.setCantidadBodega(inv.getCantidadBodega() - restante);
        }
        inv = repo.save(inv);

        // Paso 2 – Regla 1: recargar estantería desde bodega
        inv = recargarEstanteria(inv);

        // Paso 3 – Regla 2/3/4: pedido automático si bodega bajo mínimo
        gestionarPedidoAutomatico(inv);

        return repo.save(inv);
    }

    public Inventario save(Inventario inv) { return repo.save(inv); }

    // ── Lógica interna ───────────────────────────────────────────────────────

    /**
     * REGLA 1: Si estantería < 10 → transferir desde bodega hasta
     * cubrir el máximo de estantería (20% del stockMaximo).
     */
    private Inventario recargarEstanteria(Inventario inv) {
        int estanteriaMax = (int) Math.ceil(inv.getStockMaximo() * ESTANTERIA_MAX_PCT);

        if (inv.getCantidadEstanteria() < UMBRAL_RECARGA_ESTANT
                && inv.getCantidadBodega() > 0) {

            int necesario   = estanteriaMax - inv.getCantidadEstanteria();
            int aTransferir = Math.min(necesario, inv.getCantidadBodega());

            inv.setCantidadEstanteria(inv.getCantidadEstanteria() + aTransferir);
            inv.setCantidadBodega(inv.getCantidadBodega() - aTransferir);
            inv = repo.save(inv); // evidenciar movimiento en BD
        }
        return inv;
    }

    /**
     * REGLAS 2, 3 y 4:
     * Si bodega < bodegaMin → clasificar demanda → calcular cantidad →
     * registrar pedido en BD (REALIZADO) → acreditar en bodega.
     */
    private void gestionarPedidoAutomatico(Inventario inv) {
        int bodegaMin = (int) Math.ceil(inv.getStockMaximo() * BODEGA_MIN_PCT);
        if (inv.getCantidadBodega() >= bodegaMin) return;

        Producto  producto  = inv.getProducto();
        Proveedor proveedor = producto.getProveedor();
        if (proveedor == null) return;

        String nivelDemanda = clasificarDemanda(producto.getId());
        double pct          = pctSegunDemanda(nivelDemanda);
        int    objetivo     = (int) Math.ceil(inv.getStockMaximo() * pct);
        int    aPedir       = Math.max(0, objetivo - inv.getStockTotal());
        if (aPedir <= 0) return;

        double precioUnit  = redondear2(producto.getPrecio() * DESCUENTO_PROVEEDOR);
        double totalPedido = redondear2(precioUnit * aPedir);

        // Registrar pedido en BD con estado REALIZADO (aprobación automática)
        PedidoProveedor pedido = new PedidoProveedor();
        pedido.setProducto(producto);
        pedido.setProveedor(proveedor);
        pedido.setCantidadSolicitada(aPedir);
        pedido.setPrecioUnitarioProveedor(precioUnit);
        pedido.setTotalPedido(totalPedido);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("REALIZADO");
        pedido.setNivelDemanda(nivelDemanda);
        pedido.setMotivo("Reposición automática [demanda " + nivelDemanda
            + "]. Bodega (" + inv.getCantidadBodega()
            + ") < mínimo (" + bodegaMin + ")."
            + " Objetivo: " + objetivo + " uds ("
            + (int)(pct * 100) + "% del máx).");
        pedidoRepo.save(pedido);

        // Acreditar reposición en bodega (pedido aprobado automáticamente)
        inv.setCantidadBodega(inv.getCantidadBodega() + aPedir);
        repo.save(inv);
    }

    private String clasificarDemanda(Long productoId) {
        int totalVendido = detalleRepo.findByProductoId(productoId)
            .stream().mapToInt(DetalleCompra::getCantidad).sum();

        if (totalVendido > DEMANDA_ALTA_MIN)   return "ALTA";
        if (totalVendido >= DEMANDA_MEDIA_MIN) return "MEDIA";
        return "BAJA";
    }

    private double pctSegunDemanda(String nivel) {
        return switch (nivel) {
            case "ALTA"  -> PCT_ALTA;
            case "MEDIA" -> PCT_MEDIA;
            default      -> PCT_BAJA;
        };
    }

    private double redondear2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}