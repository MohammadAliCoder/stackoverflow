package app_Solutions.Controllers;

import app_Solutions.Model.Solutions;
import app_Solutions.Model.SolutionsList;
import app_Solutions.Services.SolutionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Solutions")
public class SolutionsController {

    private static Logger log = LoggerFactory.getLogger(SolutionsController.class);

    @Autowired
    SolutionsService solutionsService;

    @GetMapping("/IdQuestions/{id}")
    public SolutionsList getSolutionsById_questions(@PathVariable("id") int id) {
        log.info("GET /Solutions/ 'this is Solutions service'");

        return new SolutionsList(solutionsService.findByIdquestion(id));
    }

    @PostMapping("/Save")
    public void SaveSolution(@RequestBody Solutions solutions) {
        log.info("SET /Solutions/ 'this is Solutions service'");
        solutionsService.Save(solutions);
    }


}
