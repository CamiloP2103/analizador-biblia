/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class PalabraContador implements Comparable<PalabraContador> {
    private final String palabra;
    private int contador;

    public PalabraContador(String palabra) {
        this.palabra = palabra;
        this.contador = 1;
    }

    public void incrementar() {
        contador++;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getContador() {
        return contador;
    }

    @Override
    public int compareTo(PalabraContador otro) {
        return this.palabra.compareToIgnoreCase(otro.palabra);
    }

    @Override
    public String toString() {
        return String.format("%-15s: %d", palabra, contador);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PalabraContador that = (PalabraContador) obj;
        return palabra.equalsIgnoreCase(that.palabra);
    }

    @Override
    public int hashCode() {
        return palabra.toLowerCase().hashCode();
    }
}