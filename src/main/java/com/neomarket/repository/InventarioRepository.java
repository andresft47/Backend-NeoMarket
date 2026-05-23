package com.neomarket.repository;

import com.neomarket.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByProductoId(Long productoId);

    // Productos con stock total <= stockMinimo (alertas)
    @Query("SELECT i FROM Inventario i WHERE (i.cantidadEstanteria + i.cantidadBodega) <= i.stockMinimo")
    List<Inventario> findInventariosBajoStockMinimo();
}