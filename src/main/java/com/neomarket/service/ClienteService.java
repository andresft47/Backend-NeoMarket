package com.neomarket.service;

import com.neomarket.model.Cliente;
import com.neomarket.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {
    @Autowired private ClienteRepository repo;

    public List<Cliente> findAll() { return repo.findAll(); }
    public List<Cliente> findActivos() { return repo.findByActivoTrue(); }

    public Cliente findById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
    }

    public List<Cliente> buscar(String termino) {
        return repo.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            termino, termino);
    }

    public Cliente save(Cliente c) {
        if (c.getFechaRegistro() == null) c.setFechaRegistro(LocalDate.now());
        return repo.save(c);
    }

    public Cliente update(Long id, Cliente datos) {
        Cliente c = findById(id);
        c.setNombre(datos.getNombre());
        c.setApellido(datos.getApellido());
        c.setEmail(datos.getEmail());
        c.setTelefono(datos.getTelefono());
        return repo.save(c);
    }

    public void delete(Long id) {
        Cliente c = findById(id);
        c.setActivo(false);
        repo.save(c);
    }
}