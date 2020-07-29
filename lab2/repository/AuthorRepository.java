package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Long> {
    List<Author> findByCId (Long cid);
    Author findByNameAndEmailAndDistrictAndWorkUnit (String name,String email,String district,String workUnit);
    Author findByCIdAndNameAndEmail (Long cid,String name,String email);
    void deleteAllByCId (Long cid);
}
