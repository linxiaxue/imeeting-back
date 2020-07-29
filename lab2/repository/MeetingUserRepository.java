package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.MeetingUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MeetingUserRepository extends CrudRepository<MeetingUser,Long> {
    //MeetingUser findByAbbreviationAndUsername (String abbreviation , String username);
    MeetingUser findByMeetingFullnameAndUsername (String meetingFullname , String username);
    List<MeetingUser> findByUsername(String username);
    List<MeetingUser> findByMeetingFullname(String meetingFullname);
    List<MeetingUser> findByMeetingFullnameAndIsMember(String meetingFullname,int isMember);
    MeetingUser findByMeetingFullnameAndUsernameAndIsMember(String meetingFullname,String username,int isMember);
}
