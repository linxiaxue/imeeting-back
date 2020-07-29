package fudan.se.lab2.service;

import fudan.se.lab2.controller.AuthController;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.CanNotEditException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthorService {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private ContributionRepository contributionRepository;
    private MeetingRepository meetingRepository;
    private ContributionMemberCommentRepository contributionMemberCommentRepository;
    private RebuttalRepository rebuttalRepository;
    private PublishRepository publishRepository;
    private MeetingUserRepository meetingUserRepository;

    @Autowired
    public AuthorService(ContributionRepository contributionRepository,MeetingRepository meetingRepository,ContributionMemberCommentRepository contributionMemberCommentRepository,RebuttalRepository rebuttalRepository,PublishRepository publishRepository,MeetingUserRepository meetingUserRepository) {
        this.contributionRepository = contributionRepository;
        this.meetingRepository = meetingRepository;
        this.contributionMemberCommentRepository = contributionMemberCommentRepository;
        this.rebuttalRepository = rebuttalRepository;
        this.publishRepository = publishRepository;
        this.meetingUserRepository = meetingUserRepository;
    }

    public List<Contribution> contributionHistoryOfOneMeeting(String username, String meetingFullname){
        List<Contribution> list = contributionRepository.findByContributorAndMeetingFullname(username,meetingFullname);
        return list;
    }

    public List<ContributionMemberComment> viewEditResult(Long id){
        String meetingFullname = contributionRepository.findById(id).get().getMeetingFullname();
        Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
        if(meeting.getIsAllotSuccess()!=1){
            throw new CanNotEditException();
        }
        List<ContributionMemberComment> contributionMemberComments = contributionMemberCommentRepository.findByCId(id);
        return contributionMemberComments;
    }

    public String rebuttal(Long cid,String username,String text){
        if(rebuttalRepository.findByCid(cid)!=null){
            throw new RuntimeException();
        }else {
            Rebuttal rebuttal = new Rebuttal(cid, username, text, new Date());
            rebuttalRepository.save(rebuttal);
            Contribution contribution = contributionRepository.findById(cid).get();
            contribution.setStatus(3);
            contributionRepository.save(contribution);
            List<ContributionMemberComment> contributionMemberComments = contributionMemberCommentRepository.findByCId(cid);
            for (ContributionMemberComment c : contributionMemberComments) {
                c.setStatus(3);
                contributionMemberCommentRepository.save(c);
            }
            Meeting meeting = meetingRepository.findByMeetingFullname(contribution.getMeetingFullname());
            meeting.setStatus(5); //一有提交rebuttal就处于修改中
            meetingRepository.save(meeting);
            return "提交rebuttal成功";
        }
    }

    //不提交rebuttal
    public String unRebuttal(Long cid){
        if(rebuttalRepository.findByCid(cid)!=null){
            throw new RuntimeException();
        }else {
            Rebuttal rebuttal = new Rebuttal(cid, SecurityContextHolder.getContext().getAuthentication().getName(),null,new Date());
            rebuttalRepository.save(rebuttal);
            Contribution contribution = contributionRepository.findById(cid).get();
            contribution.setStatus(4); //该文章最终审核未录用
            contributionRepository.save(contribution);
            List<ContributionMemberComment> contributionMemberComments = contributionMemberCommentRepository.findByCId(cid);
            for (ContributionMemberComment c : contributionMemberComments) {
                c.setStatus(4);
                contributionMemberCommentRepository.save(c);
            }
            Meeting meeting = meetingRepository.findByMeetingFullname(contribution.getMeetingFullname());
            meeting.setStatus(5); //一有提交rebuttal就处于修改中
            meetingRepository.save(meeting);
            return "操作成功";
        }
    }



}
