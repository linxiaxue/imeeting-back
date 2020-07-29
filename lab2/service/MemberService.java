package fudan.se.lab2.service;

import fudan.se.lab2.controller.AuthController;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.CanNotEditException;
import fudan.se.lab2.exception.HasMadeEditException;
import fudan.se.lab2.exception.NotDiscussException;
import fudan.se.lab2.exception.NotRebuttalException;
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
public class MemberService {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private ContributionMemberCommentRepository contributionMemberCommentRepository;
    private ContributionRepository contributionRepository;
    private ContributionMeetingTopicRepository contributionMeetingTopicRepository;
    private AuthorRepository authorRepository;
    private MeetingRepository meetingRepository;
    private RebuttalRepository rebuttalRepository;
    private PublishRepository publishRepository;
    private ReplyRepository replyRepository;

    @Autowired
    public MemberService(ContributionMemberCommentRepository contributionMemberCommentRepository,ContributionRepository contributionRepository,ContributionMeetingTopicRepository contributionMeetingTopicRepository,AuthorRepository authorRepository,MeetingRepository meetingRepository,RebuttalRepository rebuttalRepository,PublishRepository publishRepository,ReplyRepository replyRepository) {
        this.contributionMemberCommentRepository = contributionMemberCommentRepository;
        this.contributionRepository = contributionRepository;
        this.contributionMeetingTopicRepository = contributionMeetingTopicRepository;
        this.authorRepository = authorRepository;
        this.meetingRepository = meetingRepository;
        this.rebuttalRepository = rebuttalRepository;
        this.publishRepository = publishRepository;
        this.replyRepository = replyRepository;
    }

    public List<RePCGetAllot> getAllot(String meetingFullname, String memberUsername){
        List<ContributionMemberComment> contributionMemberComments = contributionMemberCommentRepository.findByMeetingFullnameAndMemberUsername(meetingFullname,memberUsername);
        //List<Contribution> contributions = new ArrayList<>();
        List<RePCGetAllot> contributions = new ArrayList<>();
        for(ContributionMemberComment c :contributionMemberComments){
            Contribution contribution = contributionRepository.findById(c.getCId()).get();
            RePCGetAllot re = new RePCGetAllot(contribution.getId(),contribution.getContributor(),contribution.getStatus(),contribution.getCreateTime(),contribution.getTitle(),contribution.getMeetingFullname(),c.getStatus());
            contributions.add(re);
        }
        return contributions;
    }

    public ReContribution getConDetail (Long id){
        ReContribution reContribution;
        Contribution contribution = contributionRepository.findById(id).get();
        List<String> topics = new ArrayList();
        List<ContributionMeetingTopic> cs = contributionMeetingTopicRepository.findByCId(id);
        for(ContributionMeetingTopic c : cs){
            topics.add(c.getTopic());
        }
        List<Author> authors = authorRepository.findByCId(id);
        reContribution = new ReContribution(contribution,authors,topics);
        ContributionMemberComment contributionMemberComment = contributionMemberCommentRepository.findByCIdAndMemberUsername(id, SecurityContextHolder.getContext().getAuthentication().getName());
        if(contributionMemberComment.getComment()==null){
            reContribution.setComment(null);
        }else {
            reContribution.setComment(contributionMemberComment.getComment());
        }
        if(contributionMemberComment.getConfidence()==null){
            reContribution.setConfidence(null);
        }else {
            reContribution.setConfidence(contributionMemberComment.getConfidence());
        }
        reContribution.setMark(contributionMemberComment.getMark());
        reContribution.setMemberUsername(contributionMemberComment.getMemberUsername());
        reContribution.setEditStatus(contributionMemberComment.getStatus());
        reContribution.setId(contributionMemberComment.getId());

        return reContribution;
    }

    public String makeEdit(Long cid,String username,int mark,String comment,String confidence,Long id,int editStatus){
        if(id==null) { //初次审稿
            ContributionMemberComment contributionMemberComment = contributionMemberCommentRepository.findByCIdAndMemberUsername(cid, username);
            if(contributionMemberComment.getStatus()!=0){
                throw new HasMadeEditException();
            }
            contributionMemberComment.setComment(comment);
            contributionMemberComment.setConfidence(confidence);
            contributionMemberComment.setMark(mark);
            contributionMemberComment.setStatus(1); //初次审核
            contributionMemberCommentRepository.save(contributionMemberComment);
            List<ContributionMemberComment> comments = contributionMemberCommentRepository.findByCId(cid);
            int i = 0;
            for (ContributionMemberComment c : comments) {
                if (c.getComment() == null) {
                    logger.info("未全部评审");
                    break;
                } else {
                    i++;
                }
            }
            if (i == 3) {//这篇文章已经评审完了
                Contribution contribution = contributionRepository.findById(cid).get();
                contribution.setStatus(1); //对稿件，初次审核
                contributionRepository.save(contribution);
            }
            Meeting meeting = meetingRepository.findByMeetingFullname(contributionRepository.findById(cid).get().getMeetingFullname());
            List<Contribution> contributions = contributionRepository.findByMeetingFullname(meeting.getMeetingFullname());
            boolean isAllEdit = true;
            for (Contribution c:contributions){
                if(c.getStatus()!=1){
                    isAllEdit = false;
                }
            }
            if (isAllEdit){
                meeting.setStatus(3); //初次审稿结束
                meetingRepository.save(meeting);
            }


        }else { //修改意见

            ContributionMemberComment contributionMemberComment = contributionMemberCommentRepository.findById(id).get();
            if (editStatus!=2 && editStatus!=4){
                throw new CanNotEditException();
            }else {
                if(comment!=null) { //有修改意见
                    if(publishRepository.findByCid(cid)==null){
                        logger.info("没有讨论");
                        throw new NotDiscussException();
                        //没有讨论不能改
                    }else {
                        logger.info("有讨论贴");
                        Long pid = publishRepository.findByCid(cid).getId();
                        if(replyRepository.findByPidOrderByCreateTime(pid).isEmpty()){
                            logger.info("没有发言");
                            throw new NotDiscussException();
                        }

                    }
                    contributionMemberComment.setStatus(editStatus);
                    contributionMemberComment.setMark(mark);
                    contributionMemberComment.setConfidence(confidence);
                    contributionMemberComment.setComment(comment);
                    contributionMemberCommentRepository.save(contributionMemberComment);
                }else { //确认不修改
                    if(publishRepository.findByCid(cid)==null){
                        logger.info("没有讨论");
                        throw new NotDiscussException();
                        //没有讨论不能改
                    }else {
                        logger.info("有讨论贴");
                        Long pid = publishRepository.findByCid(cid).getId();
                        if(replyRepository.findByPidOrderByCreateTime(pid).isEmpty()){
                            logger.info("没有发言");
                            throw new NotDiscussException();
                        }

                    }
                    contributionMemberComment.setStatus(editStatus);
                    contributionMemberCommentRepository.save(contributionMemberComment);
                }
            }
            List<ContributionMemberComment> comments = contributionMemberCommentRepository.findByCId(cid);
            boolean firstChange = true;
            boolean secondChange = true;
            for (ContributionMemberComment c : comments) {
                if(c.getStatus()!=2){
                    firstChange=false;
                }
                if(c.getStatus()!=4){
                    secondChange=false;
                }
            }
            if (firstChange) {//这篇文章已经全部初次修改 or 确认完毕了
                Contribution contribution = contributionRepository.findById(cid).get();
                contribution.setStatus(2); //对稿件，初次审核完毕
                contributionRepository.save(contribution);
            }
            if(secondChange){
                Contribution contribution = contributionRepository.findById(cid).get();
                contribution.setStatus(4); //对稿件，最终修改完毕
                contributionRepository.save(contribution);
            }


        }
        return "成功提交评审意见";
    }

    public List<ContributionMemberComment> hasEdit (String meetingFullname,String memberUsername){
        List<ContributionMemberComment> re = new ArrayList<>();
        List<ContributionMemberComment> contributionMemberComments = contributionMemberCommentRepository.findByMeetingFullnameAndMemberUsername(meetingFullname,memberUsername);
        for (ContributionMemberComment contributionMemberComment:contributionMemberComments){
            if(contributionMemberComment.getStatus()==1){
                re.add(contributionMemberComment);
            }
        }
        return re;
    }

    public Rebuttal getRebuttal(Long cid){
        if(rebuttalRepository.findByCid(cid)==null){
            throw  new NotRebuttalException();
        }else {
            return rebuttalRepository.findByCid(cid);
        }
    }


}
