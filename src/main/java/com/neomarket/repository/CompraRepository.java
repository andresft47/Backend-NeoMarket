package com.neomarket.repository;

import com.neomarket.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByClienteId(Long clienteId);
    List<Compra> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT SUM(c.total) FROM Compra c WHERE c.fecha BETWEEN :inicio AND :fin")
    Double sumTotalByFechaBetween(@Param("inicio") LocalDateTime inicio,
                                  @Param("fin") LocalDateTime fin);

    @Query("SELECT COUNT(c) FROM Compra c WHERE c.fecha BETWEEN :inicio AND :fin")
    Long countByFechaBetween(@Param("inicio") LocalDateTime inicio,
                              @Param("fin") LocalDateTime fin);
}