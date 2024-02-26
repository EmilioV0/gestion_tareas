package com.gestion.tareas.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategory(
        @NotBlank
        String name
) {
}
