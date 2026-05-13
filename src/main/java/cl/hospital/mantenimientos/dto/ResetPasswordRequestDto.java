package cl.hospital.mantenimientos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequestDto {

    @NotBlank(message = "token es obligatorio")
    private String token;

    @NotBlank(message = "nuevaContrasena es obligatoria")
    @Size(min = 6, message = "nuevaContrasena debe tener al menos 6 caracteres")
    private String nuevaContrasena;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getNuevaContrasena() { return nuevaContrasena; }
    public void setNuevaContrasena(String nuevaContrasena) { this.nuevaContrasena = nuevaContrasena; }
}
