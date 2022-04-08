package engine.persistence;

import engine.businesslayer.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Quiz, Long> {
}