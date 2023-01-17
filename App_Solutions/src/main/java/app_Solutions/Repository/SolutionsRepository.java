package app_Solutions.Repository;

import app_Solutions.Model.Solutions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("solutionsRepository")
public interface SolutionsRepository extends JpaRepository<Solutions,Integer> {

    List<Solutions> findBySolution(String Solution);
    List<Solutions> findByIdquestion(int idquestion);

}
