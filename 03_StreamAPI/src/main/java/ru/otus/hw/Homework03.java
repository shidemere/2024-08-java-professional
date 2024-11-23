package ru.otus.hw;

import ru.otus.hw.model.Status;
import ru.otus.hw.model.Task;
import ru.otus.hw.service.TaskProvider;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Homework03 {
    public static void main(String[] args) {
        List<Task> tasks = TaskProvider.getTasks();

        // Получение списка задач по выбранному статусу.
        // В данном случае это будет Completed

        List<Task> tasksByStatus = tasks.stream()
                .filter(task -> task.getStatus() == Status.COMPLETED)
                .toList();


        // Проверка наличия задачи с указанным ID;
        // В данном случае это будет 47

        Optional<Task> findById = tasks.stream()
                .filter(task -> task.getId() == 47)
                .findFirst();


        // Получение списка задач в отсортирванном по статусу виде
        // Порядок: TODO, INPROGRESS, COMPLETED, FAILED

        List<Task> sortedByStatus = tasks.stream()
                .sorted(Comparator.comparingInt(t -> t.getStatus().ordinal()))
                .toList();

        // Подсчет количества задач по определенному статусу.

        long countOfFailed = tasks.stream()
                .filter(task -> task.getStatus() == Status.FAILED)
                .count();
    }
}