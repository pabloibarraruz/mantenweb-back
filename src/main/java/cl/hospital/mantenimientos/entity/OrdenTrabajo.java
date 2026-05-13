package cl.hospital.mantenimientos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_trabajo")
public class OrdenTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ot")
    private Long id;

    @Column(name = "codigo_ot", nullable = false, length = 30)
    @NotBlank(message = "codigoOt es obligatorio")
    private String codigoOt;

    @Column(name = "titulo", nullable = false, length = 120)
    @NotBlank(message = "titulo es obligatorio")
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    @NotBlank(message = "descripcion es obligatoria")
    private String descripcion;

    @Column(name = "piso", length = 40)
    private String piso;

    @Column(name = "servicio", length = 120)
    private String servicio;

    @Column(name = "nombre_solicitante", length = 120)
    private String nombreSolicitante;

    @Column(name = "detalle_trabajo_realizado", columnDefinition = "text")
    private String detalleTrabajoRealizado;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "fecha_compromiso")
    private LocalDateTime fechaCompromiso;

    @Column(name = "id_especialidad", nullable = false)
    @NotNull(message = "idEspecialidad es obligatorio")
    private Long idEspecialidad;

    @Column(name = "id_estado", nullable = false)
    @NotNull(message = "idEstado es obligatorio")
    private Long idEstado;

    @Column(name = "id_prioridad", nullable = false)
    @NotNull(message = "idPrioridad es obligatorio")
    private Long idPrioridad;

    @Column(name = "id_ubicacion", nullable = false)
    @NotNull(message = "idUbicacion es obligatorio")
    private Long idUbicacion;

    @Column(name = "id_usuario_asignado")
    private Long idUsuarioAsignado;

    @Column(name = "id_usuario_creador", nullable = false)
    @NotNull(message = "idUsuarioCreador es obligatorio")
    private Long idUsuarioCreador;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoOt() { return codigoOt; }
    public void setCodigoOt(String codigoOt) { this.codigoOt = codigoOt; }

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

    public String getDetalleTrabajoRealizado() { return detalleTrabajoRealizado; }
    public void setDetalleTrabajoRealizado(String detalleTrabajoRealizado) { this.detalleTrabajoRealizado = detalleTrabajoRealizado; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public LocalDateTime getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDateTime fechaApertura) { this.fechaApertura = fechaApertura; }

    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }

    public LocalDateTime getFechaCompromiso() { return fechaCompromiso; }
    public void setFechaCompromiso(LocalDateTime fechaCompromiso) { this.fechaCompromiso = fechaCompromiso; }

    public Long getIdEspecialidad() { return idEspecialidad; }
    public void setIdEspecialidad(Long idEspecialidad) { this.idEspecialidad = idEspecialidad; }

    public Long getIdEstado() { return idEstado; }
    public void setIdEstado(Long idEstado) { this.idEstado = idEstado; }

    public Long getIdPrioridad() { return idPrioridad; }
    public void setIdPrioridad(Long idPrioridad) { this.idPrioridad = idPrioridad; }

    public Long getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(Long idUbicacion) { this.idUbicacion = idUbicacion; }

    public Long getIdUsuarioAsignado() { return idUsuarioAsignado; }
    public void setIdUsuarioAsignado(Long idUsuarioAsignado) { this.idUsuarioAsignado = idUsuarioAsignado; }

    public Long getIdUsuarioCreador() { return idUsuarioCreador; }
    public void setIdUsuarioCreador(Long idUsuarioCreador) { this.idUsuarioCreador = idUsuarioCreador; }
}
