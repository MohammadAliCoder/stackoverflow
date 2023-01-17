package app_Questios;

import app_Questios.Model.Category;
import app_Questios.Model.Questions;
import app_Questios.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Component
public class Initializer implements CommandLineRunner {
    @Autowired
    CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        if(categoryService.findAll().isEmpty()) {
            Category category = new Category();
            category.setName("Java");
            Questions questions = new Questions();
            questions.setQuestion("How do Print in java?");
            questions.setDate_make(Date.valueOf(LocalDateTime.now().toLocalDate()));
            questions.setUsername("Mohammad");
            Questions questionsj = new Questions();
            questionsj.setQuestion("How  read file in java?");
            questionsj.setDate_make(Date.valueOf(LocalDateTime.now().toLocalDate()));
            questionsj.setUsername("Mohammad");



            categoryService.Save(category, questions);
            categoryService.Save(category, questionsj);

            Category category1 = new Category();
            category1.setName("C++");
            Questions questions1 = new Questions();
            questions1.setQuestion("How do Print in C++?");
            questions1.setDate_make(Date.valueOf(LocalDate.now()));
            questions1.setUsername("Mohammad");
            categoryService.Save(category1, questions1);
        }

    }



    }

