package engine.persistence;

import engine.businesslayer.entities.Complete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompleteRepository extends PagingAndSortingRepository<Complete, Long> {

    @Query("SELECT c FROM Complete c WHERE c.userId = :id")
    Page<Complete> findIdCompleteWithPagination(Pageable pageable, @Param("id") Long id);
}
