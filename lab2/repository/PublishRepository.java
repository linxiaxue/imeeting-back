package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Publish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishRepository extends CrudRepository<Publish,Long> {
    List<Publish> findByMeetingFullname (String meetingFullname);
    Publish findByCid (Long cid);
}
