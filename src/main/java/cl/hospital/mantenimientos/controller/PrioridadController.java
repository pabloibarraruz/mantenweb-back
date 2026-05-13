package cl.hospital.mantenimientos.controller;

import cl.hospital.mantenimientos.entity.Prioridad;
import cl.hospital.mantenimientos.repository.PrioridadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/prioridades")
public class PrioridadController {

    private final PrioridadRepository prioridadRepository;

    public PrioridadController(PrioridadRepository prioridadRepository) {
        this.prioridadRepository = prioridadRepository;
    }

    @GetMapping
    public List<Prioridad> listar() {
        return prioridadRepository.findAll();
    }

    @GetMapping("/{id}")
    public Prioridad obtenerPorId(@PathVariable Long id) {
        return prioridadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prioridad no encontrada"));
    }
}
