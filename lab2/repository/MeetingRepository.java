package fudan.se.lab2.repository;
import fudan.se.lab2.domain.Meeting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends CrudRepository<Meeting,Long> {
    //Meeting findByAbbreviation (String abbreviation);
    Meeting findByMeetingFullname(String meetingFullname);
    List<Meeting> findByChairUsername (String chairUsername);
    List<Meeting> findAllByOrderById();
    Optional<Meeting> findById(Long id);
}
