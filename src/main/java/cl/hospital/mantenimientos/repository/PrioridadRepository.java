package cl.hospital.mantenimientos.repository;

import cl.hospital.mantenimientos.entity.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrioridadRepository extends JpaRepository<Prioridad, Long> {
    Optional<Prioridad> findByNombrePrioridadIgnoreCase(String nombrePrioridad);
}
