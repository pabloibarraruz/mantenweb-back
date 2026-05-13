package cl.hospital.mantenimientos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prioridades")
public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prioridad")
    private Long idPrioridad;

    @Column(name = "nombre_prioridad", nullable = false, length = 30)
    private String nombrePrioridad;

    @Column(name = "sla_horas")
    private Integer slaHoras;

    public Long getIdPrioridad() { return idPrioridad; }
    public void setIdPrioridad(Long idPrioridad) { this.idPrioridad = idPrioridad; }

    public String getNombrePrioridad() { return nombrePrioridad; }
    public void setNombrePrioridad(String nombrePrioridad) { this.nombrePrioridad = nombrePrioridad; }

    public Integer getSlaHoras() { return slaHoras; }
    public void setSlaHoras(Integer slaHoras) { this.slaHoras = slaHoras; }
}
