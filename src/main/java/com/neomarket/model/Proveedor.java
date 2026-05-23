package com.neomarket.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "proveedor")
@Data @NoArgsConstructor @AllArgsConstructor
public class Proveedor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;

    @Column(nullable = false)
    private Boolean activo = true;
}