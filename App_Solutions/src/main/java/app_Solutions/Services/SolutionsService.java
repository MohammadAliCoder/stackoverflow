package app_Solutions.Services;

import app_Solutions.Model.Solutions;
import app_Solutions.Repository.SolutionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("solutionsService")
public class SolutionsService {

    @Qualifier("solutionsRepository")
    @Autowired
    SolutionsRepository solutionsRepository;


   public List<Solutions> findAll(){
       return solutionsRepository.findAll();
   }
   public List<Solutions> findByIdquestion(int id_question){
       return solutionsRepository.findByIdquestion(id_question);
   }



   public void Save(Solutions solutions){
        solutionsRepository.save(solutions);
   }


}
