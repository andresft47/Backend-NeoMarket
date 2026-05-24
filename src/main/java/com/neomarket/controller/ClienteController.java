package com.neomarket.controller;

import com.neomarket.dto.ClienteLoginDTO;
import com.neomarket.dto.ClienteRegistroDTO;
import com.neomarket.model.Cliente;
import com.neomarket.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
@Tag(name = "Clientes", description = "Registro y gestión de clientes")
public class ClienteController {
    @Autowired private ClienteService service;

    @GetMapping
    public List<Cliente> getAll() { return service.findActivos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/buscar")
    public List<Cliente> buscar(@RequestParam String q) {
        return service.buscar(q);
    }

    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@Valid @RequestBody ClienteLoginDTO dto) {
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody ClienteRegistroDTO dto) {
        return ResponseEntity.ok(service.registrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente c) {
        return ResponseEntity.ok(service.update(id, c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
