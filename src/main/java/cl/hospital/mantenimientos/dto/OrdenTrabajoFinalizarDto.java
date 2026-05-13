package cl.hospital.mantenimientos.dto;

import jakarta.validation.constraints.NotBlank;

public class OrdenTrabajoFinalizarDto {

    @NotBlank(message = "detalleTrabajoRealizado es obligatorio")
    private String detalleTrabajoRealizado;

    public String getDetalleTrabajoRealizado() { return detalleTrabajoRealizado; }
    public void setDetalleTrabajoRealizado(String detalleTrabajoRealizado) { this.detalleTrabajoRealizado = detalleTrabajoRealizado; }
}
