package cl.hospital.mantenimientos.repository;

import cl.hospital.mantenimientos.entity.OrdenTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long> {
    List<OrdenTrabajo> findByIdUsuarioAsignadoOrderByFechaActualizacionDesc(Long idUsuarioAsignado);
    List<OrdenTrabajo> findAllByOrderByFechaActualizacionDesc();
    Optional<OrdenTrabajo> findTopByOrderByIdDesc();
}
