package fudan.se.lab2.repository;

import fudan.se.lab2.domain.ContributionMemberComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionMemberCommentRepository extends CrudRepository<ContributionMemberComment,Long> {
    List<ContributionMemberComment> findByMemberUsername (String Memberusername);
    List<ContributionMemberComment> findByCId (Long CId);
    void deleteAllByCId (Long id);
    List<ContributionMemberComment> findByMeetingFullnameAndMemberUsername (String meetingFullname,String memberUsername);
    ContributionMemberComment findByCIdAndMemberUsername (Long cid,String username);
}
