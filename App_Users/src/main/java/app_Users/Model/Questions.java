package app_Users.Model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.sql.Date;
import java.util.List;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Questions.class)

public class Questions {

    private int idquestion;
    private String question;
    private Date date_make;
    private String username;
    private List<Solutions> solutionsList;
    private Category category;



    public Questions() {
    }

    public Questions(int idquestion, String question, Date date_make, String username) {
        this.idquestion = idquestion;
        this.question = question;
        this.date_make = date_make;
        this.username = username;
    }

    public Questions(int idquestion, String question, Date date_make, String username, List<Solutions> solutionsList) {
        this.idquestion = idquestion;
        this.question = question;
        this.date_make = date_make;
        this.username = username;
        this.solutionsList = solutionsList;
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

    public List<Solutions> getSolutionsList() {
        return solutionsList;
    }

    public void setSolutionsList(List<Solutions> solutionsList) {
        this.solutionsList = solutionsList;
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
