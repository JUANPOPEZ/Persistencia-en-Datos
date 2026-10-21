/**
 * MODELO: representa un producto de la tienda.
 *
 * Es un "record" de Java (disponible desde Java 16): una clase corta pensada
 * solo para guardar datos. Java crea automáticamente el constructor, los
 * métodos de lectura (codigo(), nombre(), precio()...) y los datos no se
 * pueden cambiar una vez creado el producto.
 *
 * Todas las reglas de validación están aquí, en un solo lugar: si los datos
 * no son correctos, el producto simplemente no se puede crear.
 */
public record Producto(String codigo, String nombre, String descripcion,
                       double precio, int cantidad) {

    // Constructor compacto: se ejecuta cada vez que se crea un Producto.
    public Producto {
        codigo = textoObligatorio(codigo, "El código");
        nombre = textoObligatorio(nombre, "El nombre");
        descripcion = textoObligatorio(descripcion, "La descripción");

        if (Double.isNaN(precio) || Double.isInfinite(precio) || precio < 0) {
            throw new IllegalArgumentException("El precio debe ser un número válido y no negativo.");
        }
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
    }

    // Verifica que un texto no esté vacío y le quita los espacios sobrantes.
    private static String textoObligatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
        return valor.trim();
    }
}
