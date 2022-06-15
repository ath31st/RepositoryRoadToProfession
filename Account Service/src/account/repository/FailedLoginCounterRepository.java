package account.repository;

import account.entites.FailedLoginCounter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FailedLoginCounterRepository extends CrudRepository<FailedLoginCounter, Long> {
    Optional<FailedLoginCounter> findByEmailIgnoreCase(String email);
}
