package cl.hospital.mantenimientos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthLoginRequestDto {

    @NotBlank(message = "correo es obligatorio")
    @Email(message = "correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "contrasena es obligatoria")
    private String contrasena;

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
