package com.andrew_company;

import entity.ToDoList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoListRepository {
    public static ArrayList<ToDoList> lists = new ArrayList<>();

    public void addTaskToToDoList(){
        Scanner sc = new Scanner(System.in);
        int id = (ToDoList.findMaxIdOfTask() + 1);
        System.out.println("Write your task: ");
        String task = sc.nextLine();
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
    }
    public static int writeNumberOfId() {
        Scanner sc = new Scanner(System.in);
        int digit;
        while (true) {
            System.out.println("Write number of id: ");
            digit = sc.nextInt();
            if (ToDoListRepository.checkInterval(digit) == true) {
                return digit;
            } else {
                System.out.println("We do not have this number of id, Please try again!");
                continue;
            }
        }
    }

    public static boolean checkInterval(int digit) {
        boolean result = true;
        if (digit >= 1 && digit <= ToDoListRepository.findMaxId()) {
        } else {
            result = false;
        }
        return result;
    }

    public static int findMaxId() {
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

    public static boolean checkIsEmpty() {
        boolean result = false;
        Connection con;
        try {
            con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                    DataBaseConfig.PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM to_do_lists;");
            if (rs.next() == false) {
                System.out.println("ToDoList is not excist! Please add ToDoList");
                result = true;
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean IsContainsName(String name) {
        boolean result = true;
        for (ToDoList list : lists) {
            if (list.getName().contains(name)) {
            } else {
                result = false;
            }
        }
        return result;
    }

    public static void showLists(ArrayList<ToDoList> lists){
        for(ToDoList list: lists){
            System.out.print(list.getId() + " " +
                    list.getName() + " ");
            for(String str : list.getTasks()){
                System.out.print(str);
                System.out.println();
            }
        }
    }

    public ArrayList<ToDoList> getall() {
        ArrayList<ToDoList> lists = new ArrayList<>();
        if (ToDoListRepository.checkIsEmpty() == true) {
        } else {
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT a.list_id, a.name, a.topic, string_agg(b.task, ', ') AS task\n" +
                        "FROM to_do_lists a\n" +
                        "INNER JOIN tasks b USING (list_id)\n" +
                        "GROUP BY a.list_id, a.name;");
                while (rs.next()) {
                    ToDoList list = new ToDoList();
                    list.setTasks(new ArrayList<>());
                    list.setId(rs.getInt("list_id"));
                    list.setName(rs.getString("name"));
                    list.setTopic(rs.getString("topic"));
                     list.addTasksToTaskList(rs.getString("task"));
                     lists.add(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lists;
    }


    public static int writeDigit() {
        Scanner scanner = new Scanner(System.in);
        int digit = scanner.nextInt();
        return digit;
    }


    public static int writeDigitForMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose between 1 and 5: " + "\n" +
                "1 is Show ToDoLists;" + "\n" +
                "2 is AddList;" + "\n" +
                "3 is DeleteList;" + "\n" +
                "4 is ChooseList;" + "\n" +
                "5 is Exit");
        while (true) {
            int digit = scanner.nextInt();
            if (digit >= 1 && digit <= 5) {
                return digit;
            } else {
                System.out.println("Choose between 1 and 5: " + "\n" +
                        "1 is Show ToDoLists;" + "\n" +
                        "2 is AddList;" + "\n" +
                        "3 is DeleteList;" + "\n" +
                        "4 is ChooseList;" + "\n" +
                        "5 is Exit");
                continue;
            }
        }
    }

    public void addList() {
        Scanner sc = new Scanner(System.in);
        Connection con;
        try {
            con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                    DataBaseConfig.PASSWORD);
            Statement stmt = con.createStatement();
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
        ToDoListRepository list = new ToDoListRepository();
        list.addTaskToToDoList();
        System.out.println("List is added");
    }

    public void deleteList() {
        ToDoListRepository toDoListRepository = new ToDoListRepository();
        Scanner sc = new Scanner(System.in);
        if (ToDoListRepository.checkIsEmpty() == true) {
        } else {
            ToDoListRepository.showLists(toDoListRepository.getall());
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                Statement stmt = con.createStatement();
                System.out.println("Choose the number of id of ToDoList to delete ToDoList");
                int idOfListTodelete = ToDoListRepository.writeDigit();
                PreparedStatement stm =
                        con.prepareStatement
                                ("DELETE FROM to_do_list WHERE list_id = ?;");
                stm.setInt(1, idOfListTodelete);
                stm.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("ToDoList is deleted");

        }
    }


    public void chooseToDoList() {
        ToDoListRepository toDoListRepository = new ToDoListRepository();
        ToDoList toDoList = new ToDoList();
        toDoList.setTasks(new ArrayList<>());
        String task;
        Scanner sc = new Scanner(System.in);
        if (ToDoListRepository.checkIsEmpty() == true) {
        } else {
            ToDoListRepository.showLists(toDoListRepository.getall());
            Connection con;
            try {
                con = DriverManager.getConnection(DataBaseConfig.URL, DataBaseConfig.USER,
                        DataBaseConfig.PASSWORD);
                Statement stmt = con.createStatement();
                PreparedStatement stm =
                        con.prepareStatement
                                ("SELECT * FROM to_do_lists LEFT JOIN tasks USING (list_id) WHERE list_id = ?;");
                stm.setInt(1, ToDoListRepository.writeNumberOfId());
                ResultSet resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    toDoList.setId(resultSet.getInt("list_id"));
                    toDoList.setName((resultSet.getString("name")));
                    toDoList.setTopic(resultSet.getString("topic"));
                    task = (resultSet.getString("task"));
                    toDoList.addTasksToTaskList(task);
                    toDoList.setCondition(resultSet.getString("condition"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ToDoList.menuOfToDoList(toDoList);
        }
    }

    public static void menuOfToDoListRepository(ToDoListRepository repository) {
        while (true) {
            int menuDigit = ToDoListRepository.writeDigitForMenu();
            switch (menuDigit) {
                case 1:
                    ToDoListRepository.showLists(repository.getall());
                    continue;

                case 2:
                    repository.addList();
                    continue;

                case 3:
                    repository.deleteList();
                    continue;

                case 4:
                    repository.chooseToDoList();
                    continue;

                case 5:
                    return;
            }
        }
    }
}

