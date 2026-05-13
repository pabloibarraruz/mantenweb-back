package cl.hospital.mantenimientos.controller;

import cl.hospital.mantenimientos.dto.AuthLoginRequestDto;
import cl.hospital.mantenimientos.dto.AuthLoginResponseDto;
import cl.hospital.mantenimientos.dto.ForgotPasswordRequestDto;
import cl.hospital.mantenimientos.dto.ResetPasswordRequestDto;
import cl.hospital.mantenimientos.entity.Rol;
import cl.hospital.mantenimientos.entity.Usuario;
import cl.hospital.mantenimientos.repository.UsuarioRepository;
import cl.hospital.mantenimientos.security.JwtService;
import cl.hospital.mantenimientos.service.PasswordResetService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Login y recuperacion de contrasena")
@SecurityRequirements
public class AuthController {

    private final PasswordResetService passwordResetService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            PasswordResetService passwordResetService,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.passwordResetService = passwordResetService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthLoginResponseDto login(@Valid @RequestBody AuthLoginRequestDto dto) {
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(dto.getCorreo().trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas."));

        Boolean activo = usuario.getActivo();
        if (activo != null && !activo) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario inactivo.");
        }

        if (!passwordEncoder.matches(dto.getContrasena(), usuario.getHashContrasena())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas.");
        }

        String token = jwtService.generarToken(usuario.getCorreo());

        return new AuthLoginResponseDto(
                token,
                usuario.getId(),
                usuario.getCorreo(),
                usuario.getNombreCompleto(),
                normalizarRol(usuario.getRol())
        );
    }

    @PostMapping("/forgot-password")
    public Map<String, String> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto dto) {
        passwordResetService.solicitarReset(dto.getCorreo());
        return Map.of("message", "Si el correo existe, se enviará un link de recuperación.");
    }

    @PostMapping("/reset-password")
    public Map<String, String> resetPassword(@Valid @RequestBody ResetPasswordRequestDto dto) {
        passwordResetService.resetPassword(dto.getToken(), dto.getNuevaContrasena());
        return Map.of("message", "Contraseña actualizada correctamente.");
    }

    private String normalizarRol(Rol rol) {
        if (rol == null || rol.getNombreRol() == null) {
            return "USER";
        }

        String nombre = rol.getNombreRol().trim();
        if (nombre.startsWith("ROLE_")) {
            nombre = nombre.substring(5);
        }

        return nombre.isBlank() ? "USER" : nombre;
    }
}
