package cl.hospital.mantenimientos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrdenTrabajoCreateDto {

    @NotBlank(message = "titulo es obligatorio")
    private String titulo;

    @NotBlank(message = "descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "piso es obligatorio")
    private String piso;

    @NotBlank(message = "servicio es obligatorio")
    private String servicio;

    @NotBlank(message = "nombreSolicitante es obligatorio")
    private String nombreSolicitante;

    @NotNull(message = "idEspecialidad es obligatorio")
    private Long idEspecialidad;

    @NotNull(message = "idPrioridad es obligatorio")
    private Long idPrioridad;

    @NotNull(message = "idUsuarioAsignado es obligatorio")
    private Long idUsuarioAsignado;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getPiso() { return piso; }
    public void setPiso(String piso) { this.piso = piso; }

    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }

    public String getNombreSolicitante() { return nombreSolicitante; }
    public void setNombreSolicitante(String nombreSolicitante) { this.nombreSolicitante = nombreSolicitante; }

    public Long getIdEspecialidad() { return idEspecialidad; }
    public void setIdEspecialidad(Long idEspecialidad) { this.idEspecialidad = idEspecialidad; }

    public Long getIdPrioridad() { return idPrioridad; }
    public void setIdPrioridad(Long idPrioridad) { this.idPrioridad = idPrioridad; }

    public Long getIdUsuarioAsignado() { return idUsuarioAsignado; }
    public void setIdUsuarioAsignado(Long idUsuarioAsignado) { this.idUsuarioAsignado = idUsuarioAsignado; }
}
