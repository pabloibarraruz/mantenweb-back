package cl.hospital.mantenimientos.controller;

import cl.hospital.mantenimientos.entity.Especialidad;
import cl.hospital.mantenimientos.repository.EspecialidadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadController(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    @GetMapping
    public List<Especialidad> listar() {
        return especialidadRepository.findAll();
    }

    @GetMapping("/{id}")
    public Especialidad obtenerPorId(@PathVariable Long id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada"));
    }
}
