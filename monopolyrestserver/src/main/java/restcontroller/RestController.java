package restcontroller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rest.entities.User;

@Controller
@RequestMapping(path = "/monopoly")
public class RestController {

    @Autowired
    private RestService service;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<User> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping(path="/register")
    public @ResponseBody models.User registerUser(String username, String password){
        User dtoUser = new User();
        dtoUser.setUsername(username);
        dtoUser.setPassword(password);
        service.addUser(dtoUser);
        return getUserByCredentials(username, password);
    }

    @GetMapping(path="/user")
    public @ResponseBody models.User getUser(String username){
        User dtoUser = service.getUser(username);
        if (dtoUser == null) return null;
        return modelMapper.map(dtoUser, models.User.class);
    }

    @GetMapping(path="/checkUsername")
    public @ResponseBody boolean checkUser(String username){
        User dtoUser = service.getUser(username);
        return dtoUser == null;
    }

    @GetMapping(path="/userCred")
    public @ResponseBody models.User getUserByCredentials(String username, String password){
        User dtoUser = service.getUserByCredentials(username, password);
        if(dtoUser == null) return null;
        return modelMapper.map(dtoUser, models.User.class);
    }
}
