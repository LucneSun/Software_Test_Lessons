package br.com.senac.todo.controllers;

import br.com.senac.todo.model.ToDo;
import br.com.senac.todo.services.ToDoService;
import br.com.senac.todo.services.ToDoServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest 
public class ToDoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ToDoService toDoService;

    @Test
    void getAllToDos() throws Exception{
        List<ToDo> toDoList = new ArrayList<>();
        toDoList.add(new ToDo(1L, "Comer três vezes", true));
        toDoList.add(new ToDo(2L, "Dormir PELO MENOS duas vezes", true));
        Mockito.when(toDoService.findAll()).thenReturn(toDoList);

        mockMvc.perform(get("/todos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2))).andDo(print());
    }

    @Test
    void getToDo() throws Exception{
        var toDo = new ToDo(1L, "Comer três vezes", true);
        Mockito.when(toDoService.findById(toDo.getId())).thenReturn(toDo);

        mockMvc.perform(get("/todo/" + toDo.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(toDo))).andExpect(status().isOk()).andDo(print())    ;
    }

    @Test
    void createToDo() throws Exception{
        var toDo = new ToDo(1L, "Entrar em férias", false);
        Mockito.when(toDoService.save(any(ToDo.class))).thenReturn(toDo);
        var mapper = new ObjectMapper();
        var jsonToDo = mapper.writeValueAsString(toDo);

        var resultActions = mockMvc.perform(post("/todo").contentType(MediaType.APPLICATION_JSON).content(jsonToDo));
        resultActions.andExpect(status().isCreated()).andExpect(content().json(jsonToDo)).andExpect(jsonPath("$.description").value("Entrar em férias"));
    }
}