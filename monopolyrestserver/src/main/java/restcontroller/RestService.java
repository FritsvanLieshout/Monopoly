package restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.entities.User;
import rest.repository.IUserRepository;

@Service
public class RestService {

    @Autowired
    IUserRepository repository;

    public Iterable<User> getAllUsers(){
        return repository.findAll();
    }

    public void addUser(User userDTO){
        repository.save(userDTO);
    }

    public User getUser(String username) { return repository.findUserByUsername(username); }

    public User getUserByCredentials(String username, String password){ return repository.findUserByCredentials(username, password); }
}
