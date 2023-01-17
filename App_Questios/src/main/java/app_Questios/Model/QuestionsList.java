package app_Questios.Model;

import java.util.List;

public class QuestionsList {
    List<Questions> questionsList;

    public QuestionsList() {

    }

    public QuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }
}
