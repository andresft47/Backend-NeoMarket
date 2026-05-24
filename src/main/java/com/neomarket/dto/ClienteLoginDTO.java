package com.neomarket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClienteLoginDTO {

    @NotBlank(message = "El correo es requerido")
    @Email(message = "Correo inválido")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
