package cl.hospital.mantenimientos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RolCreateDto {

    @NotBlank(message = "nombreRol es obligatorio")
    @Size(max = 45, message = "nombreRol no puede superar 45 caracteres")
    private String nombreRol;

    public RolCreateDto() {
    }

    public RolCreateDto(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
