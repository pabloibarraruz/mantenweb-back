package cl.hospital.mantenimientos.controller;

import cl.hospital.mantenimientos.entity.EstadoOt;
import cl.hospital.mantenimientos.repository.EstadoOtRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-ot")
public class EstadoOtController {

    private final EstadoOtRepository estadoOtRepository;

    public EstadoOtController(EstadoOtRepository estadoOtRepository) {
        this.estadoOtRepository = estadoOtRepository;
    }

    @GetMapping
    public List<EstadoOt> listar() {
        return estadoOtRepository.findAll(Sort.by(Sort.Direction.ASC, "orden"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoOt> obtenerPorId(@PathVariable Long id) {
        return estadoOtRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
