package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Contribution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends CrudRepository<Contribution,Long> {
    //Contribution findByContributor (String contributor);
    List<Contribution> findByContributor (String contributor);
    List<Contribution> findByMeetingFullname (String meetingFullname);
    //List<Contribution> findByContributorAndAbbreviation(String contributor, String abbreviation);
    Contribution findByTitle(String title);
    Contribution findByTitleAndContributor(String title,String contributor);
    List<Contribution> findByContributorAndMeetingFullname (String contributor,String meetingFullname);
    //Contribution findByAbbreviationAndContributorAndPath(String abbreviation,String contributor,String path);
    Contribution findByMeetingFullnameAndContributorAndTitle(String meetingFullname,String contributor,String title);
}
