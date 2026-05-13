package cl.hospital.mantenimientos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ubicaciones")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;

    @Column(name = "nombre_ubicacion", nullable = false, length = 80)
    private String nombreUbicacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }

    public String getNombreUbicacion() { return nombreUbicacion; }
    public void setNombreUbicacion(String nombreUbicacion) { this.nombreUbicacion = nombreUbicacion; }
}
