package cl.hospital.mantenimientos.controller;

import cl.hospital.mantenimientos.dto.RolCreateDto;
import cl.hospital.mantenimientos.entity.Rol;
import cl.hospital.mantenimientos.repository.RolRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolController {

    private final RolRepository rolRepository;

    public RolController(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @GetMapping("/roles")
    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    @PostMapping("/roles")
    public ResponseEntity<Rol> crear(@Valid @RequestBody RolCreateDto body) {
        String nombreRol = body.getNombreRol().trim();

        if (rolRepository.existsByNombreRolIgnoreCase(nombreRol)) {
            throw new IllegalArgumentException("Ya existe un rol con ese nombre.");
        }

        Rol rol = new Rol();
        rol.setNombreRol(nombreRol.toUpperCase());

        Rol guardado = rolRepository.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
