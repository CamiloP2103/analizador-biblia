package modelo;

import util.ProcesadorTexto;

public class TablaSimbolos {
    private final Lista<PalabraContador> palabras;

    public TablaSimbolos() {
        palabras = new Lista<>(16); // Capacidad inicial de 16
    }

    public void agregarPalabra(String palabra) {
        palabra = ProcesadorTexto.normalizarPalabra(palabra);
        if (!ProcesadorTexto.esPalabraValida(palabra)) {
            return;
        }

        PalabraContador existente = buscarPalabra(palabra);
        if (existente != null) {
            existente.incrementar();
        } else {
            palabras.agregarAlFinal(new PalabraContador(palabra));
        }
    }

    public PalabraContador buscarPalabra(String palabra) {
        palabra = ProcesadorTexto.normalizarPalabra(palabra);
        if (!ProcesadorTexto.esPalabraValida(palabra)) {
            return null;
        }

        for (PalabraContador pc : palabras) {
            if (pc.getPalabra().equals(palabra)) {
                return pc;
            }
        }
        return null;
    }

    public int obtenerFrecuencia(String palabra) {
        PalabraContador pc = buscarPalabra(palabra);
        return pc != null ? pc.getContador() : 0;
    }

    public void ordenarPalabras() {
        // Implementación de ordenación por inserción
        for (int i = 1; i < palabras.tamano(); i++) {
            PalabraContador actual = (PalabraContador) palabras.obtenerDe(i);
            int j = i - 1;
            
            while (j >= 0 && ((PalabraContador) palabras.obtenerDe(j)).compareTo(actual) > 0) {
                palabras.insertarEn(j + 1, palabras.eliminarDe(j));
                j--;
            }
        }
    }

    public Lista<PalabraContador> getPalabrasOrdenadas() {
        ordenarPalabras();
        return palabras;
    }

    public void procesarArchivo(String rutaArchivo) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                procesarLinea(linea);
            }
        } catch (java.io.IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private void procesarLinea(String linea) {
        String[] palabrasLinea = linea.split("\\s+");
        for (String palabra : palabrasLinea) {
            agregarPalabra(palabra);
        }
    }
}