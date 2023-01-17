package app_Questios.Services;

import app_Questios.Model.Questions;
import app_Questios.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("questionService")
public class QuestionService {
    @Qualifier("questionRepository")
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CategoryService categoryService;


    public List<Questions> findAll(){
        return questionRepository.findAll();
    }
    public List<Questions> findByQuestionAndName_category(String Question,String name){

        List<Questions> mainList = categoryService.getQuestion_findByName(name);

        List<Questions> secondList =questionRepository.findByQuestion(Question);

        List<Questions> intersectionList = new ArrayList<Questions>();

        for (Questions questions : mainList) {
            if(secondList.contains(questions)) {
                intersectionList.add(questions);
            }
        }


        return intersectionList;
    }

	public List<Questions> findByUsername(String username){
		return questionRepository.findByUsername(username);
	}
	public Questions findById_question(int id_question){
        return questionRepository.findByIdquestion(id_question);
    }


}
