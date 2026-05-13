package cl.hospital.mantenimientos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UbicacionCreateDto {

    @NotNull(message = "idArea es obligatorio")
    private Long idArea;

    @NotBlank(message = "nombreUbicacion es obligatorio")
    @Size(min = 2, max = 80, message = "nombreUbicacion debe tener entre 2 y 80 caracteres")
    private String nombreUbicacion;

    public UbicacionCreateDto() {}

    public Long getIdArea() { return idArea; }
    public void setIdArea(Long idArea) { this.idArea = idArea; }

    public String getNombreUbicacion() { return nombreUbicacion; }
    public void setNombreUbicacion(String nombreUbicacion) { this.nombreUbicacion = nombreUbicacion; }
}
