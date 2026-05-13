package cl.hospital.mantenimientos.dto;

import java.time.LocalDateTime;

public class OrdenTrabajoResponseDto {
    private Long id;
    private String codigoOt;
    private String titulo;
    private String descripcion;
    private String piso;
    private String servicio;
    private String nombreSolicitante;
    private String detalleTrabajoRealizado;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaCierre;
    private Long idEspecialidad;
    private String especialidadNombre;
    private Long idEstado;
    private String estadoNombre;
    private Long idPrioridad;
    private String prioridadNombre;
    private Long idUsuarioAsignado;
    private String tecnicoAsignadoNombre;
    private Long idUsuarioCreador;
    private String creadorNombre;

    public OrdenTrabajoResponseDto() {}

    public OrdenTrabajoResponseDto(
            Long id,
            String codigoOt,
            String titulo,
            String descripcion,
            String piso,
            String servicio,
            String nombreSolicitante,
            String detalleTrabajoRealizado,
            LocalDateTime fechaApertura,
            LocalDateTime fechaActualizacion,
            LocalDateTime fechaCierre,
            Long idEspecialidad,
            String especialidadNombre,
            Long idEstado,
            String estadoNombre,
            Long idPrioridad,
            String prioridadNombre,
            Long idUsuarioAsignado,
            String tecnicoAsignadoNombre,
            Long idUsuarioCreador,
            String creadorNombre
    ) {
        this.id = id;
        this.codigoOt = codigoOt;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.piso = piso;
        this.servicio = servicio;
        this.nombreSolicitante = nombreSolicitante;
        this.detalleTrabajoRealizado = detalleTrabajoRealizado;
        this.fechaApertura = fechaApertura;
        this.fechaActualizacion = fechaActualizacion;
        this.fechaCierre = fechaCierre;
        this.idEspecialidad = idEspecialidad;
        this.especialidadNombre = especialidadNombre;
        this.idEstado = idEstado;
        this.estadoNombre = estadoNombre;
        this.idPrioridad = idPrioridad;
        this.prioridadNombre = prioridadNombre;
        this.idUsuarioAsignado = idUsuarioAsignado;
        this.tecnicoAsignadoNombre = tecnicoAsignadoNombre;
        this.idUsuarioCreador = idUsuarioCreador;
        this.creadorNombre = creadorNombre;
    }

    public Long getId() { return id; }
    public String getCodigoOt() { return codigoOt; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getPiso() { return piso; }
    public String getServicio() { return servicio; }
    public String getNombreSolicitante() { return nombreSolicitante; }
    public String getDetalleTrabajoRealizado() { return detalleTrabajoRealizado; }
    public LocalDateTime getFechaApertura() { return fechaApertura; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public Long getIdEspecialidad() { return idEspecialidad; }
    public String getEspecialidadNombre() { return especialidadNombre; }
    public Long getIdEstado() { return idEstado; }
    public String getEstadoNombre() { return estadoNombre; }
    public Long getIdPrioridad() { return idPrioridad; }
    public String getPrioridadNombre() { return prioridadNombre; }
    public Long getIdUsuarioAsignado() { return idUsuarioAsignado; }
    public String getTecnicoAsignadoNombre() { return tecnicoAsignadoNombre; }
    public Long getIdUsuarioCreador() { return idUsuarioCreador; }
    public String getCreadorNombre() { return creadorNombre; }
}
