package cl.hospital.mantenimientos.service;

import cl.hospital.mantenimientos.entity.Rol;
import cl.hospital.mantenimientos.entity.Usuario;
import cl.hospital.mantenimientos.repository.UsuarioRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public DbUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByCorreoIgnoreCase(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        Boolean activo = u.getActivo();
        if (activo != null && !activo) {
            throw new DisabledException("Usuario inactivo");
        }

        String rolNombre = normalizarRol(u.getRol());

        return User.builder()
                .username(u.getCorreo())
                .password(u.getHashContrasena())
                .roles(rolNombre)
                .build();
    }

    private String normalizarRol(Rol rol) {
        if (rol == null || rol.getNombreRol() == null) {
            return "USER";
        }
        String nombre = rol.getNombreRol().trim();
        if (nombre.startsWith("ROLE_")) {
            nombre = nombre.substring(5);
        }
        if (nombre.isBlank()) {
            return "USER";
        }
        return nombre;
    }
}
