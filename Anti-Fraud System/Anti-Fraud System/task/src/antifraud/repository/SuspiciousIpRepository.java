package antifraud.repository;

import antifraud.entity.SuspiciousIp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuspiciousIpRepository extends CrudRepository<SuspiciousIp, Long> {
    Optional<SuspiciousIp> findSuspiciousIpByIp(String ip);
}
