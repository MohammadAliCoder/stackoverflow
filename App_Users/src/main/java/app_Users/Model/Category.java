package app_Users.Model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Set;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Category.class)
public class Category {

    private int id_cate;
    private String name;
    private Set<Questions> questions;

    public Category() {
    }

    public Category(int id_cate, String name) {
        this.id_cate = id_cate;
        this.name = name;
    }

    public int getId_cate() {
        return id_cate;
    }

    public void setId_cate(int id_cate) {
        this.id_cate = id_cate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Questions> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id_cate=" + id_cate +
                ", name='" + name + '\'' +
                '}';
    }
}
