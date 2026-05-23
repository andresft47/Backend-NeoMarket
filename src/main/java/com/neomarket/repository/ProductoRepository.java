package com.neomarket.repository;

import com.neomarket.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
    Optional<Producto> findByCodigoBarras(String codigoBarras);
    List<Producto> findByCategoriaId(Long categoriaId);
}