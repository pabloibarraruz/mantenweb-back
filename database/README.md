# Base de datos

Esta carpeta contiene el script del modelo de datos utilizado por el backend de MantenWeb.

## Archivos

- `schema.sql`: crea las tablas principales, sus claves y relaciones.

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
- `historial_ot`

## Alcance

El modelo respalda el flujo principal trabajado en el proyecto:

1. Inicio de sesion y control por rol.
2. Registro de ordenes de trabajo.
3. Asignacion de una OT a un tecnico.
4. Seguimiento por estado.
5. Cierre de la OT con observacion del trabajo realizado.
6. Recuperacion de credenciales mediante token temporal.
7. Registro de cambios relevantes de la OT mediante `historial_ot`.

El esquema no incluye materiales, inventario, adjuntos ni auditoria avanzada. Esas funciones quedan fuera del alcance implementado.

## Uso local

Para crear el esquema en MySQL:

```sql
SOURCE database/schema.sql;
```

Tambien se puede copiar el contenido de `schema.sql` y ejecutarlo desde MySQL Workbench.

En ambiente `dev` y `demo`, el backend carga datos iniciales desde `DevDataInitializer`. Por eso este script se centra en la estructura de la base de datos.
