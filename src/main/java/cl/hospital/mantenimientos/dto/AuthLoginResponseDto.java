package cl.hospital.mantenimientos.dto;

public class AuthLoginResponseDto {

    private String token;
    private String tipo;
    private Long idUsuario;
    private String correo;
    private String nombreCompleto;
    private String rol;

    public AuthLoginResponseDto(String token, Long idUsuario, String correo, String nombreCompleto, String rol) {
        this.token = token;
        this.tipo = "Bearer";
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public String getTipo() { return tipo; }
    public Long getIdUsuario() { return idUsuario; }
    public String getCorreo() { return correo; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getRol() { return rol; }
}

