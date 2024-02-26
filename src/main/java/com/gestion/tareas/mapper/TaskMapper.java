package com.gestion.tareas.mapper;

import com.gestion.tareas.dto.task.CreateTask;
import com.gestion.tareas.dto.task.GetTask;
import com.gestion.tareas.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {
//    @Mappings({
//            @Mapping(target = "category", ignore = true),
//            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "expired", ignore = true),
//    })
//    public abstract Task toTask(CreateTask createTask);

    public Task toTask(CreateTask createTask) {
        return new Task(
                createTask.issue(),
                createTask.dateLimit(),
                createTask.finished(),
                createTask.idCategory()
        );
    }

    @Mapping(source = "idCategory", target = "categoryId")
    public abstract GetTask toGetTask(Task task);

    public void fromCreateTaskToTask(CreateTask from, Task to) {
        to.setFinished( from.finished() );
        to.setIssue( from.issue() );
        to.setIdCategory( from.idCategory() );
        to.setDateLimit( from.dateLimit() );
    }
}
