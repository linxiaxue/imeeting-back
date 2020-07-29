package fudan.se.lab2.repository;

import fudan.se.lab2.domain.ContributionMeetingTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionMeetingTopicRepository extends CrudRepository<ContributionMeetingTopic,Long> {
    List<ContributionMeetingTopic> findByMeetingFullnameAndTopic (String meetingFullname,String topic);
    List<ContributionMeetingTopic> findByCId (Long cid);
    void deleteAllByCId(Long cid);
}
