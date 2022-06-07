package com.andrew_company.entity;

import com.andrew_company.DataBaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Task {
    private int id;
    private String task;
    private String condition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


    public ArrayList<Task> getAllTasks() {
        Task tasks = new Task();
        ArrayList<Task> listOfTasks = new ArrayList<>();
        if (tasks.checkIsEmpty()) {
        } else {
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM tasks ORDER BY list_id");
                while (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getInt("list_id"));
                    task.setTask(rs.getString("task"));
                    task.setCondition(rs.getString("condition"));
                    listOfTasks.add(task);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listOfTasks;
    }

    public int writeNumberOfId() {
        Task task = new Task();
        Scanner sc = new Scanner(System.in);
        int digit;
        while (true) {
            System.out.println("Write number of id: ");
            digit = sc.nextInt();
            if (task.checkIntervalOfTaskID(digit)) {
                return digit;
            } else {
                System.out.println("We do not have this number of id, Please try again!");
            }
        }
    }

    public boolean checkIntervalOfTaskID(int digit) {
        ToDoList toDoList = new ToDoList();
        boolean result = true;
        if (digit >= 1 && digit <= toDoList.findMaxId()) {
        } else {
            result = false;
        }
        return result;
    }


    public void showTasks() {
        Task task = new Task();
        ArrayList<Task> listOfTasks = task.getAllTasks();
        for (Task tasks : listOfTasks) {
            System.out.println(tasks.getTask() + " " + tasks.getCondition());
        }
    }

    public boolean checkIsEmpty() { // jest opcja przerobiania
        boolean result = false;
        Connection con;
        try {
            con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                    DataBaseConfig.PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks;");
            if (!rs.next()) {
                System.out.println("Tasks is empty! Please add task");
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Task addTask() {
        Scanner sc = new Scanner(System.in);
        Task task = new Task();
        int id = task.writeNumberOfId();
        System.out.println("Write task to add: ");
        String tasks = sc.nextLine();
        task.setTask(tasks);
        String condition = "active";  //because default condition = "active"
        task.setCondition(condition);
        try {
            Connection conn = DriverManager.getConnection(DataBaseConfig.URL,
                    DataBaseConfig.USER, DataBaseConfig.PASSWORD);
            PreparedStatement stm =
                    conn.prepareStatement(" INSERT INTO tasks VALUES (?,?);");
            stm.setInt(1, id);
            stm.setString(2, tasks);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Task is added");
        task.showTasks();
        return task;
    }

    public void deleteTask() {
        Task task = new Task();
        Scanner sc = new Scanner(System.in);
        if (!task.checkIsEmpty()) {
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);

                task.showTasks();
                System.out.println("Write name of task ");
                String nameOfTask = sc.nextLine();
                PreparedStatement stm =
                        con.prepareStatement("DELETE FROM tasks WHERE task = ?;");
                stm.setString(1, nameOfTask);
                stm.execute();
                System.out.println("Task is deleted");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void refactorTask() {
        Task task = new Task();
        Scanner sc = new Scanner(System.in);
        if (!task.checkIsEmpty()) {
            task.showTasks();
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                System.out.println("Write the name of task to refactor");
                String nameOfTask = sc.nextLine();
                System.out.println("Write changed task");
                String changedTask = sc.nextLine();
                PreparedStatement stm =
                        con.prepareStatement
                                ("UPDATE tasks SET task = ? task = ?;");
                stm.setString(1, changedTask);
                stm.setString(2, nameOfTask);
                stm.execute();
                System.out.println("Task is changed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void markAsActive() {
        Task task = new Task();
        Scanner sc = new Scanner(System.in);
        String condition = "active";
        if (!task.checkIsEmpty()) {
            task.showTasks();
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                System.out.println("Write task to mark as \"active\": ");
                String nameOfTask = sc.nextLine();
                PreparedStatement stm =
                        con.prepareStatement
                                ("UPDATE tasks SET condition = ? WHERE task = ?;");
                stm.setString(1, condition);
                stm.setString(2, nameOfTask);
                stm.execute();
                System.out.println("Task was marked as active! ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void markAsComplite() {
        Task task = new Task();
        Scanner sc = new Scanner(System.in);
        String condition = "complite";
        if (!task.checkIsEmpty()) {
            task.showTasks();

            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                System.out.println("Write task to mark as \"complite\": ");
                String nameOfTask = sc.nextLine();
                PreparedStatement stm =
                        con.prepareStatement
                                ("UPDATE tasks SET condition = ? task = ?;");
                stm.setString(1, condition);
                stm.setString(2, nameOfTask);
                stm.execute();
                System.out.println("Task was marked as complite! ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
