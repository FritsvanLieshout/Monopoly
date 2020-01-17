package rest.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import rest.entities.User;

@Qualifier
public interface IUserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
    User findUserByUsername(String username);

    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
    User findUserByCredentials(String username, String password);
}
