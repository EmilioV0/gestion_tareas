package com.gestion.tareas.dto;

import java.time.LocalDateTime;
public record ErrorDTO(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path
) {
}
