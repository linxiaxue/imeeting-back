package fudan.se.lab2.repository;

import fudan.se.lab2.domain.MemberMeetingTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import java.util.List;

@Repository
public interface MemberMeetingTopicRepository extends CrudRepository<MemberMeetingTopic,Long> {
    MemberMeetingTopic findByMemberNameAndMeetingFullnameAndTopic (String memberName,String meetingFullname,String topic);
    List<MemberMeetingTopic> findByMeetingFullnameAndTopic (String meetingFullname,String topic);
    List<MemberMeetingTopic> findByMeetingFullname (String meetingFullname);
}
