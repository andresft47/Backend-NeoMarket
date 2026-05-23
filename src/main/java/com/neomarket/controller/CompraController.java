package com.neomarket.controller;

import com.neomarket.dto.CompraRequestDTO;
import com.neomarket.model.Compra;
import com.neomarket.service.CompraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
@Tag(name = "Compras", description = "Registro de compras desde caja y consulta de historial")
public class CompraController {
    @Autowired private CompraService service;

    @GetMapping
    public List<Compra> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Compra> getByCliente(@PathVariable Long clienteId) {
        return service.findByCliente(clienteId);
    }

    @GetMapping("/rango")
    public List<Compra> getByRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return service.findByRangoFechas(inicio, fin);
    }

    @GetMapping("/resumen/hoy")
    public Map<String, Object> getResumenHoy() {
        return service.getResumenHoy();
    }

    @PostMapping
    public ResponseEntity<Compra> registrar(@RequestBody CompraRequestDTO dto) {
        return ResponseEntity.ok(service.registrarCompra(dto));
    }
}