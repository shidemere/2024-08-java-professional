package ru.otus.hw.service;

import ru.otus.hw.model.Status;
import ru.otus.hw.model.Task;

import java.util.List;

public class TaskProvider {
    public static List<Task> getTasks() {
        return List.of(
                new Task(1L, "Уговорить кота пить чай со мной", Status.TODO),
                new Task(2L, "Провести переговоры с холодильником", Status.INPROGRESS),
                new Task(3L, "Поймать удачу за хвост, желательно без травм", Status.FAILED),
                new Task(4L, "Найти единорога в парке", Status.TODO),
                new Task(5L, "Изобрести самоделящийся торт", Status.COMPLETED),
                new Task(6L, "Договориться с будильником о перемирии", Status.INPROGRESS),
                new Task(7L, "Выучить язык чаек, чтобы разговаривать на пляже", Status.TODO),
                new Task(8L, "Прыгнуть выше головы на батуте", Status.COMPLETED),
                new Task(9L, "Вести переговоры с лампой о приглушенном свете", Status.FAILED),
                new Task(10L, "Сделать селфи с енотом", Status.INPROGRESS),
                new Task(11L, "Сочинить симфонию для пылесоса", Status.TODO),
                new Task(12L, "Засыпать во время раздумий", Status.COMPLETED),
                new Task(13L, "Прокатиться на облаке (с разрешением облака)", Status.FAILED),
                new Task(14L, "Найти смысл жизни в кексе", Status.INPROGRESS),
                new Task(15L, "Вырастить дерево пиццы", Status.TODO),
                new Task(16L, "Поболтать с мебелью о смысле бытия", Status.FAILED),
                new Task(17L, "Побыть день королем дивана", Status.COMPLETED),
                new Task(18L, "Провести магический ритуал для исчезновения носков", Status.TODO),
                new Task(19L, "Доставить письмо Деду Морозу до июня", Status.INPROGRESS),
                new Task(20L, "Выучить песню чаек и удивить соседей", Status.FAILED),
                new Task(21L, "Нарисовать портрет муравья для муравейника", Status.COMPLETED),
                new Task(22L, "Создать коллекцию модной одежды для лягушек", Status.TODO),
                new Task(23L, "Сыграть в шахматы с кактусом", Status.INPROGRESS),
                new Task(24L, "Перевести книги на язык тапок", Status.FAILED),
                new Task(25L, "Превратить скучный вечер в цирковое шоу", Status.COMPLETED),
                new Task(26L, "Сфотографировать радугу с ухмылкой", Status.TODO),
                new Task(27L, "Вырезать снежинки из спагетти", Status.INPROGRESS),
                new Task(28L, "Приготовить ужин для гусениц", Status.FAILED),
                new Task(29L, "Провести экскурсию по шкафу", Status.COMPLETED),
                new Task(30L, "Выучить кактусовые шутки", Status.TODO),
                new Task(31L, "Съесть мороженое, не облизывая ложку", Status.INPROGRESS),
                new Task(32L, "Нарисовать усы солнцу", Status.FAILED),
                new Task(33L, "Потанцевать с тенями в лунную ночь", Status.COMPLETED),
                new Task(34L, "Устроить вечеринку для одуванчиков", Status.TODO),
                new Task(35L, "Научиться хрустеть пальцами громче, чем попкорн", Status.FAILED),
                new Task(36L, "Вырастить кактус-гитарист", Status.COMPLETED),
                new Task(37L, "Сыграть симфонию на воздушных шариках", Status.INPROGRESS),
                new Task(38L, "Сфотографировать сосиску в прыжке", Status.TODO),
                new Task(39L, "Подружиться с ветром и не улететь", Status.FAILED),
                new Task(40L, "Нарисовать портрет снеговика в июле", Status.COMPLETED),
                new Task(41L, "Поймать звезду в ладони (и не обжечься)", Status.TODO),
                new Task(42L, "Устроить пикник в воображаемом лесу", Status.INPROGRESS),
                new Task(43L, "Поспорить с дождем, кто мокрее", Status.FAILED),
                new Task(44L, "Научить кота танцевать вальс", Status.COMPLETED),
                new Task(45L, "Выгулять книжку по городу", Status.TODO),
                new Task(46L, "Найти заколдованную пуговицу на пляже", Status.FAILED),
                new Task(47L, "Сочинить гимн для муравьиной армии", Status.INPROGRESS),
                new Task(48L, "Поговорить с камнями о будущем", Status.COMPLETED),
                new Task(49L, "Научиться смотреть на звезды вверх ногами", Status.TODO),
                new Task(50L, "Спеть сирену в автобусе", Status.INPROGRESS)
        );
    }
}
