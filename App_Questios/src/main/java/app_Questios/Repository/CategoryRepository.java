package app_Questios.Repository;

import app_Questios.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("categoryRepository")
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Category findByName(String name);

}
