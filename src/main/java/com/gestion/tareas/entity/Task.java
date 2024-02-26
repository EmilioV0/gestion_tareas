package com.gestion.tareas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
/*
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "Task.updateStateTask",
                procedureName = "update_state_task",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class)
                }
        ),

        @NamedStoredProcedureQuery(name = "Task.updateStateAllTask",
                procedureName = "update_state_all_task"
         )
}) */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String issue;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateLimit;
    private Boolean finished;
    private Boolean expired;

    @Column(name = "id_category")
    private Long idCategory;

    @ManyToOne
    @JoinColumn (
            name = "id_category",
            insertable = false,
            updatable = false,
            referencedColumnName = "id"
    )
    private Category category;

    public Task(String issue, LocalDateTime dateLimit, Boolean finished, Long idCategory) {
        this.issue = issue;
        this.dateLimit = dateLimit;
        this.finished = finished;
        this.idCategory = idCategory;
        this.expired = dateLimit.isBefore( LocalDateTime.now() );
    }
}
