package cl.hospital.mantenimientos.config;

import cl.hospital.mantenimientos.entity.Area;
import cl.hospital.mantenimientos.entity.Especialidad;
import cl.hospital.mantenimientos.entity.EstadoOt;
import cl.hospital.mantenimientos.entity.Prioridad;
import cl.hospital.mantenimientos.entity.Rol;
import cl.hospital.mantenimientos.entity.Ubicacion;
import cl.hospital.mantenimientos.entity.Usuario;
import cl.hospital.mantenimientos.repository.AreaRepository;
import cl.hospital.mantenimientos.repository.EspecialidadRepository;
import cl.hospital.mantenimientos.repository.EstadoOtRepository;
import cl.hospital.mantenimientos.repository.PrioridadRepository;
import cl.hospital.mantenimientos.repository.RolRepository;
import cl.hospital.mantenimientos.repository.UbicacionRepository;
import cl.hospital.mantenimientos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile({"dev", "demo"})
public class DevDataInitializer {

    @Bean
    CommandLineRunner seedDevData(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            EspecialidadRepository especialidadRepository,
            PrioridadRepository prioridadRepository,
            EstadoOtRepository estadoOtRepository,
            AreaRepository areaRepository,
            UbicacionRepository ubicacionRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Rol adminRol = ensureRol(rolRepository, "ADMIN");
            Rol tecnicoRol = ensureRol(rolRepository, "TECNICO");
            ensureRol(rolRepository, "SOLICITANTE");

            Area area = areaRepository.findAll().stream().findFirst().orElseGet(() -> {
                Area nueva = new Area();
                nueva.setNombreArea("Hospitalizacion");
                return areaRepository.save(nueva);
            });

            ensureUbicacion(ubicacionRepository, area, "Piso 1");
            ensureUbicacion(ubicacionRepository, area, "Laboratorio");
            ensureUbicacion(ubicacionRepository, area, "Urgencias");

            ensureEspecialidad(especialidadRepository, "Electricidad");
            ensureEspecialidad(especialidadRepository, "Gasfiteria");
            ensureEspecialidad(especialidadRepository, "Infraestructura");

            ensurePrioridad(prioridadRepository, "Urgente", 4);
            ensurePrioridad(prioridadRepository, "Programado", 48);

            ensureEstado(estadoOtRepository, "Abierta", 1);
            ensureEstado(estadoOtRepository, "En proceso", 2);
            ensureEstado(estadoOtRepository, "Finalizada", 3);

            ensureUsuario(usuarioRepository, tecnicoRol, passwordEncoder, "Tecnico Electricidad", "tecnico.electricidad@hospital.cl", "tecnico123");
            ensureUsuario(usuarioRepository, tecnicoRol, passwordEncoder, "Tecnico Gasfiteria", "tecnico.gasfiteria@hospital.cl", "tecnico123");
            ensureUsuario(usuarioRepository, tecnicoRol, passwordEncoder, "Tecnico Infraestructura", "tecnico.infraestructura@hospital.cl", "tecnico123");
            ensureUsuario(usuarioRepository, tecnicoRol, passwordEncoder, "Tecnico Demo", "tecnico@mantenweb.cl", "Tecnico123");

            if (usuarioRepository.findByCorreoIgnoreCase("admin@hospital.cl").isEmpty()) {
                ensureUsuario(usuarioRepository, adminRol, passwordEncoder, "Administrador", "admin@hospital.cl", "admin123");
            }
            ensureUsuario(usuarioRepository, adminRol, passwordEncoder, "Administrador Demo", "admin@mantenweb.cl", "Admin123");
        };
    }

    private Rol ensureRol(RolRepository repository, String nombre) {
        return repository.findByNombreRolIgnoreCase(nombre).orElseGet(() -> {
            Rol rol = new Rol();
            rol.setNombreRol(nombre.toUpperCase());
            return repository.save(rol);
        });
    }

    private void ensureEspecialidad(EspecialidadRepository repository, String nombre) {
        if (repository.findByNombreEspecialidadIgnoreCase(nombre).isPresent()) return;
        Especialidad especialidad = new Especialidad();
        especialidad.setNombreEspecialidad(nombre);
        repository.save(especialidad);
    }

    private void ensurePrioridad(PrioridadRepository repository, String nombre, int slaHoras) {
        if (repository.findByNombrePrioridadIgnoreCase(nombre).isPresent()) return;
        Prioridad prioridad = new Prioridad();
        prioridad.setNombrePrioridad(nombre);
        prioridad.setSlaHoras(slaHoras);
        repository.save(prioridad);
    }

    private void ensureEstado(EstadoOtRepository repository, String nombre, int orden) {
        if (repository.findByNombreEstadoIgnoreCase(nombre).isPresent()) return;
        EstadoOt estado = new EstadoOt();
        estado.setNombreEstado(nombre);
        estado.setOrden(orden);
        repository.save(estado);
    }

    private void ensureUbicacion(UbicacionRepository repository, Area area, String nombre) {
        boolean existe = repository.findAll().stream()
                .anyMatch(ubicacion -> nombre.equalsIgnoreCase(ubicacion.getNombreUbicacion()));
        if (existe) return;

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setArea(area);
        ubicacion.setNombreUbicacion(nombre);
        repository.save(ubicacion);
    }

    private void ensureUsuario(
            UsuarioRepository repository,
            Rol rol,
            PasswordEncoder passwordEncoder,
            String nombreCompleto,
            String correo,
            String passwordPlano
    ) {
        if (repository.findByCorreoIgnoreCase(correo).isPresent()) return;

        Usuario usuario = new Usuario();
        usuario.setRol(rol);
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setCorreo(correo);
        usuario.setHashContrasena(passwordEncoder.encode(passwordPlano));
        usuario.setActivo(true);
        repository.save(usuario);
    }
}
