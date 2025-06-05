/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package procesadortexto;

import java.io.IOException;
import java.util.Scanner;

/**
 * La clase Main contiene el punto de entrada del programa y maneja la interfaz de usuario.
 * Proporciona un menú interactivo para analizar y procesar textos.
 * 
 * @author Rodrigo
 * @author Juan
 * @author Camilo
 */
public class MainCamilo {
    private static final String RUTA_BIBLIA = "\textos\Bible.txt";
    private static String contenidoCompleto;
    private static String contenidoLimpio;
    private static String[] parrafos;
    private static ProcesadorTexto procesador = new ProcesadorTexto();

    public static void main(String[] args) {
        try {
            // Cargar y procesar el archivo una sola vez al inicio
            contenidoCompleto = FileManager.leerArchivo(RUTA_BIBLIA);
            contenidoLimpio = procesador.limpiarTexto(contenidoCompleto);
            parrafos = contenidoCompleto.split("\\r?\\n\\r?\\n");
            
            mostrarMenu();
            
        } catch (IOException e) {
            System.err.println("Error al cargar la Biblia: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        int totalGeneral = 0;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Buscar palabras por letra inicial (texto completo)");
            System.out.println("2. Mostrar estadisticas generales");
            System.out.println("3. Contar palabras en un parrafo especifico");
            System.out.println("4. Mostrar todos los parrafos con conteo de palabras");
            System.out.println("5. Buscar palabras por letra en parrafo especifico");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    buscarPorLetra(scanner, totalGeneral);
                    break;
                    
                case 2:
                    mostrarEstadisticasGenerales(totalGeneral);
                    break;
                    
                case 3:
                    contarPalabrasParrafoEspecifico(scanner);
                    break;
                    
                case 4:
                    mostrarTodosParrafosConConteo();
                    break;
                    
                case 5:
                    contarPalabrasPorLetraEnParrafo(scanner);
                    break;
                
                case 6:
                    salirDelPrograma();
                    break;
                    
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (opcion != 6);
        
        scanner.close();
    }

    private static void buscarPorLetra(Scanner scanner, int totalGeneral) {
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