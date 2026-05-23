package com.neomarket.controller;

import com.neomarket.model.Inventario;
import com.neomarket.service.InventarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
@Tag(name = "Inventario", description = "Control de stock en estantería y bodega")
public class InventarioController {
    @Autowired private InventarioService service;

    @GetMapping
    public List<Inventario> getAll() { return service.findAll(); }

    @GetMapping("/alertas")
    public List<Inventario> getAlertas() { return service.getAlertas(); }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Inventario> getByProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(service.findByProducto(productoId));
    }

    @PutMapping("/producto/{productoId}")
    public ResponseEntity<Inventario> actualizarStock(
            @PathVariable Long productoId,
            @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(service.actualizarStock(
            productoId,
            body.get("estanteria"),
            body.get("bodega")
        ));
    }

    @PostMapping
    public ResponseEntity<Inventario> create(@RequestBody Inventario inv) {
        return ResponseEntity.ok(service.save(inv));
    }
}