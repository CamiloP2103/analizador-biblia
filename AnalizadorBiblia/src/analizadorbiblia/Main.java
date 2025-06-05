/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package analizadorbiblia;

import java.io.IOException;
import modelo.TablaSimbolos;
import modelo.PalabraContador;
import java.util.Scanner;
import procesadortexto.FileManager;
import procesadortexto.ProcesadorTexto;

public class Main {
    private static final String RUTA_BIBLIA = "\textos\Bible.txt";
    private static String contenidoCompleto;
    private static String contenidoLimpio;
    private static String[] parrafos;
    private static ProcesadorTexto procesador = new ProcesadorTexto();
    public static void main(String[] args) {
        TablaSimbolos tabla = new TablaSimbolos();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("ANALIZADOR DE TEXTO - BIBLIA");
        System.out.print("Ingrese la ruta del archivo: ");
        String rutaArchivo = scanner.nextLine();
        
        System.out.println("\nProcesando archivo...");
        tabla.procesarArchivo(rutaArchivo);
        System.out.println("Procesamiento completado.\n");
        
        int opcion;
        do {
            mostrarMenu();
            opcion = obtenerOpcion(scanner);
            
            switch (opcion) {
                case 1:
                    mostrarPalabrasOrdenadas(tabla);
                    break;
                case 2:
                    buscarFrecuenciaPalabra(tabla, scanner);
                    break;
                case 3:
                    buscarReferenciaPorClave(tabla, scanner);
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("1. Mostrar todas las palabras ordenadas (A-Z)");
        System.out.println("2. Buscar frecuencia de una palabra");
        System.out.println("3. Buscar referencia por clave");
        System.out.println("4. Salir");
    }
    
    private static int obtenerOpcion(Scanner scanner) {
        System.out.print("\nSeleccione una opción: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor ingrese un número.");
            scanner.next();
        }
        return scanner.nextInt();
    }
    
    private static void mostrarPalabrasOrdenadas(TablaSimbolos tabla) {
        System.out.println("\nPALABRAS ORDENADAS:");
        for (PalabraContador pc : tabla.getPalabrasOrdenadas()) {
            System.out.println(pc);
        }
    }
    
    private static void buscarFrecuenciaPalabra(TablaSimbolos tabla, Scanner scanner) {
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Ingrese la palabra a buscar: ");
        String palabra = scanner.nextLine();
        int frecuencia = tabla.obtenerFrecuencia(palabra);
        System.out.printf("La palabra '%s' aparece %d veces.%n", palabra, frecuencia);
    }
    
    private static void buscarReferenciaPorClave(TablaSimbolos tabla, Scanner scanner) {
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Ingrese la palabra clave: ");
        String clave = scanner.nextLine();
        
        PalabraContador referencia = tabla.buscarPalabra(clave);
        
        if (referencia != null) {
            System.out.println("Referencia encontrada:");
            System.out.println("Palabra: " + referencia.getPalabra());
            System.out.println("Repeticiones: " + referencia.getContador());
        } else {
            System.out.println("La clave '" + clave + "' no fue encontrada.");
        }
    }
    private static void buscarPorLetra(Scanner scanner) {
        System.out.print("\nIngrese la letra a buscar: ");
        char letra = scanner.nextLine().toLowerCase().charAt(0);
        int total = procesador.contarPalabrasConLetra(contenidoLimpio.split(" "), letra);
        System.out.println("\nTotal de palabras que empiezan con '" + letra + "': " + total);
    }

    private static void mostrarEstadisticasGenerales(int totalGeneral) {
        System.out.println("\nESTADiSTICAS GENERALES");
        System.out.println("Total de palabras: " + contenidoLimpio.split(" ").length);
        System.out.println("Total de parrafos: " + parrafos.length);
    }

    private static void contarPalabrasParrafoEspecifico(Scanner scanner) {
        System.out.println("\nSeleccione un parrafo (1-" + parrafos.length + "): ");
        int numParrafo = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        if (numParrafo < 1 || numParrafo > parrafos.length) {
            System.out.println("Numero de parrafo invalido!");
            return;
        }
        
        String parrafoLimpio = procesador.limpiarTexto(parrafos[numParrafo-1]);
        int totalPalabras = parrafoLimpio.split(" ").length;
        
        System.out.println("\nParrafo " + numParrafo + ":");
        System.out.println("Total de palabras: " + totalPalabras);
        
        // Mostrar un extracto del parrafo (primeras 50 palabras)
        String[] palabras = parrafoLimpio.split(" ");
        System.out.print("Extracto: ");
        for (int i = 0; i < Math.min(50, palabras.length); i++) {
            System.out.print(palabras[i] + " ");
        }
        if (palabras.length > 50) System.out.println("...");
        else System.out.println();
    }

    private static void mostrarTodosParrafosConConteo() {
        System.out.println("\nCONTEO DE PALABRAS POR PaRRAFO");
        for (int i = 0; i < parrafos.length; i++) {
            String parrafoLimpio = procesador.limpiarTexto(parrafos[i]);
            int totalPalabras = parrafoLimpio.split(" ").length;
            System.out.printf("Parrafo %4d: %6d palabras%n", (i+1), totalPalabras);
        }
    }

    private static void salirDelPrograma() {
        System.out.println("Guardando archivo limpio...");
        try {
            FileManager.guardarArchivoLimpio(RUTA_BIBLIA, contenidoLimpio);
            System.out.println("Archivo guardado en: textos/Bible.limpio.txt");
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
        System.out.println("Saliendo del programa...");
    }
    
    private static void contarPalabrasPorLetraEnParrafo(Scanner scanner) {
        System.out.println("\nSeleccione un parrafo (1-" + parrafos.length + "): ");
        int numParrafo = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (numParrafo < 1 || numParrafo > parrafos.length) {
            System.out.println("Numero de parrafo invalido!");
            return;
        }

        System.out.print("Ingrese la letra a buscar: ");
        char letra = scanner.nextLine().toLowerCase().charAt(0);

        int cantidad = procesador.contarPalabrasConLetraEnParrafo(parrafos[numParrafo-1], letra);
        int totalPalabras = procesador.limpiarTexto(parrafos[numParrafo-1]).split(" ").length;

        System.out.println("\nResultados para el Parrafo " + numParrafo + ":");
        System.out.println("Total de palabras: " + totalPalabras);
        System.out.println("Palabras que empiezan con '" + letra + "': " + cantidad);
        System.out.printf("Porcentaje: %.2f%%\n", (cantidad * 100.0 / totalPalabras));
    }
}