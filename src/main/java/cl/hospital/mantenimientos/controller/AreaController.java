package cl.hospital.mantenimientos.controller;

import cl.hospital.mantenimientos.entity.Area;
import cl.hospital.mantenimientos.repository.AreaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    private final AreaRepository areaRepository;

    public AreaController(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @GetMapping
    public List<Area> listar() {
        return areaRepository.findAll();
    }
}
