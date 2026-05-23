package com.neomarket.service;

import com.neomarket.model.Producto;
import com.neomarket.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired private ProductoRepository repo;

    public List<Producto> findAll() { return repo.findAll(); }
    public List<Producto> findActivos() { return repo.findByActivoTrue(); }

    public Producto findById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    public Optional<Producto> findByCodigoBarras(String codigo) {
        return repo.findByCodigoBarras(codigo);
    }

    public Producto save(Producto p) { return repo.save(p); }

    public Producto update(Long id, Producto datos) {
        Producto p = findById(id);
        p.setNombre(datos.getNombre());
        p.setDescripcion(datos.getDescripcion());
        p.setPrecio(datos.getPrecio());
        p.setCategoria(datos.getCategoria());
        p.setProveedor(datos.getProveedor());
        return repo.save(p);
    }

    public void delete(Long id) {
        Producto p = findById(id);
        p.setActivo(false); // Baja lógica
        repo.save(p);
    }
}