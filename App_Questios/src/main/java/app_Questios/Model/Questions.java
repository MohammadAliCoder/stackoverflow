package app_Questios.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Questions.class)
@Entity
@Table(name = "Questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idquestion")
    private int idquestion;
    @Column(name = "question")
    private String question;
    @Column(name = "date_make")
    private Date date_make;
    @Column(name = "username")
    private String username;

    @JsonBackReference
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cate_ques",referencedColumnName = "id_cate",nullable = false)
    private Category category;

    public Questions() {
    }

    public Questions(String question, Date date_make, String username) {
        this.question = question;
        this.date_make = date_make;
        this.username = username;
    }

    public int getIdquestion() {
        return idquestion;
    }

    public void setIdquestion(int idquestion) {
        this.idquestion = idquestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getDate_make() {
        return date_make;
    }

    public void setDate_make(Date date_make) {
        this.date_make = date_make;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "idquestion=" + idquestion +
                ", question='" + question + '\'' +
                ", date_make=" + date_make +
                ", username='" + username + '\'' +
                ", category=" + category +
                '}';
    }
}
