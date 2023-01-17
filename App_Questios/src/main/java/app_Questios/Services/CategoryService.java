package app_Questios.Services;

import app_Questios.Model.Category;
import app_Questios.Model.Questions;
import app_Questios.Repository.CategoryRepository;
import app_Questios.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("categoryService")
public class CategoryService {
    @Qualifier("categoryRepository")
    @Autowired
    CategoryRepository categoryRepository;
    @Qualifier("questionRepository")
    @Autowired
    QuestionRepository questionRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
    public Category findByName(String Name){
        return categoryRepository.findByName(Name);
    }
    public List<Questions> getQuestion_findByName(String Name){
        List<Questions> questionsList = new ArrayList<Questions>();
        questionsList.addAll(categoryRepository.findByName(Name).getQuestions());
        return questionsList;
    }
    public void Save(Category category,Questions questions){

        if(findByName(category.getName())!=null){ //is not null
            questions.setCategory(findByName(category.getName()));
            questionRepository.save(questions);
        }else{//is null
            categoryRepository.save(category);
            questions.setCategory(findByName(category.getName()));
            questionRepository.save(questions);
        }


    }
}
