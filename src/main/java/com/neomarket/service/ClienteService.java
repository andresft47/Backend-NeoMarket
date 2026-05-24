package com.neomarket.service;

import com.neomarket.dto.ClienteLoginDTO;
import com.neomarket.dto.ClienteRegistroDTO;
import com.neomarket.model.Cliente;
import com.neomarket.repository.ClienteRepository;
import com.neomarket.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {
    @Autowired private ClienteRepository repo;
    @Autowired private PasswordHasher passwordHasher;

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

    public Cliente registrar(ClienteRegistroDTO dto) {
        String email = dto.getEmail().trim().toLowerCase();

        if (repo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        Cliente c = new Cliente();
        c.setNombre(dto.getNombre().trim());
        c.setApellido(dto.getApellido().trim());
        c.setEmail(email);
        c.setTelefono(dto.getTelefono() != null ? dto.getTelefono().trim() : null);
        c.setPassword(passwordHasher.encode(dto.getPassword()));
        c.setFechaRegistro(LocalDate.now());
        c.setActivo(true);

        return repo.save(c);
    }

    public Cliente login(ClienteLoginDTO dto) {
        String email = dto.getEmail().trim().toLowerCase();

        Cliente c = repo.findByEmail(email)
            .filter(Cliente::getActivo)
            .orElseThrow(() -> new IllegalArgumentException("Correo o contraseña incorrectos"));

        if (!passwordHasher.matches(dto.getPassword(), c.getPassword())) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }

        return c;
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
