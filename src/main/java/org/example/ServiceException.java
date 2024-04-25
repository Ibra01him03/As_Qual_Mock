package org.example;



// Déclaration de la classe ServiceException
public class ServiceException extends Exception {
    // Constructeur avec un message d'erreur
    public ServiceException(String message) {
        super(message);
    }

    // Constructeur avec un message d'erreur et une cause
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

