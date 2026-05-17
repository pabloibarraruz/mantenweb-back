# Uso con Docker

Este archivo resume como levantar el backend de MantenWeb usando Docker. No es obligatorio para ejecutar el proyecto, pero sirve si se quiere probar el backend junto con MySQL local.

## Backend + MySQL local

Desde la carpeta `mantenweb-back`:

```bash
docker compose --env-file .env.local up --build
```

Esto levanta:

- `mysql` en `localhost:3306`
- `backend` en `localhost:8080`

El archivo `docker-compose.yml` esta pensado para desarrollo local y usa:

- perfil Spring `dev`
- MySQL local `mantenweb`
- usuario `mantenweb`
- password definido en `.env.local`

## Backend apuntando a produccion

Desde la carpeta `mantenweb-back`:

```bash
docker compose --env-file .env.prod -f docker-compose.prod.yml up --build
```

Este compose usa:

- perfil Spring `prod`
- MySQL de Railway definido en `.env.prod`
- JWT definido en `.env.prod`

## Variables de entorno

El backend lee la configuracion desde variables de entorno:

- `SPRING_PROFILES_ACTIVE`
- `SERVER_PORT`
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_JPA_HIBERNATE_DDL_AUTO`
- `SPRING_JPA_SHOW_SQL`
- `APP_JWT_SECRET`
- `APP_JWT_EXPIRATION`
- `APP_FRONTEND_RESET_URL`
- `APP_MAIL_FROM`

## Ambientes usados

Para desarrollo local usa `.env.local`.

Para producción usa `.env.prod`.

VS Code quedó con dos configuraciones:

- `Spring Boot Local`
- `Spring Boot Prod`

## Formato de conexion MySQL

Spring Boot espera la URL con formato `jdbc:mysql://...`.

Ejemplo de formato para variables:

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://host:puerto/base?useSSL=true&requireSSL=true&serverTimezone=UTC"
export SPRING_DATASOURCE_USERNAME="usuario"
export SPRING_DATASOURCE_PASSWORD="<password>"
export APP_JWT_SECRET="<secreto-jwt-de-al-menos-32-bytes>"
export APP_JWT_EXPIRATION="86400000"
```

Los valores reales deben quedar solamente en variables de entorno o archivos `.env` locales no versionados.

## Variables usadas en Render

En Render deja estas variables de entorno:

```env
SPRING_PROFILES_ACTIVE=prod
PORT=10000
SPRING_DATASOURCE_URL=jdbc:mysql://host:puerto/base?useSSL=true&requireSSL=true&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=usuario
SPRING_DATASOURCE_PASSWORD=<password>
APP_JWT_SECRET=<secreto-jwt-de-al-menos-32-bytes>
APP_JWT_EXPIRATION=86400000
```

## Ejecutar solo la imagen del backend

```bash
docker build -t mantenweb-backend .
docker run --rm -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/mantenweb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=<password-local> \
  mantenweb-backend
```
