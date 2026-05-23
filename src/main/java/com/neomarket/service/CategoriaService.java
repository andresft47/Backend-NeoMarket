package com.neomarket.service;

import com.neomarket.model.Categoria;
import com.neomarket.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {
    @Autowired private CategoriaRepository repo;

    public List<Categoria> findAll() { return repo.findAll(); }
    public Categoria findById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada: " + id));
    }
    public Categoria save(Categoria c) { return repo.save(c); }
    public void delete(Long id) { repo.deleteById(id); }
}