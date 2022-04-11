package platform.dataaccesslayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.businesslayer.entities.Snippet;

@Repository
public interface SnippetsRepository extends CrudRepository<Snippet,Long> {
}
