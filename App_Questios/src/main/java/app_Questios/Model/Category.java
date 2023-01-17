package app_Questios.Model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Category.class)
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cate;
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "category",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private Set<Questions> questions;

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
