package app_Questios.Repository;

import app_Questios.Model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("questionRepository")
public interface QuestionRepository extends JpaRepository<Questions, Integer> {

    List<Questions> findByQuestion(String question);
    List<Questions> findByUsername(String username);
    Questions findByIdquestion(int idquestion);

}
