package ru.otus.hw.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {
    private Long id;
    private String name;
    private Status status;
}
