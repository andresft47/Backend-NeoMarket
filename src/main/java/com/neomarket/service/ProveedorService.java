package com.neomarket.service;

import com.neomarket.model.Proveedor;
import com.neomarket.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {
    @Autowired private ProveedorRepository repo;

    public List<Proveedor> findAll() { return repo.findAll(); }
    public List<Proveedor> findActivos() { return repo.findByActivoTrue(); }
    public Proveedor findById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado: " + id));
    }
    public Proveedor save(Proveedor p) { return repo.save(p); }
    public void delete(Long id) { repo.deleteById(id); }
}