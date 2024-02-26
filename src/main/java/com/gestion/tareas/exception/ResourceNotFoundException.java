package com.gestion.tareas.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String className, long id) {
        super("Resource " + className + " with id " + id + " not found ");
    }

    public ResourceNotFoundException(String className, String categoryName) {
        super("Resource " + className + " with name " + categoryName + " not found ");
    }
}
