package com.gestion.tareas.dto.task;

import java.time.LocalDateTime;
public record TaskByDate(
        Long id,
        String issue,
        LocalDateTime dateLimit,
        String categoryName,
        Boolean expired
) {
}
