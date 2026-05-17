package cl.hospital.mantenimientos.controller;

import cl.hospital.mantenimientos.dto.OrdenTrabajoCreateDto;
import cl.hospital.mantenimientos.dto.OrdenTrabajoFinalizarDto;
import cl.hospital.mantenimientos.dto.OrdenTrabajoResponseDto;
import cl.hospital.mantenimientos.entity.OrdenTrabajo;
import cl.hospital.mantenimientos.service.OrdenTrabajoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-trabajo")
public class OrdenTrabajoController {

    private final OrdenTrabajoService ordenTrabajoService;

    public OrdenTrabajoController(OrdenTrabajoService ordenTrabajoService) {
        this.ordenTrabajoService = ordenTrabajoService;
    }

    // Listado general de ordenes.
    @GetMapping
    public List<OrdenTrabajoResponseDto> listar(@AuthenticationPrincipal UserDetails userDetails) {
        requireRole(userDetails, "ADMIN");
        return ordenTrabajoService.listar();
    }

    // Ordenes asignadas al tecnico autenticado.
    @GetMapping("/mis-asignadas")
    public List<OrdenTrabajoResponseDto> listarMisAsignadas(@AuthenticationPrincipal UserDetails userDetails) {
        requireRole(userDetails, "TECNICO");
        return ordenTrabajoService.listarMisAsignadas(userDetails.getUsername());
    }

    // Busca una orden por id.
    @GetMapping("/{id:\\d+}")
    public OrdenTrabajoResponseDto obtenerPorId(@PathVariable Long id) {
        return ordenTrabajoService.obtenerPorId(id);
    }

    // Crea una nueva orden.
    @PostMapping
    public ResponseEntity<OrdenTrabajoResponseDto> crear(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody OrdenTrabajoCreateDto nueva
    ) {
        requireRole(userDetails, "ADMIN");
        OrdenTrabajoResponseDto guardada = ordenTrabajoService.crear(nueva, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // Edita la orden completa.
    @PutMapping("/{id}")
    public OrdenTrabajo actualizar(
            @PathVariable Long id,
            @Valid @RequestBody OrdenTrabajo cambios,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        requireRole(userDetails, "ADMIN");
        return ordenTrabajoService.actualizar(id, cambios);
    }

    // Asigna tecnico a una OT.
    @PatchMapping("/{id}/asignar/{idUsuario}")
    public OrdenTrabajo asignar(
            @PathVariable Long id,
            @PathVariable Long idUsuario,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        requireRole(userDetails, "ADMIN");
        return ordenTrabajoService.asignarUsuario(id, idUsuario);
    }

    // Cambia el estado de la OT.
    @PatchMapping("/{id}/estado/{idEstado}")
    public OrdenTrabajo cambiarEstado(
            @PathVariable Long id,
            @PathVariable Long idEstado,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        requireRole(userDetails, "ADMIN");
        return ordenTrabajoService.cambiarEstado(id, idEstado);
    }

    // Cierra la OT desde jefatura.
    @PatchMapping("/{id}/cerrar")
    public OrdenTrabajo cerrar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        requireRole(userDetails, "ADMIN");
        return ordenTrabajoService.cerrar(id);
    }

    // Finaliza la OT desde la vista del tecnico.
    @PatchMapping("/{id}/finalizar")
    public OrdenTrabajoResponseDto finalizar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody OrdenTrabajoFinalizarDto body
    ) {
        requireRole(userDetails, "TECNICO");
        return ordenTrabajoService.finalizarComoTecnico(id, userDetails.getUsername(), body.getDetalleTrabajoRealizado());
    }

    private void requireRole(UserDetails userDetails, String role) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado.");
        }

        boolean allowed = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase("ROLE_" + role));

        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para esta acción.");
        }
    }
}
