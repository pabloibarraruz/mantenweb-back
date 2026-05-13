package cl.hospital.mantenimientos.repository;

import cl.hospital.mantenimientos.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    boolean existsByNombreRolIgnoreCase(String nombreRol);
    Optional<Rol> findByNombreRolIgnoreCase(String nombreRol);
}
