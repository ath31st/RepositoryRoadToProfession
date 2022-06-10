package antifraud.repository;

import antifraud.entity.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Optional<List<Transaction>> findByNumberEquals(String number);

    Optional<List<Transaction>> findByNumberAndDateBetween(String number, LocalDateTime dateStart, LocalDateTime dateEnd, Sort sort);
}
