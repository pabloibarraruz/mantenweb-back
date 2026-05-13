package cl.hospital.mantenimientos.service;

import cl.hospital.mantenimientos.entity.PasswordResetToken;
import cl.hospital.mantenimientos.entity.Usuario;
import cl.hospital.mantenimientos.repository.PasswordResetTokenRepository;
import cl.hospital.mantenimientos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final String resetUrl;
    private final long tokenMinutes;

    public PasswordResetService(
            UsuarioRepository usuarioRepository,
            PasswordResetTokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            @Value("${app.frontend.reset-url}") String resetUrl,
            @Value("${app.security.reset-token-minutes:30}") long tokenMinutes
    ) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.resetUrl = resetUrl;
        this.tokenMinutes = tokenMinutes;
    }

    public void solicitarReset(String correo) {
        usuarioRepository.findByCorreoIgnoreCase(correo).ifPresent(usuario -> {

            String token = UUID.randomUUID().toString().replace("-", "");

            PasswordResetToken t = new PasswordResetToken();
            t.setUsuario(usuario);
            t.setToken(token);
            t.setFechaExpiracion(LocalDateTime.now().plusMinutes(tokenMinutes));
            t.setUsado(false);

            tokenRepository.save(t);

            String link = String.format(resetUrl, token);

            String subject = "Recuperación de contraseña - MantenWeb";
            String body = "Se solicitó recuperación de contraseña.\n\nLink:\n" + link
                    + "\n\nEste link expira en " + tokenMinutes + " minutos.";

            emailService.enviar(usuario.getCorreo(), subject, body);
        });

        // Si el correo no existe, se responde igual para no mostrar si esta registrado.
    }

    public void resetPassword(String token, String nuevaContrasena) {
        PasswordResetToken t = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido o expirado"));

        if (Boolean.TRUE.equals(t.getUsado())) {
            throw new IllegalArgumentException("Token inválido o expirado");
        }

        if (t.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token inválido o expirado");
        }

        Usuario usuario = t.getUsuario();
        usuario.setHashContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);

        t.setUsado(true);
        tokenRepository.save(t);
    }
}
