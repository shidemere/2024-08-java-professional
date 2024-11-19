package ru.otus.hw.model;

import lombok.Getter;

@Getter
public enum Status {
    TODO ("To Do"),
    INPROGRESS ("In Progress"),
    COMPLETED ("Completed"),
    FAILED ("Failed");

    private final String title;

    Status(String title) {
        this.title = title;
    }
}
