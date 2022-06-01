package entity;

import com.andrew_company.DataBaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

    private int id;
    private String name;
    private String topic;
    private ArrayList<String> task;
    private String condition;

    public void addTasksToTaskList(String tasks){
        task.add(tasks);
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    ToDoList(String name, String topic, ArrayList<String> task) {
        this.topic = topic;
        this.task = task;

    }

    public ToDoList() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTasks(ArrayList<String> task) {
        this.task = task;
    }

    public ArrayList<String> getTasks() {
        return task;
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public static void showTasks() {
        Connection con;
        try {
            con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                    DataBaseConfig.PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks;");
            while (rs.next()) {
                System.out.print("ID " + rs.getInt("list_id") + " ");
                System.out.print("Tasks: " + rs.getString("task") + " ");
                System.out.println("Condition: " + rs.getString("condition"));
                System.out.println("--------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIsEmpty() {
        boolean result = true;
        Connection con;
        try {
            con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                    DataBaseConfig.PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks;");
            if (rs.next() == false) {
                System.out.println("Tasks is empty! Please add task");
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addTask() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Write ToDoList id: ");
        int id = sc.nextInt();
        System.out.println("Write task to add: ");
        String task = sc.next();
        try {
            Connection conn = DriverManager.getConnection(DataBaseConfig.URL,
                    DataBaseConfig.USER, DataBaseConfig.PASSWORD);
            Statement stmn = conn.createStatement();
            PreparedStatement stm =
                    conn.prepareStatement(" INSERT INTO tasks VALUES (?,?);");
            stm.setInt(1, id);
            stm.setString(2, task);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Task is added");
        ToDoList.showTasks();
    }

    public void deleteTask() {
        Scanner sc = new Scanner(System.in);
        Connection con;
        try {
            con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                    DataBaseConfig.PASSWORD);
            Statement stmt = con.createStatement();
            if (ToDoList.checkIsEmpty() == false) {
            } else {
                ToDoList.showTasks();
                System.out.println("Write idOfTask to delete: ");
                int id = sc.nextInt();
                System.out.println("Write name of task ");
                String nameOfTask = sc.next();
                PreparedStatement stm =
                        con.prepareStatement("DELETE FROM tasks WHERE list_id = ? and task = ?;");
                stm.setInt(1, id);
                stm.setString(2, nameOfTask);
                stm.execute();
                System.out.println("Task is deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void refactorTask() {
        Scanner sc = new Scanner(System.in);
        if (ToDoList.checkIsEmpty() == false) {
        } else {
            ToDoList.showTasks();
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                Statement stmt = con.createStatement();
                System.out.println("Write id of task to refactor: ");
                int idOfTask = sc.nextInt();
                System.out.println("Write the name of task to refactor");
                String nameOfTask = sc.next();
                System.out.println("Write changed task");
                String changedTask = sc.next();
                PreparedStatement stm =
                        con.prepareStatement
                                ("UPDATE tasks SET task = ? WHERE list_id = ? and task = ?;");
                stm.setString(1, changedTask);
                stm.setInt(2, idOfTask);
                stm.setString(3, nameOfTask);
                stm.execute();
                System.out.println("Task is changed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void markAsActive() {
        Scanner sc = new Scanner(System.in);
        String condition = "active";
        if (ToDoList.checkIsEmpty() == false) {
        } else {
            ToDoList.showTasks();

            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                Statement stmt = con.createStatement();
                System.out.println("Write id of task: ");
                int id = sc.nextInt();
                System.out.println("Write task to mark as \"active\": ");
                String nameOfTask = sc.next();
                PreparedStatement stm =
                        con.prepareStatement
                                ("UPDATE tasks SET condition = ? WHERE list_id = ? and task = ?;");
                stm.setString(1, condition);
                stm.setInt(2, id);
                stm.setString(3, nameOfTask);
                stm.execute();
                System.out.println("Task was marked as active! ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void markAsComplite() {
        Scanner sc = new Scanner(System.in);
        String condition = "complite";
        if (ToDoList.checkIsEmpty() == false) {
        } else {
            ToDoList.showTasks();

            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                Statement stmt = con.createStatement();
                System.out.println("Write id of task: ");
                int id = sc.nextInt();
                System.out.println("Write task to mark as \"complite\": ");
                String nameOfTask = sc.next();
                PreparedStatement stm =
                        con.prepareStatement
                                ("UPDATE tasks SET condition = ? WHERE list_id = ? and task = ?;");
                stm.setString(1, condition);
                stm.setInt(2, id);
                stm.setString(3, nameOfTask);
                stm.execute();
                System.out.println("Task was marked as complite! ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public static void menuOfToDoList(ToDoList list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose between 1 and 5: " + "\n" +
                "1 is Add task;" + "\n" +
                "2 is Delete task;" + "\n" +
                "3 is Refactor task;" + "\n" +
                "4 is Mark as \"active\";" + "\n" +
                "5 is Mark as \"complite\"");
        while (true) {
            int menuDigit = sc.nextInt();
            if (menuDigit >= 1 && menuDigit <= 5) {
                switch (menuDigit) {
                    case 1:
                        list.addTask();
                        return;

                    case 2:
                        list.deleteTask();
                        return;

                    case 3:
                        list.refactorTask();
                        return;

                    case 4:
                        list.markAsActive();
                        return;

                    case 5:
                        list.markAsComplite();
                        return;
                }

            } else {
                System.out.println("Choose between 1 and 5: " + "\n" +
                        "1 is Add task;" + "\n" +
                        "2 is Delete task;" + "\n" +
                        "3 is Refactor task;" + "\n" +
                        "4 is Mark as \"active\";" + "\n" +
                        "5 is Mark as \"complite\"");
                continue;
            }
        }
    }
}