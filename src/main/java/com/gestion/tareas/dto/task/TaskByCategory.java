package com.gestion.tareas.dto.task;

import java.time.LocalDateTime;

public record TaskByCategory(
        Long id,
        String issue,
        LocalDateTime dateLimit,
        Boolean expired
) {
}
