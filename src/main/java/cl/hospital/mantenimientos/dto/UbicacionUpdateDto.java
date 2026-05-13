package cl.hospital.mantenimientos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UbicacionUpdateDto {

    @NotNull(message = "idArea es obligatorio")
    private Long idArea;

    @NotBlank(message = "nombreUbicacion es obligatorio")
    private String nombreUbicacion;

    public Long getIdArea() { return idArea; }
    public void setIdArea(Long idArea) { this.idArea = idArea; }

    public String getNombreUbicacion() { return nombreUbicacion; }
    public void setNombreUbicacion(String nombreUbicacion) { this.nombreUbicacion = nombreUbicacion; }
}
