package com.neomarket.controller;

import com.neomarket.model.Categoria;
import com.neomarket.service.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
@Tag(name = "Categorias", description = "Gestión de categorías de productos")
public class CategoriaController {
    @Autowired private CategoriaService service;

    @GetMapping
    public List<Categoria> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody Categoria c) {
        return ResponseEntity.ok(service.save(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria c) {
        c.setId(id);
        return ResponseEntity.ok(service.save(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}