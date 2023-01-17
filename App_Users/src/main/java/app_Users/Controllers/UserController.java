package app_Users.Controllers;

import app_Users.MQ.Sender;
import app_Users.Model.*;
import app_Users.Services.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    Sender sender;

//    @Value("${spring.profiles}")
//    private String zone;
//
//    @GetMapping("ping")
//    public String ping() {
//        return "I'm in zone " + zone;
//    }


    @GetMapping(value = {"", "Home"})
    public ModelAndView Home() {
        log.info("Home Page");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Home");
        return modelAndView;
    }

    @GetMapping(value = {"login"})
    public ModelAndView login() {
        log.info("Login Page");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping(value = "registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Register");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }

    @GetMapping(value = {"Profile"})
    public ModelAndView Profile(String search) {

        if (search != null) {
            return new ModelAndView("Profile", "list", userService.findByUsername(search));
        } else {
            return new ModelAndView("Profile", "list", userService.userfindAll());
        }

    }


    @GetMapping(value = {"Questions"})
    @HystrixCommand(fallbackMethod = "getFallbackQuestions",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000") })
    public ModelAndView Questions(String Language, String search) {


        if (search == null && Language == null) {
            log.info("GET /Questions/ 'this is User service'");
            QuestionsList questionsList = restTemplate.getForObject("http://Gateway-SERVICE/Questions/category",
                    QuestionsList.class);

            return new ModelAndView("Questions", "list", questionsList.getQuestionsList());

        } else if (search == null && Language != null) {
            QuestionsList questionsList = restTemplate.getForObject("http://Gateway-SERVICE/Questions/category/" + Language,
                    QuestionsList.class);
            return new ModelAndView("Questions", "list", questionsList.getQuestionsList());

        } else if (search != null && Language != null) {

            QuestionsList questionsList = restTemplate.getForObject("http://Gateway-SERVICE/Questions/category/" + Language + "/conf/" + search,
                    QuestionsList.class);
            return new ModelAndView("Questions", "list", questionsList.getQuestionsList());

        } else {
            log.info("GET /Questions/ 'this is User service'");
            QuestionsList questionsList = restTemplate.getForObject("http://Gateway-SERVICE/Questions/category",
                    QuestionsList.class);
            return new ModelAndView("Questions", "list", questionsList.getQuestionsList());
        }

    }

    @GetMapping(value = {"AddQuestions"})
    public ModelAndView getAddQuestion() {
        return new ModelAndView("AddQuestions", "questions", new Questions());
    }


    @PostMapping(value = {"AddQuestions"})
    public ModelAndView postAddQuestion(@Valid Questions questions, String Language) {
        log.info("SET /Questions/ 'send Questions via mq'");
        //Date now
        questions.setDate_make(Date.valueOf(LocalDateTime.now().toLocalDate()));
        //username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        questions.setUsername(user.getUsername());
        Category category = new Category();
        category.setName(Language);
        questions.setCategory(category);
        sender.sendMsg(questions);
        return new ModelAndView("AddQuestions", "questions", new Questions());
    }

    @GetMapping(value = {"PrivateQuestions"})
    @HystrixCommand(fallbackMethod = "getFallbackPrivateQuestions",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")})
    public ModelAndView getPrivateQuestions() {

        //username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        QuestionsList questionsList = restTemplate.getForObject("http://localhost:8081/Questions/category/username/" + user.getUsername(),
                QuestionsList.class);


        questionsList.getQuestionsList().forEach(questions -> {
            SolutionsList solutionsList = restTemplate.getForObject("http://SOLUTIONS-SERVICE/Solutions/IdQuestions/" + questions.getIdquestion(),
                    SolutionsList.class);
            questions.setSolutionsList(solutionsList.getSolutionsList());
        });

        return new ModelAndView("PrivateQuestions", "list", questionsList.getQuestionsList());
    }


    @GetMapping(value = "Solutions/{idquestion}")
    @HystrixCommand(fallbackMethod = "getFallbackGetSolution",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")  })
    public ModelAndView getSolution(@PathVariable("idquestion") String idquestion, @ModelAttribute("solutions") Solutions solutions) {
        log.info("GET /Solutions/ 'this is User service'");

        Questions questions = restTemplate.getForObject("http://Gateway-SERVICE/Questions/Question/Id_question/" + idquestion
                , Questions.class);
        SolutionsList solutionsList = restTemplate.getForObject("http://Gateway-SERVICE/Solutions/IdQuestions/" + questions.getIdquestion(),
                SolutionsList.class);
        questions.setSolutionsList(solutionsList.getSolutionsList());
        QuestionsList questionsList = new QuestionsList(new ArrayList<Questions>(Arrays.asList(questions)));
        Map model = new HashMap();
        model.put("list", questionsList.getQuestionsList());
        Solutions solutions1 = new Solutions();
        solutions1.setIdquestion(Integer.valueOf(idquestion));

        model.put("solutions", solutions1);


        return new ModelAndView("Solutions", model);
    }


    @RequestMapping(value = "Solutions/{idquestion}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "getFallbackpostSolution",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")  }
    )
    public ModelAndView postSaveSolution(@Valid Solutions solutions) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());


        //save
        solutions.setDate_make(Date.valueOf(LocalDateTime.now().toLocalDate()));
        solutions.setChecked(false);
        solutions.setUsername(user.getUsername());
        restTemplate.postForObject("http://Gateway-SERVICE/Solutions/Save",
                solutions, Solutions.class);


        Questions questions = restTemplate.getForObject("http://Gateway-SERVICE/Questions/Question/Id_question/" + solutions.getIdquestion()
                , Questions.class);
        SolutionsList solutionsList = restTemplate.getForObject("http://Gateway-SERVICE/Solutions/IdQuestions/" + questions.getIdquestion(),
                SolutionsList.class);
        questions.setSolutionsList(solutionsList.getSolutionsList());
        QuestionsList questionsList = new QuestionsList(new ArrayList<Questions>(Arrays.asList(questions)));
        Map model = new HashMap();
        model.put("list", questionsList.getQuestionsList());
        Solutions solutions1 = new Solutions();
        solutions1.setIdquestion(solutions.getIdquestion());

        model.put("solutions", solutions1);


        return new ModelAndView("Solutions", model);
    }




    //_____________________Methods hystrix______________________________________________________________________________

    public ModelAndView getFallbackpostAddQuestion(@Valid Questions questions) {

        return new ModelAndView("AddQuestions", "questions", new Questions());
    }


    public ModelAndView getFallbackQuestions(String Language, String search) {
        MyFallback myFallback = new MyFallback();


        return new ModelAndView("Questions", "list", myFallback.getQuestionsList().getQuestionsList());


    }


    public ModelAndView getFallbackPrivateQuestions() {
        MyFallback myFallback = new MyFallback();
        List<Questions> list = new ArrayList<>();
        myFallback.getQuestionsList().getQuestionsList().forEach(ques -> {
            if (ques.getUsername().equals("Mohammad"))
                list.add(ques);
        });

        return new ModelAndView("PrivateQuestions", "list", list);
    }

    public ModelAndView getFallbackGetSolution(@PathVariable("idquestion") String idquestion, @ModelAttribute("solutions") Solutions solutions) {
        MyFallback myFallback = new MyFallback();


        Map model = new HashMap();
        model.put("list", myFallback.getQuestionsList().getQuestionsList());
        Solutions solutions1 = new Solutions();
        solutions1.setIdquestion(Integer.valueOf(1));

        model.put("solutions", solutions1);


        return new ModelAndView("Solutions", model);
    }

    public ModelAndView getFallbackpostSolution(@Valid Solutions solutions) {

        Category category = new Category();
        category.setName("Java");

        Questions questions = new Questions();
        questions.setIdquestion(1);
        questions.setQuestion("How do Print in java?");
        questions.setDate_make(Date.valueOf(LocalDateTime.now().toLocalDate()));
        questions.setUsername("Mohammad");
        questions.setCategory(category);
        List<Solutions> solutionsList = new ArrayList<>(Arrays.asList(
                new Solutions(1, "System.out.print()", Date.valueOf(LocalDateTime.now().toLocalDate()), false, "Ali", 1),
                new Solutions(2, "System.out.println()", Date.valueOf(LocalDateTime.now().toLocalDate()), false, "Mohammad", 1)
        ));
        questions.setSolutionsList(solutionsList);

        HashSet<Questions> hashSet = new HashSet<>();
        hashSet.add(questions);
        category.setQuestions(hashSet);


        QuestionsList questionsList = new QuestionsList(new ArrayList<>(Arrays.asList(questions)));


        Map model = new HashMap();
        model.put("list", questionsList.getQuestionsList());
        Solutions solutions1 = new Solutions();
        solutions1.setIdquestion(Integer.valueOf(1));

        model.put("solutions", solutions1);


        return new ModelAndView("Solutions", model);
    }


}
