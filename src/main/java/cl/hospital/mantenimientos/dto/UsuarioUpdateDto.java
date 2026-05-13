package cl.hospital.mantenimientos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioUpdateDto {

    @NotNull(message = "idRol es obligatorio")
    private Long idRol;

    @NotBlank(message = "nombreCompleto es obligatorio")
    @Size(min = 3, max = 120, message = "nombreCompleto debe tener entre 3 y 120 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "correo es obligatorio")
    @Email(message = "correo debe tener un formato válido")
    @Size(max = 150, message = "correo no puede superar 150 caracteres")
    private String correo;

    private Boolean activo;

    public UsuarioUpdateDto() {
    }

    public UsuarioUpdateDto(Long idRol, String nombreCompleto, String correo, Boolean activo) {
        this.idRol = idRol;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.activo = activo;
    }

    public Long getIdRol() { return idRol; }
    public void setIdRol(Long idRol) { this.idRol = idRol; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
