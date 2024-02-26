package com.gestion.tareas.search;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public record TaskSearchByRange(
        LocalDateTime start,
        LocalDateTime end
) {
    static public TaskSearchByRange getTaskSearchByRange(String searchDate) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        LocalDateTime now = LocalDateTime.now();

        switch (searchDate) {
            case "today":
                startDate = LocalDateTime.of( now.toLocalDate(), LocalTime.MIN);
                endDate = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);
                break;
            case "tomorrow":
                LocalDateTime tomorrow = now.plusDays(1);
                startDate = LocalDateTime.of(tomorrow.toLocalDate(), LocalTime.MIN);
                endDate = LocalDateTime.of(tomorrow.toLocalDate(),LocalTime.MAX);
                break;
            case "this week":
                LocalDateTime monday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
                LocalDateTime sunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                startDate = LocalDateTime.of(monday.toLocalDate(), LocalTime.MIN);
                endDate = LocalDateTime.of(sunday.toLocalDate(), LocalTime.MIN);
                break;
            case "next week":
                LocalDateTime nextMonday = now.with( TemporalAdjusters.next( DayOfWeek.MONDAY )  );
                LocalDateTime nextSunday = nextMonday.with( TemporalAdjusters.next( DayOfWeek.SUNDAY ) );
                startDate = LocalDateTime.of(nextMonday.toLocalDate(), LocalTime.MIN);
                endDate = LocalDateTime.of(nextSunday.toLocalDate(), LocalTime.MIN);
                break;
            case "this month":
                LocalDateTime lastDayOfMonth = now.with( TemporalAdjusters.lastDayOfMonth() );
                LocalDateTime firstDayOfMonth = now.with( TemporalAdjusters.firstDayOfMonth() );
                startDate = LocalDateTime.of(firstDayOfMonth.toLocalDate(), LocalTime.MIN);
                endDate = LocalDateTime.of(lastDayOfMonth.toLocalDate(), LocalTime.MIN);
                break;
            default:
                break;
        }

        return new TaskSearchByRange(startDate, endDate);
    }
}
