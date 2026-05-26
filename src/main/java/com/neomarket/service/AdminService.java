package com.neomarket.service;

import com.neomarket.dto.AdminLoginDTO;
import com.neomarket.model.Administrador;
import com.neomarket.repository.AdministradorRepository;
import com.neomarket.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    @Autowired private AdministradorRepository repo;
    @Autowired private PasswordHasher passwordHasher;

    public Administrador login(AdminLoginDTO dto) {
        String email = dto.getEmail().trim().toLowerCase();
        Administrador admin = repo.findByEmail(email)
            .filter(Administrador::getActivo)
            .orElseThrow(() -> new IllegalArgumentException("Correo o contraseña incorrectos"));

        if (!passwordHasher.matches(dto.getPassword(), admin.getPassword())) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }
        return admin;
    }

    public List<Administrador> findAll() { return repo.findAll(); }

    public Administrador findById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Administrador no encontrado: " + id));
    }

    public Administrador crear(Administrador admin) {
        admin.setEmail(admin.getEmail().trim().toLowerCase());
        admin.setPassword(passwordHasher.encode(admin.getPassword()));
        return repo.save(admin);
    }

    public void desactivar(Long id) {
        Administrador a = findById(id);
        a.setActivo(false);
        repo.save(a);
    }
}