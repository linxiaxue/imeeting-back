package fudan.se.lab2.repository;
import fudan.se.lab2.domain.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends CrudRepository<Application,Long> {
    //Application findByChairUsername (String chairUsername);
    List<Application> findByChairUsername (String chairUsername);
    //Application findByAbbreviation (String abbreviation);
    Application findByMeetingFullname(String meetingFullname);
    List<Application> findByStatus (int status);
    Optional<Application> findById(Long id);

}
