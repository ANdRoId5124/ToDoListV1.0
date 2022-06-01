import com.andrew_company.ToDoListRepository;
import entity.ToDoList;

public class Main {
    public static void main(String[] args) {
        ToDoListRepository toDoListRepository = new ToDoListRepository();
        ToDoList t = new ToDoList();
        //ToDoListRepository.showLists();

        System.out.println(ToDoListRepository.findMaxId());
        //toDoListRepository.chooseToDoList();
        //  ToDoListRepository.showLists();
    }

}