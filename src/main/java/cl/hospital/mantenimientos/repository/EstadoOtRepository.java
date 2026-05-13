package cl.hospital.mantenimientos.repository;

import cl.hospital.mantenimientos.entity.EstadoOt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoOtRepository extends JpaRepository<EstadoOt, Long> {
    Optional<EstadoOt> findByNombreEstadoIgnoreCase(String nombreEstado);
}
