package edu.upvictoria.fpoo;

import edu.upvictoria.fpoo.Exceptions.ErrorSintaxis;
import edu.upvictoria.fpoo.Exceptions.ArchivoNoEncontrado;

import java.io.*;
import java.util.ArrayList;

public class Sentencias {
    static String path = "/home/tristan/Escritorio/iti-271215-poo-practica-1-jaredefrain/dataBaseManager/src/main/TABLE";

    static BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

    public static void menu(BufferedReader lector) throws IOException {
        //SentenciasAcciones.listaComandos();
        System.out.println("Ingrese un comandod");
        System.out.print("$PATH$:");
        String cmnd = obtenerComando(lector);
        //System.out.println("Comando ingresado: " + cmnd);

        if (cmnd.startsWith("DROP TABLE")) {
            if (!cmnd.startsWith("DROP TABLE")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
            String tableName = cmnd.substring(11).trim();
            String nombre = tableName.substring(0, tableName.length());

            SentenciasAcciones.dropTable(nombre);
        } else if (cmnd.startsWith("CREATE TABLE")) {
            crearTabla(cmnd);
            if (!cmnd.startsWith("CREATE TABLE")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
        } else if (cmnd.startsWith("SHOW TABLES")) {
            if (!cmnd.startsWith("SHOW TABLES")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
            SentenciasAcciones.showTables();
        } else if (cmnd.startsWith("INSERT")) {
            if (!cmnd.startsWith("INSERT INTO")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
            insertDatos(cmnd);
        } else if (cmnd.startsWith("SELECT")) {
            if (!cmnd.startsWith("SELECT * FROM")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
            selectDatos(cmnd);
        } else if (cmnd.startsWith("exit")) {
            System.exit(0);
        }

    }

    public static String obtenerComando(BufferedReader lector) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = lector.readLine()) != null) {
            sb.append(line.trim());
            if (line.trim().endsWith(";")) {
                break;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static void crearTabla(String cmnd) {
        String[] palabras = cmnd.split(" ");
        String tableName = palabras[2];
        System.out.println("Nombre de la tabla: " + tableName);

        String dentro = cmnd.substring(cmnd.indexOf("(") + 1, cmnd.lastIndexOf(")")).trim();
        String[] campos = dentro.split(",");


        // si no existe la tabla
        File archivo = new File(path + "/" + tableName + ".csv");
        if (!archivo.exists()) {
            SentenciasAcciones.createTable(tableName, campos);
        }
    }

    public static void insertDatos(String cmnd) {
        try {
            String nombreTabla = cmnd.substring(12, cmnd.indexOf("(")).trim();
            String columnas = cmnd.substring(cmnd.indexOf("(") + 1, cmnd.indexOf(")"));
            String valores = cmnd.substring(cmnd.lastIndexOf("(") + 1, cmnd.lastIndexOf(")"));

            String[] aColumnas = columnas.split(",");
            String[] aValores = valores.split(",");

            ArrayList<String> nombreColumnas = new ArrayList<>();
            ArrayList<String> valoresColumnas = new ArrayList<>();

            for (String columna : aColumnas) {
                nombreColumnas.add(columna.trim());
            }

            for (String dato : aValores) {
                valoresColumnas.add(dato.trim());
            }

            SentenciasAcciones.insertInto(nombreTabla, valoresColumnas);
        } catch (ArchivoNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectDatos(String cmnd) throws ArchivoNoEncontrado {
        try {
            String[] partes = cmnd.split("FROM");
            if (partes.length == 2) {
                String selectClausula = partes[0].substring(7).trim();
                String[] subPartes = partes[1].trim().split("WHERE");

                String tabla = subPartes[0].trim();
                String condition = (subPartes.length == 2) ? subPartes[1].trim() : null;

                String[] columnas = selectClausula.equals("*") ? new String[]{"*"} : selectClausula.split(",");

                SentenciasAcciones.selectFrom(tabla, columnas, condition);
            } else if (partes.length == 1) {
                // Si no hay cláusula WHERE
                String selectClausula = partes[0].substring(7).trim();
                String tabla = selectClausula.trim();
                String[] columnas = selectClausula.equals("*") ? new String[]{"*"} : selectClausula.split(",");

                SentenciasAcciones.selectFrom(tabla, columnas, null);
            } else {
                throw new ErrorSintaxis("ERROR SINTAXIS");
            }
        } catch (ArchivoNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }

}
