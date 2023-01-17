package app_Users;

import app_Users.Model.Role;
import app_Users.Model.User;
import app_Users.Services.RoleService;
import app_Users.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    // Save Role&Users when runtime
    @Override
    public void run(String... args) throws Exception {

       if(roleService.rolefindAll().isEmpty()) {
           Role role_Admin = new Role();
           role_Admin.setRole("ADMIN");
           roleService.save(role_Admin);

           Role role_User = new Role();
           role_User.setRole("User");
           roleService.save(role_User);
       }

//____________Save Admin User_____
        if(userService.userfindAll().isEmpty()) {
            User userAmin = new User();
            userAmin.setUsername("Admin");
            userAmin.setPassword("Admin");
            userAmin.setEmail("Admin@gmail.com");
            userAmin.setFirst_name("Admin1");
            userAmin.setLastName("Admin2");

            userAmin.setUsername("Mohammad");
            userAmin.setPassword("12345");
            userAmin.setEmail("mohammad@gmail.com");
            userAmin.setFirst_name("Mohammad");
            userAmin.setLastName("Mohammad");
            userService.saveUser(userAmin);
        }



    }
}
