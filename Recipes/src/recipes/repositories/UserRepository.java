package recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import recipes.entites.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}