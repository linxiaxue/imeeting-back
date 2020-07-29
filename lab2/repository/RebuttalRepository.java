package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Rebuttal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebuttalRepository extends CrudRepository<Rebuttal,Long> {
    Rebuttal findByCid (Long cid);
}
