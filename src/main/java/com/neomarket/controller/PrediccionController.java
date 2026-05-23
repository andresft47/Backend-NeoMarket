package com.neomarket.controller;

import com.neomarket.dto.PedidoSugeridoDTO;
import com.neomarket.dto.ProductoPrediccionDTO;
import com.neomarket.service.MotorRecomendacion;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/predicciones")
@CrossOrigin(origins = "*")
@Tag(name = "Motor IA - Predicciones",
     description = "Motor de recomendacion y prediccion de compras futuras")
public class PrediccionController {
    @Autowired private MotorRecomendacion motor;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ProductoPrediccionDTO>> predecirCliente(
            @PathVariable Long clienteId) {
        return ResponseEntity.ok(motor.predecirComprasFuturas(clienteId));
    }

    @GetMapping("/pedidos-sugeridos")
    public ResponseEntity<List<PedidoSugeridoDTO>> pedidosSugeridos() {
        return ResponseEntity.ok(motor.sugerirPedidosProveedor());
    }

    @GetMapping("/mas-vendidos")
    public ResponseEntity<List<Map<String, Object>>> masVendidos(
            @RequestParam(defaultValue = "10") int top) {
        return ResponseEntity.ok(motor.getProductosMasVendidos(top));
    }
}