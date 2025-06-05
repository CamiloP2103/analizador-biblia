/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package analizadorbiblia;

import modelo.TablaSimbolos;
import modelo.PalabraContador;
import java.util.Scanner;

public class Main {
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
}