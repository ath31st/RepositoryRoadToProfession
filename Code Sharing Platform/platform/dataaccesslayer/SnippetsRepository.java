package platform.dataaccesslayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.businesslayer.entities.Snippet;

import java.util.Optional;

@Repository
public interface SnippetsRepository extends CrudRepository<Snippet, Long> {
    Optional<Snippet> findByUuid(String uuid);
}
