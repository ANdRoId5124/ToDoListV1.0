package com.andrew_company.entity;

import com.andrew_company.DataBaseConfig;
import com.andrew_company.ToDoListRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

    private int id;
    private String name;
    private String topic;
    private ArrayList<Task> task;


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

    public void setTasks(ArrayList<Task> task) {
        this.task = task;
    }

    public ArrayList<Task> getTasks() {
        return task;
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public Task addTask(int id) {
        Scanner sc = new Scanner(System.in);
        Task task = new Task();
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

    public ArrayList<Task> setTaskToToDoListTasks(Task task) {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task);
        return tasks;
    }

    public ArrayList<ToDoList> setTaskToToDoList() {
        ToDoList toDoList = new ToDoList();
        Task tsk = new Task();
        ArrayList<ToDoList> toDoLists = toDoList.getall();
        ArrayList<Task> tasks = tsk.getAllTasks();
        for (int i = 0; i < toDoLists.size(); i++) {
            ArrayList<Task> task = new ArrayList<>();
            for (int j = 0; j < tasks.size(); j++)
                if (toDoLists.get(i).getId() == tasks.get(j).getId()) {
                    task.add(tasks.get(j));
                    toDoLists.get(i).setTasks(task);
                }
        }
        return toDoLists;
    }


    public int writeNumberOfId() {
        ToDoList toDoList = new ToDoList();
        Scanner sc = new Scanner(System.in);
        int digit;
        while (true) {
            System.out.println("Write number of id: ");
            digit = sc.nextInt();
            if (toDoList.checkInterval(digit)) {
                return digit;
            } else {
                System.out.println("We do not have this number of id, Please try again!");
            }
        }
    }

    public boolean checkInterval(int digit) {
        ToDoList toDoList = new ToDoList();
        boolean result = true;
        if (digit >= 1 && digit <= toDoList.findMaxId()) {
        } else {
            result = false;
        }
        return result;
    }

    public int findMaxId() {
        int maxId = 0;
        Connection con;
        try {
            con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                    DataBaseConfig.PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(list_id) AS max_id FROM to_do_lists");
            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
    }

    public boolean checkIsEmpty() {
        boolean result = false;
        Connection con;
        try {
            con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                    DataBaseConfig.PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM to_do_lists;");
            if (!rs.next()) {
                System.out.println("ToDoList is not excist! Please add ToDoList");
                result = true;
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void showLists() { //Null pointer exception, if tasks empty!!
        ToDoList toDoList = new ToDoList();
        ArrayList<ToDoList> lists = toDoList.setTaskToToDoList();
        // try {
        for (ToDoList list : lists) {
            System.out.print(list.getId() + " " + list.getName() + " " +
                    list.getTopic() + " ");
            for (Task task : list.getTasks()) {
                System.out.print(task.getTask() + " " + task.getCondition() + "; ");
            }
            System.out.println();
        }
        //  }catch (NullPointerException e){
    }


    public ArrayList<ToDoList> getall() {
        ToDoList toDoList = new ToDoList();
        ArrayList<ToDoList> lists = new ArrayList<>();
        if (!toDoList.checkIsEmpty()) {
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM to_do_lists");
                while (rs.next()) {
                    ToDoList list = new ToDoList();
                    list.setId(rs.getInt("list_id"));
                    list.setName(rs.getString("name"));
                    list.setTopic(rs.getString("topic"));
                    lists.add(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lists;
    }


    public int writeDigit() {
        Scanner scanner = new Scanner(System.in);
        int digit = scanner.nextInt();
        return digit;
    }


    public int writeDigitForMenu() {
        Scanner scanner = new Scanner(System.in);
        String menuText = "Choose between 1 and 5: " + "\n" +
                "1 is Show ToDoLists;" + "\n" +
                "2 is AddList;" + "\n" +
                "3 is DeleteList;" + "\n" +
                "4 is ChooseList;" + "\n" +
                "5 is Exit";
        System.out.println(menuText);
        while (true) {
            int digit = scanner.nextInt();
            if (digit >= 1 && digit <= 5) {
                return digit;
            } else {
                System.out.println(menuText);
            }
        }
    }

    public void addList() {
        Scanner sc = new Scanner(System.in);
        Connection con;
        try {
            con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                    DataBaseConfig.PASSWORD);
            System.out.println("Write the name of list: ");
            String name = sc.nextLine();
            System.out.println("Write the topic: ");
            String topic = sc.next();
            PreparedStatement stm =
                    con.prepareStatement
                            ("INSERT INTO to_do_lists(name,topic) VALUES(?, ?);");
            stm.setString(1, name);
            stm.setString(2, topic);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ToDoList list = new ToDoList();
        int idOfNewToDoList = (list.findMaxId());
        Task addingTask = list.addTask(idOfNewToDoList);
        list.setTaskToToDoListTasks(addingTask);
        System.out.println("List is added");
    }

    public void deleteList() {
        ToDoList toDoList = new ToDoList();
        if (!toDoList.checkIsEmpty()) {
            toDoList.showLists();
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                System.out.println("Choose the number of id of ToDoList to delete ToDoList");
                int idOfListTodelete = toDoList.writeDigit();
                PreparedStatement stm =
                        con.prepareStatement
                                ("DELETE FROM tasks WHERE list_id = ?;");
                stm.setInt(1, idOfListTodelete);
                stm.executeUpdate();
                stm.close();
                PreparedStatement stm1 =
                        con.prepareStatement
                                ("DELETE FROM to_do_lists WHERE list_id = ?;");
                stm1.setInt(1, idOfListTodelete);
                stm1.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("ToDoList is deleted");

        }
    }


    public void chooseToDoList() {
        ToDoListRepository repository = new ToDoListRepository();
        ToDoList toDoList = new ToDoList();
        if (!toDoList.checkIsEmpty()) {
            toDoList.showLists();
            int id = toDoList.writeNumberOfId();
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                PreparedStatement stm =
                        con.prepareStatement
                                ("SELECT * FROM to_do_lists LEFT JOIN tasks USING (list_id) WHERE list_id = ?;");
                stm.setInt(1, id);
                ResultSet resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    Task task = new Task();
                    toDoList.setId(resultSet.getInt("list_id"));
                    toDoList.setName((resultSet.getString("name")));
                    toDoList.setTopic(resultSet.getString("topic"));
                    task.setTask(resultSet.getString("task"));
                    task.setTask(resultSet.getString("condition"));
                    toDoList.setTaskToToDoListTasks(task);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            repository.menuOfToDoList();
        }
    }
}