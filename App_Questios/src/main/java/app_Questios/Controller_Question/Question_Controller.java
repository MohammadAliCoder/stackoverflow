package app_Questios.Controller_Question;

import app_Questios.Model.Questions;
import app_Questios.Model.QuestionsList;
import app_Questios.Services.CategoryService;
import app_Questios.Services.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/Questions")
public class Question_Controller {

    private static Logger log = LoggerFactory.getLogger(Question_Controller.class);

    @Autowired
    CategoryService categoryService;
    @Autowired
    QuestionService questionService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.profiles}")
    private String zone;

    @GetMapping("/ping")
    public String ping() {
        return "I'm in zone " + zone;
    }
    @GetMapping(value ="/category/{name}/conf/{question}",produces = "application/json")
    public QuestionsList findByQuestionAndName_category(@PathVariable("name") String name,@PathVariable("question") String question) {
        log.info("GET /Questions/ 'this is Questions service 1'");
        question+="?";

        QuestionsList questionsList=new QuestionsList();
        questionsList.setQuestionsList(questionService.findByQuestionAndName_category(question,name));
        return questionsList;
    }
    @GetMapping(value ="/category/{name}" ,produces = "application/json")
    public QuestionsList getCategoryfindByName(@PathVariable("name") String name) {

        log.info("GET /Questions/ 'this is Questions service 2'");

        QuestionsList questionsList=new QuestionsList();

        questionsList.setQuestionsList(categoryService.getQuestion_findByName(name));
        return questionsList;
    }
    @GetMapping(value ="/Question/Id_question/{id_question}" ,produces = "application/json")
    public Questions getQuestionfindById_question(@PathVariable("id_question") int id_question) {
        Questions questions=questionService.findById_question(id_question);
        return questions;
    }

    @GetMapping(value ="/category",produces = "application/json")
    public QuestionsList getCategoryAll() {
        log.info("GET /Questions/ 'this is Questions service 3'");
        QuestionsList questionsList=new QuestionsList();
                questionsList.setQuestionsList(questionService.findAll());
        return  questionsList;
    }
	
	@GetMapping(value ="/category/username/{username}",produces = "application/json")
    public QuestionsList getQuestionsByUsername(@PathVariable("username") String username) {
        QuestionsList questionsList=new QuestionsList();
                questionsList.setQuestionsList(questionService.findByUsername(username));
        return  questionsList;
    }




}
