-- MantenWeb - Esquema MySQL actual
-- Este script documenta las tablas implementadas por el backend Spring Boot.
-- No incluye materiales, adjuntos, inventario ni auditoria avanzada porque no forman parte del alcance actual.

CREATE DATABASE IF NOT EXISTS mantenweb
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE mantenweb;

CREATE TABLE IF NOT EXISTS roles (
  id_rol BIGINT NOT NULL AUTO_INCREMENT,
  nombre_rol VARCHAR(45) NOT NULL,
  PRIMARY KEY (id_rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS especialidades (
  id_especialidad BIGINT NOT NULL AUTO_INCREMENT,
  nombre_especialidad VARCHAR(60) NOT NULL,
  PRIMARY KEY (id_especialidad)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS usuarios (
  id_usuario BIGINT NOT NULL AUTO_INCREMENT,
  id_rol BIGINT NOT NULL,
  id_especialidad BIGINT NULL,
  nombre_completo VARCHAR(120) NOT NULL,
  correo VARCHAR(120) NOT NULL,
  hash_contrasena VARCHAR(255) NOT NULL,
  activo TINYINT(1) NOT NULL,
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_usuario),
  KEY idx_usuarios_rol (id_rol),
  KEY idx_usuarios_especialidad (id_especialidad),
  CONSTRAINT fk_usuarios_roles
    FOREIGN KEY (id_rol) REFERENCES roles (id_rol),
  CONSTRAINT fk_usuarios_especialidades
    FOREIGN KEY (id_especialidad) REFERENCES especialidades (id_especialidad)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS areas (
  id_area BIGINT NOT NULL AUTO_INCREMENT,
  nombre_area VARCHAR(80) NOT NULL,
  PRIMARY KEY (id_area)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS ubicaciones (
  id_ubicacion BIGINT NOT NULL AUTO_INCREMENT,
  id_area BIGINT NOT NULL,
  id_usuario_encargado BIGINT NULL,
  nombre_ubicacion VARCHAR(80) NOT NULL,
  PRIMARY KEY (id_ubicacion),
  KEY idx_ubicaciones_area (id_area),
  KEY idx_ubicaciones_usuario_encargado (id_usuario_encargado),
  CONSTRAINT fk_ubicaciones_areas
    FOREIGN KEY (id_area) REFERENCES areas (id_area),
  CONSTRAINT fk_ubicaciones_usuario_encargado
    FOREIGN KEY (id_usuario_encargado) REFERENCES usuarios (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS prioridades (
  id_prioridad BIGINT NOT NULL AUTO_INCREMENT,
  nombre_prioridad VARCHAR(30) NOT NULL,
  sla_horas INT NULL,
  PRIMARY KEY (id_prioridad)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS estados_ot (
  id_estado BIGINT NOT NULL AUTO_INCREMENT,
  nombre_estado VARCHAR(30) NOT NULL,
  orden INT NOT NULL,
  PRIMARY KEY (id_estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS ordenes_trabajo (
  id_ot BIGINT NOT NULL AUTO_INCREMENT,
  codigo_ot VARCHAR(30) NOT NULL,
  titulo VARCHAR(120) NOT NULL,
  descripcion TEXT NOT NULL,
  piso VARCHAR(40) NULL,
  servicio VARCHAR(120) NULL,
  nombre_solicitante VARCHAR(120) NULL,
  detalle_trabajo_realizado TEXT NULL,
  fecha_actualizacion DATETIME NOT NULL,
  fecha_apertura DATETIME NOT NULL,
  fecha_cierre DATETIME NULL,
  fecha_compromiso DATETIME NULL,
  id_especialidad BIGINT NOT NULL,
  id_estado BIGINT NOT NULL,
  id_prioridad BIGINT NOT NULL,
  id_ubicacion BIGINT NOT NULL,
  id_usuario_asignado BIGINT NULL,
  id_usuario_creador BIGINT NOT NULL,
  PRIMARY KEY (id_ot),
  KEY idx_ot_especialidad (id_especialidad),
  KEY idx_ot_estado (id_estado),
  KEY idx_ot_prioridad (id_prioridad),
  KEY idx_ot_ubicacion (id_ubicacion),
  KEY idx_ot_usuario_asignado (id_usuario_asignado),
  KEY idx_ot_usuario_creador (id_usuario_creador),
  CONSTRAINT fk_ot_especialidades
    FOREIGN KEY (id_especialidad) REFERENCES especialidades (id_especialidad),
  CONSTRAINT fk_ot_estados
    FOREIGN KEY (id_estado) REFERENCES estados_ot (id_estado),
  CONSTRAINT fk_ot_prioridades
    FOREIGN KEY (id_prioridad) REFERENCES prioridades (id_prioridad),
  CONSTRAINT fk_ot_ubicaciones
    FOREIGN KEY (id_ubicacion) REFERENCES ubicaciones (id_ubicacion),
  CONSTRAINT fk_ot_usuario_asignado
    FOREIGN KEY (id_usuario_asignado) REFERENCES usuarios (id_usuario),
  CONSTRAINT fk_ot_usuario_creador
    FOREIGN KEY (id_usuario_creador) REFERENCES usuarios (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS password_reset_tokens (
  id_token BIGINT NOT NULL AUTO_INCREMENT,
  token VARCHAR(64) NOT NULL,
  id_usuario BIGINT NOT NULL,
  fecha_expiracion DATETIME NOT NULL,
  usado TINYINT(1) NOT NULL,
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_token),
  UNIQUE KEY uk_password_reset_token (token),
  KEY idx_password_reset_usuario (id_usuario),
  CONSTRAINT fk_password_reset_usuarios
    FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS historial_ot (
  id_historial BIGINT NOT NULL AUTO_INCREMENT,
  id_ot BIGINT NOT NULL,
  id_usuario BIGINT NOT NULL,
  campo_modificado VARCHAR(60) NOT NULL,
  valor_anterior TEXT NULL,
  valor_nuevo TEXT NULL,
  comentario TEXT NULL,
  fecha_cambio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_historial),
  KEY idx_historial_ot_ot (id_ot),
  KEY idx_historial_ot_usuario (id_usuario),
  CONSTRAINT fk_historial_ot_ordenes_trabajo
    FOREIGN KEY (id_ot) REFERENCES ordenes_trabajo (id_ot)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_historial_ot_usuarios
    FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
