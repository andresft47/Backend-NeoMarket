package com.neomarket.controller;

import com.neomarket.dto.AdminLoginDTO;
import com.neomarket.dto.PedidoSugeridoDTO;
import com.neomarket.dto.ProductoPrediccionDTO;
import com.neomarket.model.Administrador;
import com.neomarket.model.Cliente;
import com.neomarket.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@Tag(name = "Administrador", description = "Panel de administración NeoMarket")
public class AdminController {

    @Autowired private AdminService          adminService;
    @Autowired private AdminDashboardService dashboardService;
    @Autowired private ClienteService        clienteService;
    @Autowired private CompraService         compraService;
    @Autowired private InventarioService     inventarioService;
    @Autowired private MotorRecomendacion    motorRecomendacion;

    // ── AUTH ────────────────────────────────────────────────────────────────
    /** POST /api/admin/login */
    @PostMapping("/login")
    public ResponseEntity<Administrador> login(@Valid @RequestBody AdminLoginDTO dto) {
        return ResponseEntity.ok(adminService.login(dto));
    }

    // ── DASHBOARD ────────────────────────────────────────────────────────────
    /** GET /api/admin/estado-tienda */
    @GetMapping("/estado-tienda")
    public ResponseEntity<Map<String, Object>> estadoTienda() {
        return ResponseEntity.ok(dashboardService.getEstadoTienda());
    }

    // ── CLIENTES ─────────────────────────────────────────────────────────────
    /** GET /api/admin/clientes → todos los clientes (activos e inactivos) */
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> clientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    // ── COMPRAS ──────────────────────────────────────────────────────────────
    /** GET /api/admin/compras → historial completo */
    @GetMapping("/compras")
    public ResponseEntity<?> compras() {
        return ResponseEntity.ok(compraService.findAll());
    }

    /** GET /api/admin/compras/resumen-hoy */
    @GetMapping("/compras/resumen-hoy")
    public ResponseEntity<Map<String, Object>> resumenHoy() {
        return ResponseEntity.ok(compraService.getResumenHoy());
    }

    // ── INVENTARIO ───────────────────────────────────────────────────────────
    /** GET /api/admin/inventario → bodega + estantes de todos los productos */
    @GetMapping("/inventario")
    public ResponseEntity<?> inventario() {
        return ResponseEntity.ok(inventarioService.findAll());
    }

    /** GET /api/admin/inventario/stock-bajo */
    @GetMapping("/inventario/stock-bajo")
    public ResponseEntity<?> stockBajo() {
        return ResponseEntity.ok(inventarioService.getAlertas());
    }

    // ── PREDICCIONES / IA ────────────────────────────────────────────────────
    /** GET /api/admin/predicciones/cliente/{clienteId} */
    @GetMapping("/predicciones/cliente/{clienteId}")
    public ResponseEntity<List<ProductoPrediccionDTO>> prediccionCliente(
            @PathVariable Long clienteId) {
        return ResponseEntity.ok(motorRecomendacion.predecirComprasFuturas(clienteId));
    }

    /** GET /api/admin/predicciones/pedidos-sugeridos */
    @GetMapping("/predicciones/pedidos-sugeridos")
    public ResponseEntity<List<PedidoSugeridoDTO>> pedidosSugeridos() {
        return ResponseEntity.ok(motorRecomendacion.sugerirPedidosProveedor());
    }

    /** GET /api/admin/predicciones/mas-vendidos?top=10 */
    @GetMapping("/predicciones/mas-vendidos")
    public ResponseEntity<List<Map<String, Object>>> masVendidos(
            @RequestParam(defaultValue = "10") int top) {
        return ResponseEntity.ok(motorRecomendacion.getProductosMasVendidos(top));
    }

    // ── CRUD ADMINISTRADORES ─────────────────────────────────────────────────
    @GetMapping
    public List<Administrador> getAll() { return adminService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> getById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Administrador> crear(@RequestBody Administrador admin) {
        return ResponseEntity.ok(adminService.crear(admin));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        adminService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}