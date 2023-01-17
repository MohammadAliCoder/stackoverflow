package app_Users.Model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MyFallback {
   private QuestionsList questionsList;

    public MyFallback() {
        Initializer();
    }
    public void Initializer(){

        Category category = new Category();
        category.setName("Java");

        Questions questions = new Questions();
        questions.setIdquestion(1);
        questions.setQuestion("How do Print in java?");
        questions.setDate_make(Date.valueOf(LocalDateTime.now().toLocalDate()));
        questions.setUsername("Mohammad");
        questions.setCategory(category);
        List<Solutions> solutionsList=new ArrayList<>(Arrays.asList(
                new Solutions(1,"System.out.print()",Date.valueOf(LocalDateTime.now().toLocalDate()),false,"Ali",1),
                new Solutions(2,"System.out.println()",Date.valueOf(LocalDateTime.now().toLocalDate()),false,"Mohammad",1)
        ));
        questions.setSolutionsList(solutionsList);

        HashSet<Questions> hashSet=new HashSet<>();
        hashSet.add(questions);
        category.setQuestions(hashSet);


         questionsList=new QuestionsList(new ArrayList<>(Arrays.asList(questions)));

    }

    public QuestionsList getQuestionsList() {
        return questionsList;
    }
}
