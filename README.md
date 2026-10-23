# Sistema de Gestión de Productos (Java, consola)

Aplicación de consola para registrar, consultar, actualizar y eliminar productos de un pequeño establecimiento comercial (CRUD).

> **Sobre la persistencia:** por indicación del profesor no se usa base de datos. Los productos se guardan en memoria (un `Map`), igual que en las guías anteriores de la API. Los datos se conservan mientras el programa está en ejecución.

## Ejecución automática en GitHub

![Ejecutar demostración](https://github.com/USUARIO/REPOSITORIO/actions/workflows/ejecutar.yml/badge.svg)

Cada vez que se sube código al repositorio, GitHub Actions compila el proyecto y lo ejecuta solo, usando las respuestas de `demo/entrada_demo.txt`. Para ver el resultado: pestaña **Actions** → última ejecución → paso **Ejecutar la demostración automática** (o el resumen al final de la ejecución). También se puede lanzar manualmente con **Run workflow**.

## Estructura

```
GestionProductos/
├── .github/workflows/ejecutar.yml  # Ejecución automática en GitHub
├── demo/entrada_demo.txt           # Respuestas de la demostración automática
├── src/
│   ├── Producto.java               # Modelo + validaciones (record)
│   ├── ProductoRepositorio.java    # Acceso a datos (CRUD en memoria)
│   └── Main.java                   # Menú de consola (presentación)
├── README.md
└── .gitignore
```

## Requisitos

- Java 17 o superior (JDK).
- Cualquier IDE (IntelliJ IDEA, Eclipse, NetBeans) o solo la terminal.

## Cómo ejecutarlo

**Desde la terminal** (dentro de la carpeta del proyecto):

```
javac -d out src/*.java
java -cp out Main
```

**Desde un IDE:** crear un proyecto Java, copiar los tres archivos de `src/` y ejecutar `Main`.

Para ver la demostración automática en tu equipo: `java -cp out Main --demo < demo/entrada_demo.txt`

> En Windows, si las tildes se ven raras en la consola, ejecute antes `chcp 65001`.

## Funciones

| Opción | Acción | Requerimiento |
|---|---|---|
| 1 | Registrar producto | RF1 |
| 2 | Mostrar todos los productos | RF2 |
| 3 | Buscar por código | RF2 |
| 4 | Buscar por nombre | RF2 |
| 5 | Actualizar precio, descripción o cantidad (Enter = dejar igual) | RF3 |
| 6 | Eliminar producto con confirmación | RF4 |
| 0 | Salir | — |

## Validaciones

- Código, nombre y descripción no pueden estar vacíos.
- El precio debe ser un número no negativo (acepta `2.5` o `2,5`).
- La cantidad debe ser un número entero no negativo.
- No se permiten códigos repetidos.
- Buscar, actualizar o eliminar un código inexistente muestra un mensaje claro.
