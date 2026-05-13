package cl.hospital.mantenimientos.repository;

import cl.hospital.mantenimientos.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    Optional<Ubicacion> findFirstByOrderByIdAsc();
}
