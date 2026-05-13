# Base de datos MantenWeb

Esta carpeta documenta el modelo de base de datos usado por el backend Spring Boot.

## Archivos

- `schema.sql`: estructura MySQL del modelo actual.

## Tablas incluidas

- `roles`
- `usuarios`
- `areas`
- `ubicaciones`
- `especialidades`
- `prioridades`
- `estados_ot`
- `ordenes_trabajo`
- `password_reset_tokens`

## Alcance del modelo

El modelo actual respalda el flujo principal de la aplicacion:

1. Inicio de sesion y control por rol.
2. Registro de ordenes de trabajo.
3. Asignacion de una OT a un tecnico.
4. Seguimiento por estado.
5. Cierre de la OT con observacion del trabajo realizado.
6. Recuperacion de credenciales mediante token temporal.

El esquema no incluye materiales, inventario, adjuntos ni auditoria avanzada, porque esas funciones no forman parte de la implementacion demostrada en esta entrega.

## Uso local

Para crear el esquema en MySQL:

```sql
SOURCE database/schema.sql;
```

Tambien se puede copiar el contenido de `schema.sql` y ejecutarlo desde MySQL Workbench.

En ambiente `dev` y `demo`, el backend carga datos iniciales desde `DevDataInitializer`, por lo que no se incluyen contrasenas ni hashes fijos en este script.
