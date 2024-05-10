package edu.upvictoria.fpoo;

import edu.upvictoria.fpoo.Exceptions.ErrorSintaxis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class App {
    public static void main(String[] args) throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        String op;
        String use;
        boolean continuar = true;

        do {
            System.out.print("BASE DE DATOS:");
            use = String.valueOf(lector.readLine());
            if (!use.startsWith("USE $TABLE$;")) {
                System.out.println("INVALIDO");
            }
        } while ((!use.startsWith("USE $TABLE$;")));

        do {
            try {
                Sentencias.menu(lector);
                op = lector.readLine();
                if (op.equalsIgnoreCase("exit;")){
                    continuar = false; // Establecer la variable a false para salir del bucle
                }
            } catch (ErrorSintaxis e) {
                System.out.println("Error de sintaxis: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        } while (continuar); // La condición de salida ahora depende de la variable 'continuar'
    }
}
