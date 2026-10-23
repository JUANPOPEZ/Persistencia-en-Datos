
import java.util.List;
import java.util.Scanner;

/**
 * PRESENTACIÓN: menú de consola del Sistema de Gestión de Productos.
 *
 * Aquí solo se pide información al usuario, se llama al repositorio y se
 * muestran los resultados o mensajes de error.
 */
public class Main {

    private static final ProductoRepositorio repositorio = new ProductoRepositorio();
    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> mostrarProductos(repositorio.listar());
                case 3 -> buscarPorCodigo();
                case 4 -> buscarPorNombre();
                case 5 -> actualizarProducto();
                case 6 -> eliminarProducto();
                case 0 -> System.out.println("Hasta pronto.");
                default -> System.out.println("Opción no válida. Elija un número del menú.");
            }
        } while (opcion != 0);

        teclado.close(); // Se libera el recurso de entrada al terminar.
    }

    // ---------- Opciones del menú ----------

    // RF1: registrar un producto.
    private static void registrarProducto() {
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        String descripcion = leerTexto("Descripción: ");
        String precio = leerTexto("Precio: ");
        String cantidad = leerTexto("Cantidad disponible: ");

        try {
            Producto producto = new Producto(codigo, nombre, descripcion, aDecimal(precio), Integer.parseInt(cantidad));
            repositorio.registrar(producto);
            System.out.println("Producto registrado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: el precio debe ser un número y la cantidad un número entero.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // RF2: buscar por código.
    private static void buscarPorCodigo() {
        Producto producto = repositorio.buscarPorCodigo(leerTexto("Código a buscar: "));
        if (producto == null) {
            System.out.println("No existe un producto con ese código.");
        } else {
            mostrarProductos(List.of(producto));
        }
    }

    // RF2: buscar por nombre.
    private static void buscarPorNombre() {
        mostrarProductos(repositorio.buscarPorNombre(leerTexto("Nombre (o parte del nombre): ")));
    }

    // RF3: actualizar precio, descripción o cantidad. Enter = dejar el valor actual.
    private static void actualizarProducto() {
        Producto actual = repositorio.buscarPorCodigo(leerTexto("Código del producto a actualizar: "));
        if (actual == null) {
            System.out.println("No existe un producto con ese código.");
            return;
        }

        String descripcion = leerTexto("Nueva descripción (Enter para dejarla igual): ");
        String precio = leerTexto("Nuevo precio (Enter para dejarlo igual): ");
        String cantidad = leerTexto("Nueva cantidad (Enter para dejarla igual): ");

        try {
            // Se crea un producto con los valores nuevos; si algo es inválido, falla antes de guardar.
            Producto actualizado = new Producto(
                    actual.codigo(),
                    actual.nombre(),
                    descripcion.isEmpty() ? actual.descripcion() : descripcion,
                    precio.isEmpty() ? actual.precio() : aDecimal(precio),
                    cantidad.isEmpty() ? actual.cantidad() : Integer.parseInt(cantidad));
            repositorio.actualizar(actualizado);
            System.out.println("Producto actualizado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: el precio debe ser un número y la cantidad un número entero.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // RF4: eliminar un producto pidiendo confirmación.
    private static void eliminarProducto() {
        String codigo = leerTexto("Código del producto a eliminar: ");
        if (repositorio.buscarPorCodigo(codigo) == null) {
            System.out.println("No existe un producto con ese código.");
            return;
        }

        String respuesta = leerTexto("¿Seguro que desea eliminarlo? (s/n): ");
        if (respuesta.equalsIgnoreCase("s")) {
            repositorio.eliminar(codigo);
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    // ---------- Métodos de apoyo ----------

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE GESTIÓN DE PRODUCTOS ===");
        System.out.println("1. Registrar producto");
        System.out.println("2. Mostrar todos los productos");
        System.out.println("3. Buscar por código");
        System.out.println("4. Buscar por nombre");
        System.out.println("5. Actualizar producto");
        System.out.println("6. Eliminar producto");
        System.out.println("0. Salir");
    }

    private static void mostrarProductos(List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
            return;
        }
        System.out.printf("%-8s %-18s %10s %9s  %s%n", "Código", "Nombre", "Precio", "Cantidad", "Descripción");
        for (Producto p : productos) {
            System.out.printf("%-8s %-18s %10.2f %9d  %s%n",
                    p.codigo(), p.nombre(), p.precio(), p.cantidad(), p.descripcion());
        }
    }

    // Lee una línea de texto. Si la entrada se cierra, devuelve texto vacío.
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.hasNextLine() ? teclado.nextLine().trim() : "";
    }

    // Lee la opción del menú; si no es un número devuelve -1 (opción no válida).
    private static int leerOpcion() {
        System.out.print("Elija una opción: ");
        if (!teclado.hasNextLine()) {
            return 0; // Sin más entrada: se cierra el programa.
        }
        try {
            return Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Convierte texto a número decimal aceptando coma o punto (ej. 2.5 o 2,5).
    private static double aDecimal(String texto) {
        return Double.parseDouble(texto.replace(',', '.'));
    }
}
