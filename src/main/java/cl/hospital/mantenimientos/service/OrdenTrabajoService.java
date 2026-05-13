package cl.hospital.mantenimientos.service;

import cl.hospital.mantenimientos.dto.OrdenTrabajoCreateDto;
import cl.hospital.mantenimientos.dto.OrdenTrabajoResponseDto;
import cl.hospital.mantenimientos.entity.EstadoOt;
import cl.hospital.mantenimientos.entity.OrdenTrabajo;
import cl.hospital.mantenimientos.entity.Prioridad;
import cl.hospital.mantenimientos.entity.Usuario;
import cl.hospital.mantenimientos.exception.ResourceNotFoundException;
import cl.hospital.mantenimientos.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenTrabajoService {

    private final OrdenTrabajoRepository ordenTrabajoRepository;
    private final EspecialidadRepository especialidadRepository;
    private final EstadoOtRepository estadoOtRepository;
    private final PrioridadRepository prioridadRepository;
    private final UbicacionRepository ubicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final Clock appClock;

    public OrdenTrabajoService(
            OrdenTrabajoRepository ordenTrabajoRepository,
            EspecialidadRepository especialidadRepository,
            EstadoOtRepository estadoOtRepository,
            PrioridadRepository prioridadRepository,
            UbicacionRepository ubicacionRepository,
            UsuarioRepository usuarioRepository,
            Clock appClock
    ) {
        this.ordenTrabajoRepository = ordenTrabajoRepository;
        this.especialidadRepository = especialidadRepository;
        this.estadoOtRepository = estadoOtRepository;
        this.prioridadRepository = prioridadRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.appClock = appClock;
    }

    @Transactional(readOnly = true)
    public List<OrdenTrabajoResponseDto> listar() {
        return ordenTrabajoRepository.findAllByOrderByFechaActualizacionDesc().stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrdenTrabajoResponseDto> listarMisAsignadas(String correoUsuario) {
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(correoUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado para correo=" + correoUsuario));

        return ordenTrabajoRepository.findByIdUsuarioAsignadoOrderByFechaActualizacionDesc(usuario.getId()).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrdenTrabajoResponseDto obtenerPorId(Long id) {
        return toResponseDto(obtenerEntidadPorId(id));
    }

    @Transactional
    public OrdenTrabajoResponseDto crear(OrdenTrabajoCreateDto dto, String correoUsuarioCreador) {
        Usuario creador = usuarioRepository.findByCorreoIgnoreCase(correoUsuarioCreador)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario creador no encontrado."));

        EstadoOt estadoEnProceso = buscarEstadoPorNombre("En proceso")
                .orElseGet(() -> buscarEstadoPorNombre("Abierta")
                        .orElseThrow(() -> new ResourceNotFoundException("No existe un estado inicial para la OT.")));

        OrdenTrabajo ot = new OrdenTrabajo();
        ot.setCodigoOt(generarCodigoOt());
        ot.setTitulo(dto.getTitulo().trim());
        ot.setDescripcion(dto.getDescripcion().trim());
        ot.setPiso(dto.getPiso().trim());
        ot.setServicio(dto.getServicio().trim());
        ot.setNombreSolicitante(dto.getNombreSolicitante().trim());
        ot.setIdEspecialidad(dto.getIdEspecialidad());
        ot.setIdPrioridad(dto.getIdPrioridad());
        ot.setIdUsuarioAsignado(dto.getIdUsuarioAsignado());
        ot.setIdUsuarioCreador(creador.getId());
        ot.setIdEstado(estadoEnProceso.getId());
        ot.setIdUbicacion(obtenerUbicacionPorDefectoId());

        validarReferencias(ot);

        LocalDateTime now = LocalDateTime.now(appClock);
        ot.setFechaApertura(now);
        ot.setFechaActualizacion(now);

        return toResponseDto(ordenTrabajoRepository.save(ot));
    }

    @Transactional
    public OrdenTrabajo actualizar(Long id, OrdenTrabajo datos) {
        OrdenTrabajo actual = obtenerEntidadPorId(id);

        actual.setCodigoOt(datos.getCodigoOt());
        actual.setTitulo(datos.getTitulo());
        actual.setDescripcion(datos.getDescripcion());
        actual.setPiso(datos.getPiso());
        actual.setServicio(datos.getServicio());
        actual.setNombreSolicitante(datos.getNombreSolicitante());
        actual.setDetalleTrabajoRealizado(datos.getDetalleTrabajoRealizado());
        actual.setFechaCompromiso(datos.getFechaCompromiso());
        actual.setIdEspecialidad(datos.getIdEspecialidad());
        actual.setIdEstado(datos.getIdEstado());
        actual.setIdPrioridad(datos.getIdPrioridad());
        actual.setIdUbicacion(datos.getIdUbicacion());
        actual.setIdUsuarioAsignado(datos.getIdUsuarioAsignado());
        actual.setIdUsuarioCreador(datos.getIdUsuarioCreador());

        actual.setFechaActualizacion(LocalDateTime.now(appClock));

        validarReferencias(actual);
        return ordenTrabajoRepository.save(actual);
    }

    @Transactional
    public OrdenTrabajo asignarUsuario(Long idOt, Long idUsuario) {
        OrdenTrabajo ot = obtenerEntidadPorId(idOt);
        validarUsuarioExiste(idUsuario);

        ot.setIdUsuarioAsignado(idUsuario);
        ot.setFechaActualizacion(LocalDateTime.now(appClock));
        return ordenTrabajoRepository.save(ot);
    }

    @Transactional
    public OrdenTrabajo cambiarEstado(Long idOt, Long idEstado) {
        OrdenTrabajo ot = obtenerEntidadPorId(idOt);
        validarEstadoExiste(idEstado);

        ot.setIdEstado(idEstado);
        ot.setFechaActualizacion(LocalDateTime.now(appClock));
        return ordenTrabajoRepository.save(ot);
    }

    @Transactional
    public OrdenTrabajo cerrar(Long idOt) {
        OrdenTrabajo ot = obtenerEntidadPorId(idOt);

        if (ot.getFechaCierre() != null) {
            return ot; // ya estaba cerrada
        }

        LocalDateTime now = LocalDateTime.now(appClock);
        ot.setFechaCierre(now);
        ot.setFechaActualizacion(now);
        return ordenTrabajoRepository.save(ot);
    }

    @Transactional
    public OrdenTrabajoResponseDto finalizarComoTecnico(Long idOt, String correoUsuario, String detalleTrabajoRealizado) {
        OrdenTrabajo ot = obtenerEntidadPorId(idOt);
        Usuario tecnico = usuarioRepository.findByCorreoIgnoreCase(correoUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario técnico no encontrado."));

        if (ot.getIdUsuarioAsignado() == null || !ot.getIdUsuarioAsignado().equals(tecnico.getId())) {
            throw new IllegalArgumentException("No puedes finalizar una OT que no está asignada a tu usuario.");
        }

        EstadoOt estadoFinal = buscarEstadoPorNombre("Finalizada")
                .orElseGet(() -> buscarEstadoPorNombre("Cerrada")
                        .orElseThrow(() -> new ResourceNotFoundException("No existe un estado final para la OT.")));

        ot.setDetalleTrabajoRealizado(detalleTrabajoRealizado.trim());
        ot.setIdEstado(estadoFinal.getId());
        LocalDateTime now = LocalDateTime.now(appClock);
        ot.setFechaCierre(now);
        ot.setFechaActualizacion(now);

        return toResponseDto(ordenTrabajoRepository.save(ot));
    }

    private OrdenTrabajo obtenerEntidadPorId(Long id) {
        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden de trabajo no encontrada: id=" + id));
    }

    private String generarCodigoOt() {
        long siguienteId = ordenTrabajoRepository.findTopByOrderByIdDesc()
                .map(OrdenTrabajo::getId)
                .orElse(0L) + 1L;
        return String.format("OT-%04d", siguienteId);
    }

    private Long obtenerUbicacionPorDefectoId() {
        return ubicacionRepository.findFirstByOrderByIdAsc()
                .map(ubicacion -> ubicacion.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No existe una ubicación base configurada."));
    }

    private Optional<EstadoOt> buscarEstadoPorNombre(String nombreEstado) {
        return estadoOtRepository.findAll().stream()
                .filter(estado -> estado.getNombreEstado() != null)
                .filter(estado -> estado.getNombreEstado().trim().equalsIgnoreCase(nombreEstado))
                .findFirst();
    }

    private OrdenTrabajoResponseDto toResponseDto(OrdenTrabajo ot) {
        String especialidadNombre = especialidadRepository.findById(ot.getIdEspecialidad())
                .map(especialidad -> especialidad.getNombreEspecialidad())
                .orElse(null);

        String estadoNombre = estadoOtRepository.findById(ot.getIdEstado())
                .map(estado -> estado.getNombreEstado())
                .orElse(null);

        String prioridadNombre = prioridadRepository.findById(ot.getIdPrioridad())
                .map(Prioridad::getNombrePrioridad)
                .orElse(null);

        String tecnicoAsignadoNombre = ot.getIdUsuarioAsignado() == null
                ? null
                : usuarioRepository.findById(ot.getIdUsuarioAsignado())
                    .map(Usuario::getNombreCompleto)
                    .orElse(null);

        String creadorNombre = usuarioRepository.findById(ot.getIdUsuarioCreador())
                .map(Usuario::getNombreCompleto)
                .orElse(null);

        return new OrdenTrabajoResponseDto(
                ot.getId(),
                ot.getCodigoOt(),
                ot.getTitulo(),
                ot.getDescripcion(),
                ot.getPiso(),
                ot.getServicio(),
                ot.getNombreSolicitante(),
                ot.getDetalleTrabajoRealizado(),
                ot.getFechaApertura(),
                ot.getFechaActualizacion(),
                ot.getFechaCierre(),
                ot.getIdEspecialidad(),
                especialidadNombre,
                ot.getIdEstado(),
                estadoNombre,
                ot.getIdPrioridad(),
                prioridadNombre,
                ot.getIdUsuarioAsignado(),
                tecnicoAsignadoNombre,
                ot.getIdUsuarioCreador(),
                creadorNombre
        );
    }

    private void validarReferencias(OrdenTrabajo ot) {
        validarEspecialidadExiste(ot.getIdEspecialidad());
        validarEstadoExiste(ot.getIdEstado());
        validarPrioridadExiste(ot.getIdPrioridad());
        validarUbicacionExiste(ot.getIdUbicacion());
        validarUsuarioExiste(ot.getIdUsuarioCreador());

        if (ot.getIdUsuarioAsignado() != null) {
            validarUsuarioExiste(ot.getIdUsuarioAsignado());
        }
    }

    private void validarEspecialidadExiste(Long idEspecialidad) {
        if (idEspecialidad == null) return;
        if (!especialidadRepository.existsById(idEspecialidad)) {
            throw new ResourceNotFoundException("Especialidad no encontrada: id=" + idEspecialidad);
        }
    }

    private void validarEstadoExiste(Long idEstado) {
        if (idEstado == null) return;
        if (!estadoOtRepository.existsById(idEstado)) {
            throw new ResourceNotFoundException("Estado OT no encontrado: id=" + idEstado);
        }
    }

    private void validarPrioridadExiste(Long idPrioridad) {
        if (idPrioridad == null) return;
        if (!prioridadRepository.existsById(idPrioridad)) {
            throw new ResourceNotFoundException("Prioridad no encontrada: id=" + idPrioridad);
        }
    }

    private void validarUbicacionExiste(Long idUbicacion) {
        if (idUbicacion == null) return;
        if (!ubicacionRepository.existsById(idUbicacion)) {
            throw new ResourceNotFoundException("Ubicación no encontrada: id=" + idUbicacion);
        }
    }

    private void validarUsuarioExiste(Long idUsuario) {
        if (idUsuario == null) return;
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ResourceNotFoundException("Usuario no encontrado: id=" + idUsuario);
        }
    }
}
