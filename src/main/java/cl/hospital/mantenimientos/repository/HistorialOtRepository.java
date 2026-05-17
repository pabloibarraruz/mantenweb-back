package cl.hospital.mantenimientos.repository;

import cl.hospital.mantenimientos.entity.HistorialOt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialOtRepository extends JpaRepository<HistorialOt, Long> {
    List<HistorialOt> findByOrdenTrabajo_IdOrderByFechaCambioDesc(Long idOt);
}

