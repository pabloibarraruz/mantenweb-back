package cl.hospital.mantenimientos.controller;

import cl.hospital.mantenimientos.dto.UsuarioCreateDto;
import cl.hospital.mantenimientos.dto.UsuarioResponseDto;
import cl.hospital.mantenimientos.dto.UsuarioUpdateDto;
import cl.hospital.mantenimientos.entity.Rol;
import cl.hospital.mantenimientos.entity.Usuario;
import cl.hospital.mantenimientos.exception.ResourceNotFoundException;
import cl.hospital.mantenimientos.repository.RolRepository;
import cl.hospital.mantenimientos.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Listar usuarios.
    @GetMapping
    public List<UsuarioResponseDto> listar() {
        return usuarioRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // Buscar un usuario por id.
    @GetMapping("/{id}")
    public UsuarioResponseDto obtener(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: id=" + id));
        return toDto(u);
    }

    // Crear usuario.
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> crear(@Valid @RequestBody UsuarioCreateDto body) {

        Rol rol = rolRepository.findById(body.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: id=" + body.getIdRol()));

        Usuario u = new Usuario();
        u.setRol(rol);
        u.setNombreCompleto(body.getNombreCompleto().trim());
        u.setCorreo(body.getCorreo().trim().toLowerCase());
        u.setHashContrasena(passwordEncoder.encode(body.getContrasena()));
        u.setActivo(body.getActivo() != null ? body.getActivo() : Boolean.TRUE);

        try {
            Usuario guardado = usuarioRepository.save(u);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(guardado));
        } catch (DataIntegrityViolationException ex) {
            // Se deja que el manejador global responda el error de correo repetido.
            throw ex;
        }
    }

    // Actualiza datos basicos, sin cambiar la contrasena.
    @PutMapping("/{id}")
    public UsuarioResponseDto actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateDto body) {

        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: id=" + id));

        Rol rol = rolRepository.findById(body.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: id=" + body.getIdRol()));

        existente.setRol(rol);
        existente.setNombreCompleto(body.getNombreCompleto().trim());
        existente.setCorreo(body.getCorreo().trim().toLowerCase());
        existente.setActivo(body.getActivo() != null ? body.getActivo() : existente.getActivo());

        Usuario guardado = usuarioRepository.save(existente);
        return toDto(guardado);
    }

    // Activa o desactiva el usuario.
    @PatchMapping("/{id}/activar")
    public UsuarioResponseDto activar(@PathVariable Long id, @RequestParam boolean valor) {

        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: id=" + id));

        existente.setActivo(valor);
        Usuario guardado = usuarioRepository.save(existente);
        return toDto(guardado);
    }

    private UsuarioResponseDto toDto(Usuario u) {
        Long idRol = (u.getRol() != null) ? u.getRol().getId() : null;
        String nombreRol = (u.getRol() != null) ? u.getRol().getNombreRol() : null;

        return new UsuarioResponseDto(
                u.getId(),
                idRol,
                nombreRol,
                u.getNombreCompleto(),
                u.getCorreo(),
                u.getActivo(),
                u.getFechaCreacion()
        );
    }
}
