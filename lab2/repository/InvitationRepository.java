package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Invitation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation,Long> {
    //Invitation findByInviteeName(String inviteeName);
    List<Invitation> findByInviteeName(String inviteeName);
    List<Invitation> findByInviteeNameAndStatus(String inviteeName,int status);
    Optional<Invitation> findById(Long id);
    //Invitation findByAbbreviationAndChairNameAndInviteeName(String abbreviation,String chairName,String inviteeName);
    Invitation findByMeetingFullnameAndChairNameAndInviteeName(String meetingFullname,String chairName,String inviteeName);
    List<Invitation> findByChairName (String chairName);

}
