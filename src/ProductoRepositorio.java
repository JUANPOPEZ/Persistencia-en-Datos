
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ACCESO A DATOS: guarda y administra los productos.
 *
 * Por indicación del profesor no se usa base de datos: los productos se
 * almacenan en memoria dentro de un Map (código -> producto), igual que en
 * las guías anteriores de la API. LinkedHashMap conserva el orden en que se
 * registraron los productos.
 *
 * Esta clase solo guarda y busca datos; no pregunta nada al usuario ni
 * imprime mensajes (eso lo hace Main).
 */
public class ProductoRepositorio {

    private final Map<String, Producto> productos = new LinkedHashMap<>();

    // CREATE: agrega un producto nuevo (no permite códigos repetidos).
    public void registrar(Producto producto) {
        if (productos.containsKey(producto.codigo())) {
            throw new IllegalArgumentException("Ya existe un producto con el código " + producto.codigo() + ".");
        }
        productos.put(producto.codigo(), producto);
    }

    // READ: devuelve todos los productos.
    public List<Producto> listar() {
        return new ArrayList<>(productos.values());
    }

    // READ: busca un producto por su código (devuelve null si no existe).
    public Producto buscarPorCodigo(String codigo) {
        return productos.get(codigo.trim());
    }

    // READ: busca productos cuyo nombre contenga el texto (sin importar mayúsculas).
    public List<Producto> buscarPorNombre(String texto) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : productos.values()) {
            if (p.nombre().toLowerCase().contains(texto.trim().toLowerCase())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    // UPDATE: reemplaza un producto existente. Devuelve false si no existe.
    public boolean actualizar(Producto producto) {
        if (!productos.containsKey(producto.codigo())) {
            return false;
        }
        productos.put(producto.codigo(), producto);
        return true;
    }

    // DELETE: elimina un producto por su código. Devuelve false si no existe.
    public boolean eliminar(String codigo) {
        return productos.remove(codigo.trim()) != null;
    }
}
