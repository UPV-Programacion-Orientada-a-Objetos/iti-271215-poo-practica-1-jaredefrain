package edu.upvictoria.fpoo.Exceptions;

import java.io.FileNotFoundException;

public class ArchivoNoEncontrado extends FileNotFoundException {
    public ArchivoNoEncontrado(String mensaje){
        super(mensaje);
    }
}
