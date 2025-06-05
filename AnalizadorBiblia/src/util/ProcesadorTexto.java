
package util;

import java.text.Normalizer;

public class ProcesadorTexto {
    public static String normalizarPalabra(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return "";
        }

        // Convertir a minúsculas
        palabra = palabra.toLowerCase();

        // Eliminar tildes y caracteres diacríticos
        palabra = Normalizer.normalize(palabra, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        // Eliminar todo lo que no sea letra
        palabra = palabra.replaceAll("[^a-z]", "");

        return palabra;
    }

    public static boolean esPalabraValida(String palabra) {
        return palabra != null && !palabra.isEmpty() && palabra.matches(".*[a-z].*");
    }
}