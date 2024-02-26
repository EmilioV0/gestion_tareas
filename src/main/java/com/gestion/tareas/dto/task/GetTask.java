package com.gestion.tareas.dto.task;

import java.time.LocalDateTime;

public record GetTask(
        Long id,
        String issue,
        Boolean finished,
        LocalDateTime dateLimit,
        Long categoryId
) {

}
