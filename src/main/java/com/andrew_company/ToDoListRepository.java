package com.andrew_company;

import com.andrew_company.entity.Task;
import com.andrew_company.entity.ToDoList;


import java.util.Scanner;

public class ToDoListRepository {


    public void menuOfToDoListRepository(ToDoList toDoList) {
        while (true) {
            int menuDigit = toDoList.writeDigitForMenu();
            switch (menuDigit) {
                case 1:
                    toDoList.showLists();
                    System.out.println();
                    continue;

                case 2:
                    toDoList.addList();
                    continue;

                case 3:
                    toDoList.deleteList();
                    continue;

                case 4:
                    toDoList.chooseToDoList();
                    continue;

                case 5:
                    return;
            }
        }
    }

    public void menuOfToDoList() {
        Task tasks = new Task();
        Scanner sc = new Scanner(System.in);
        String menu = "Choose between 1 and 5: " + "\n" +
                "1 is Add task;" + "\n" +
                "2 is Delete task;" + "\n" +
                "3 is Refactor task;" + "\n" +
                "4 is Mark as \"active\";" + "\n" +
                "5 is Mark as \"complite\"" ;
        System.out.println(menu);
        while (true) {
            int menuDigit = sc.nextInt();
            if (menuDigit >= 1 && menuDigit <= 5) {
                switch (menuDigit) {
                    case 1:
                        tasks.addTask();
                        return;

                    case 2:
                        tasks.deleteTask();
                        return;

                    case 3:
                        tasks.refactorTask();
                        return;

                    case 4:
                        tasks.markAsActive();
                        return;

                    case 5:
                        tasks.markAsComplite();
                        return;
                }

            } else {
                System.out.println(menu);
            }
        }
    }
}

