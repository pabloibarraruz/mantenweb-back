package cl.hospital.mantenimientos.controller;

import cl.hospital.mantenimientos.dto.UbicacionCreateDto;
import cl.hospital.mantenimientos.dto.UbicacionResponseDto;
import cl.hospital.mantenimientos.entity.Area;
import cl.hospital.mantenimientos.entity.Ubicacion;
import cl.hospital.mantenimientos.exception.ResourceNotFoundException;
import cl.hospital.mantenimientos.repository.AreaRepository;
import cl.hospital.mantenimientos.repository.UbicacionRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    private final UbicacionRepository ubicacionRepository;
    private final AreaRepository areaRepository;

    public UbicacionController(UbicacionRepository ubicacionRepository, AreaRepository areaRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.areaRepository = areaRepository;
    }

    // Listar ubicaciones.
    @GetMapping
    public List<UbicacionResponseDto> listar() {
        return ubicacionRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // Buscar ubicacion por id.
    @GetMapping("/{id}")
    public UbicacionResponseDto obtenerPorId(@PathVariable Long id) {
        Ubicacion u = ubicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada: id=" + id));
        return toDto(u);
    }

    // Crear ubicacion.
    @PostMapping
    public ResponseEntity<UbicacionResponseDto> crear(@Valid @RequestBody UbicacionCreateDto body) {

        Area area = areaRepository.findById(body.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada: id=" + body.getIdArea()));

        Ubicacion u = new Ubicacion();
        u.setArea(area);
        u.setNombreUbicacion(body.getNombreUbicacion().trim());

        Ubicacion guardada = ubicacionRepository.save(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(guardada));
    }

    // Editar ubicacion.
    @PutMapping("/{id}")
    public UbicacionResponseDto actualizar(@PathVariable Long id, @Valid @RequestBody UbicacionCreateDto body) {

        Ubicacion existente = ubicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada: id=" + id));

        Area area = areaRepository.findById(body.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada: id=" + body.getIdArea()));

        existente.setArea(area);
        existente.setNombreUbicacion(body.getNombreUbicacion().trim());

        Ubicacion guardada = ubicacionRepository.save(existente);
        return toDto(guardada);
    }

    // Eliminar ubicacion.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Ubicacion existente = ubicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada: id=" + id));

        ubicacionRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }

    private UbicacionResponseDto toDto(Ubicacion u) {
        Long idArea = (u.getArea() != null) ? u.getArea().getId() : null;
        String nombreArea = (u.getArea() != null) ? u.getArea().getNombreArea() : null;

        return new UbicacionResponseDto(
                u.getId(),
                idArea,
                nombreArea,
                u.getNombreUbicacion()
        );
    }
}
