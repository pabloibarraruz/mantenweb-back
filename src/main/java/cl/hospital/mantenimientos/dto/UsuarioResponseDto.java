package cl.hospital.mantenimientos.dto;

import java.time.LocalDateTime;

public class UsuarioResponseDto {

    private Long id;
    private Long idRol;
    private String nombreRol;
    private String nombreCompleto;
    private String correo;
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    public UsuarioResponseDto() {}

    public UsuarioResponseDto(Long id, Long idRol, String nombreRol, String nombreCompleto,
                              String correo, Boolean activo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdRol() { return idRol; }
    public void setIdRol(Long idRol) { this.idRol = idRol; }

    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
