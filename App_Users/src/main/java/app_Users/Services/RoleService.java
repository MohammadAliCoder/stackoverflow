package app_Users.Services;

import app_Users.Model.Role;
import app_Users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
public class RoleService {
    @Qualifier("roleRepository")
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Role  save(Role role){
       return roleRepository.save(role);
    }

    @Transactional
    public void delete(Role role){
         roleRepository.delete(role);
    }

    public List<Role> rolefindAll(){
        return roleRepository.findAll();
    }


}
