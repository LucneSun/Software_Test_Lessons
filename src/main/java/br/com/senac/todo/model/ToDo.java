package br.com.senac.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ToDo {
    @Id
    @GeneratedValue
    private long id;
    private String description;
    private boolean completed;

    public ToDo(){

    }

    public ToDo(long id, String description, boolean completed){
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public ToDo(String description, boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
