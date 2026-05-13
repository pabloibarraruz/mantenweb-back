package cl.hospital.mantenimientos.dto;

public class UbicacionResponseDto {

    private Long id;
    private Long idArea;
    private String nombreArea;
    private String nombreUbicacion;

    public UbicacionResponseDto() {}

    public UbicacionResponseDto(Long id, Long idArea, String nombreArea, String nombreUbicacion) {
        this.id = id;
        this.idArea = idArea;
        this.nombreArea = nombreArea;
        this.nombreUbicacion = nombreUbicacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdArea() { return idArea; }
    public void setIdArea(Long idArea) { this.idArea = idArea; }

    public String getNombreArea() { return nombreArea; }
    public void setNombreArea(String nombreArea) { this.nombreArea = nombreArea; }

    public String getNombreUbicacion() { return nombreUbicacion; }
    public void setNombreUbicacion(String nombreUbicacion) { this.nombreUbicacion = nombreUbicacion; }
}
