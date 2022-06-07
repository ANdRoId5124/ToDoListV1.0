import com.andrew_company.ToDoListRepository;
import com.andrew_company.entity.ToDoList;



public class Main {
    public static void main(String[] args) {
        ToDoListRepository toDoListRepository = new ToDoListRepository();
        ToDoList t1 = new ToDoList();
       toDoListRepository.menuOfToDoListRepository(t1);
    }

}