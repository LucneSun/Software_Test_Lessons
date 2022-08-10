package br.com.senac.todo.services;

import br.com.senac.todo.model.ToDo;
import br.com.senac.todo.repositories.ToDoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ToDoServiceTest {

    @Autowired
    private ToDoRepository toDoRepository;


    @AfterEach
    void tearDown(){
        toDoRepository.deleteAll();
    }

    @Test
    void findAll(){
        var toDo = new ToDo("Dormir 8h por dia", true);
        toDoRepository.save(toDo);

        var toDoService = new ToDoService(toDoRepository);
        List<ToDo> toDoList = toDoService.findAll();
        var lastToDo = toDoList.get(toDoList.size() -1);

        assertEquals(toDo.getDescription(), lastToDo.getDescription());
        assertEquals(toDo.isCompleted(), lastToDo.isCompleted());
        assertEquals(toDo.getId(), lastToDo.getId());
    }

    @Test
    void saveToDo(){
        var toDoService = new ToDoService(toDoRepository);
        var toDo = new ToDo("Fix Oi on TodoServiceTest", true);

        toDoService.save(toDo);


        assertEquals(1, toDoRepository.count());
    }

    @Test
    void saveAllToDo(){
        var toDoList = new ArrayList<ToDo>();
        var toDoService = new ToDoService(toDoRepository);
        var toDo1 = new ToDo("Fix Oi on TodoServiceTest", true);
        var toDo2 = new ToDo("Create test to save all", true);
        var toDo3 = new ToDo("Create testo to edit", false);

        toDoList.add(toDo1);
        toDoList.add(toDo2);
        toDoList.add(toDo3);

        toDoService.saveAll(toDoList);

        assertEquals(3.0, toDoRepository.count());
    }

    @Test
    void editToDo(){
        var toDoService = new ToDoService(toDoRepository);
        var toDo = new ToDo("Test Edit", false);

        toDoService.save(toDo);

        toDo.setCompleted(true);

        toDoService.save(toDo);


        assertEquals(1, toDoRepository.count());
    }

    @Test
    void deleteToDo(){
        var toDoService = new ToDoService(toDoRepository);
        var toDo = new ToDo("Test delete", false);

        var toDoSaved = toDoService.save(toDo);

        toDoService.deleteById(toDoSaved.getId());

        assertEquals(0, toDoRepository.count());
    }

    @Test
    void findById()
    {
        var toDoSaved = toDoRepository.save(new ToDo("Dormir 8h por dia", true));

        var toDoService = new ToDoService(toDoRepository);
        var toDoFinded = toDoService.findById(toDoSaved.getId());

        assertEquals(toDoSaved.getDescription(), toDoFinded.getDescription());
        assertEquals(toDoSaved.isCompleted(), toDoFinded.isCompleted());
        assertEquals(toDoSaved.getId(), toDoFinded.getId());
    }

}