package com.neomarket.controller;

import com.neomarket.model.Proveedor;
import com.neomarket.service.ProveedorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
@Tag(name = "Proveedores", description = "Gestión de proveedores")
public class ProveedorController {
    @Autowired private ProveedorService service;

    @GetMapping
    public List<Proveedor> getAll() { return service.findAll(); }

    @GetMapping("/activos")
    public List<Proveedor> getActivos() { return service.findActivos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Proveedor> create(@RequestBody Proveedor p) {
        return ResponseEntity.ok(service.save(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> update(@PathVariable Long id, @RequestBody Proveedor p) {
        p.setId(id);
        return ResponseEntity.ok(service.save(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}