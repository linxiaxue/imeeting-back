package fudan.se.lab2.service;

import fudan.se.lab2.controller.AuthController;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.CanNotEditException;
import fudan.se.lab2.exception.CanNotPublishEditException;
import fudan.se.lab2.exception.UserHasBeenInvitedException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ChairService {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private UserRepository userRepository;
    private InvitationRepository invitationRepository;
    private MeetingRepository meetingRepository;
    private MeetingUserRepository meetingUserRepository;
    private ContributionRepository contributionRepository;
    private ContributionMemberCommentRepository contributionMemberCommentRepository;
    private AuthorRepository authorRepository;
    private MemberMeetingTopicRepository memberMeetingTopicRepository;
    private ContributionMeetingTopicRepository contributionMeetingTopicRepository;

    @Autowired
    public ChairService(UserRepository userRepository, InvitationRepository invitationRepository, MeetingRepository meetingRepository, MeetingUserRepository meetingUserRepository, ContributionRepository contributionRepository, ContributionMemberCommentRepository contributionMemberCommentRepository, AuthorRepository authorRepository, MemberMeetingTopicRepository memberMeetingTopicRepository,ContributionMeetingTopicRepository contributionMeetingTopicRepository) {
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.meetingRepository = meetingRepository;
        this.meetingUserRepository = meetingUserRepository;
        this.contributionRepository = contributionRepository;
        this.contributionMemberCommentRepository = contributionMemberCommentRepository;
        this.authorRepository = authorRepository;
        this.memberMeetingTopicRepository = memberMeetingTopicRepository;
        this.contributionMeetingTopicRepository = contributionMeetingTopicRepository;
    }


    //根据全名搜索用户
    public List<User> handleChairSearchUser(String fullname){
        List<User> users = new ArrayList<>();
        users=userRepository.findByFullname(fullname);
        return users;
    }

    public List<Invitation> invitationHistory (String username){
        List<Invitation> list = invitationRepository.findByChairName(username);
        return list;
    }

    //发送邀请
    public String handleChairMakeInvitation(String chairName,String inviteeName,String meetingFullname) throws UserHasBeenInvitedException {
        if(invitationRepository.findByMeetingFullnameAndChairNameAndInviteeName(meetingFullname,chairName,inviteeName)==null) {
            Date createTime = new Date();
            Invitation invitation = new Invitation(chairName, inviteeName, meetingFullname, 0, createTime);
            invitationRepository.save(invitation);
            return "邀请成功";
        }else {
            throw new UserHasBeenInvitedException();
        }
    }

    //改变会议投稿许可状态
    public String handleChangeContributionValid(String meetingFullname,int status){
        Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
        meeting.setStatus(status);
        meetingRepository.save(meeting);
        return "修改成功";
    }

    @Transactional
    public String handleOpenEdit(String meetingFullname,int status,int strategy){
        Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
        if(meeting.getStatus()==0){
            throw new CanNotEditException();
        }

        switch (strategy){
            case 1:
                allotByBurden(meetingFullname);
                break;
            case 2:
                allotByTopic(meetingFullname);
                break;
        }
        meeting.setStatus(status); //此时状态应该是2
        meeting.setStrategy(strategy);
        meetingRepository.save(meeting);
        return "开启审稿成功";
    }

    private void allotByBurden(String meetingFullname){
        logger.info("基于平均负担分配");

        List<MeetingUser> meetingUsers1 = meetingUserRepository.findByMeetingFullnameAndIsMember(meetingFullname,1);
        MeetingUser [] meetingUsers = meetingUsers1.toArray(new MeetingUser[meetingUsers1.size()]);
        if(meetingUsers.length<3){
            logger.info("member数小于3");
            throw new CanNotEditException();
        }
        List<Contribution> contributions = contributionRepository.findByMeetingFullname(meetingFullname);
        //先不在数据库中保存关系表，如果成功分配再保存
        for(Contribution contribution : contributions){
            contributionMemberCommentRepository.deleteAllByCId(contribution.getId());
        }
        List<ContributionMemberComment> allotTable = new ArrayList<>();
        //记录每个member分配的稿件数，最大值与最小值相差若大于1返回分配失败
        HashMap<String,Integer> allotNumbers =  new HashMap<>();
        //记录member与cid
        HashMap<String,Long> allot = new HashMap<>();
        for (int i=0;i<3*contributions.size();i++){
            int j = i % meetingUsers.length;
            int k = j;
            logger.info("j:"+j+","+"i:"+i+","+"i/3");
            //MeetingUser meetingUser = meetingUsers[j];
            Contribution contribution = contributions.get(i/3);
            while (isAuthor(contribution, meetingUsers[k])){
                logger.info(meetingUsers[k].getUsername());
                //向后寻找
                if(k==meetingUsers.length-1){
                    k=0;
                }else {
                    k++;
                }
            }
            if(allot.get(meetingUsers[k].getUsername())==contribution.getId()){
                logger.info("没有足够的member");
                throw new CanNotEditException();
            }
            if(k!=j){
                //交换位置
                MeetingUser m = meetingUsers[j];
                meetingUsers[j] = meetingUsers[k];
                meetingUsers[k] = m;
            }
            //记录每人分到的稿件数
            String memberName = meetingUsers[j].getUsername();
            ContributionMemberComment contributionMemberComment = new ContributionMemberComment(contribution.getId(),memberName,meetingFullname);
            allot.put(memberName,contribution.getId());
            allotTable.add(contributionMemberComment);
            if(allotNumbers.containsKey(memberName)){
                allotNumbers.replace(memberName,allotNumbers.get(memberName)+1);
            }else {
                allotNumbers.put(memberName,1);
            }

        }
        logger.info(allotNumbers.toString());
        ArrayList<Map.Entry<String,Integer>> arrayList = new ArrayList<>(allotNumbers.entrySet());
        arrayList.sort(Comparator.comparing(Map.Entry::getValue));
        logger.info(arrayList.get(0).getValue().toString()+","+arrayList.get(arrayList.size()-1).getValue().toString());
        if(arrayList.get(arrayList.size()-1).getValue()-arrayList.get(0).getValue()>1){
            logger.info("不能平均");
            throw new CanNotEditException();
        }else {
            logger.info("成功分配");
            Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
            meeting.setIsAllotSuccess(1);
            meetingRepository.save(meeting);
            for (ContributionMemberComment c:allotTable){
                contributionMemberCommentRepository.save(c);
            }
        }

    }


    private void allotByTopic(String meetingFullname){
        logger.info("基于topic相关度分配");
        List<Contribution> contributions = contributionRepository.findByMeetingFullname(meetingFullname);
        for(Contribution contribution : contributions){
            contributionMemberCommentRepository.deleteAllByCId(contribution.getId());
        }
        List<MemberMeetingTopic> allMemberMeetingTopics = memberMeetingTopicRepository.findByMeetingFullname(meetingFullname);
        List<MeetingUser> allMembers = new ArrayList<>();
        for (MemberMeetingTopic m : allMemberMeetingTopics){
            allMembers.add(meetingUserRepository.findByMeetingFullnameAndUsername(meetingFullname,m.getMemberName()));
        }
        Collections.shuffle(allMembers);
        if(allMembers.size()<3){
            logger.info("1");
            throw new CanNotEditException();
        }
        for (Contribution contribution:contributions){
            logger.info(contribution.getId().toString());
            List<ContributionMeetingTopic> contributionMeetingTopics = contributionMeetingTopicRepository.findByCId(contribution.getId());
            List<MeetingUser> members = new ArrayList<>();
            List<MemberMeetingTopic> memberMeetingTopics = new ArrayList<>();
            for (ContributionMeetingTopic c:contributionMeetingTopics){
                 memberMeetingTopics.addAll(memberMeetingTopicRepository.findByMeetingFullnameAndTopic(meetingFullname,c.getTopic())) ;
            }
            for (MemberMeetingTopic m :memberMeetingTopics){
                boolean tag = true;
                for (ContributionMeetingTopic c:contributionMeetingTopics){
                    if (memberMeetingTopicRepository.findByMemberNameAndMeetingFullnameAndTopic(m.getMemberName(),meetingFullname,c.getTopic())==null){
                        tag = false;
                    }
                }
                if(m.getMemberName()!=contribution.getContributor()&tag&!members.contains(meetingUserRepository.findByMeetingFullnameAndUsername(meetingFullname, m.getMemberName()))) {
                    members.add(meetingUserRepository.findByMeetingFullnameAndUsername(meetingFullname, m.getMemberName()));
                    logger.info(m.getMemberName());

                }
            }
            if(members.size()<3){
                logger.info("在所有member里随机");
                int j = 0;
                for (MeetingUser allMember : allMembers) {
                    if (!isAuthor(contribution, allMember)) {
                        ContributionMemberComment contributionMemberComment1 = new ContributionMemberComment(contribution.getId(), allMember.getUsername(), meetingFullname);
                        contributionMemberCommentRepository.save(contributionMemberComment1);
                        logger.info(contribution.getId().toString()+","+allMember.getUsername());
                        j++;
                    }
                    if (j==3){
                        break;
                    }
                }
                if(j<3){
                    logger.info("2");
                    throw new CanNotEditException();
                }
            }else {
                int j = 0;
                for (MeetingUser member : members) {
                    if (!isAuthor(contribution, member)) {
                        ContributionMemberComment contributionMemberComment1 = new ContributionMemberComment(contribution.getId(), member.getUsername(), meetingFullname);
                        contributionMemberCommentRepository.save(contributionMemberComment1);
                        logger.info(contribution.getId().toString()+","+member.getUsername());
                        j++;
                    }
                    if (j==3){
                        break;
                    }
                }
                if(j<3){
                    logger.info("3");
                    throw new CanNotEditException();
                }
            }

        }

    }



    private Boolean isAuthor(Contribution contribution, MeetingUser meetingUser){
        boolean isAuthor = false;
        User user = userRepository.findByUsername(meetingUser.getUsername());
        List<Author> authors = authorRepository.findByCId(contribution.getId());
        logger.info(contribution.getId().toString());
        logger.info(contribution.getContributor()+","+contribution.getMeetingFullname());
        if(contribution.getContributor().equals(meetingUser.getUsername())){
            isAuthor= true;
            logger.info(meetingUser.getUsername()+"is contributor of this paper");
        }
        for(Author author:authors){
            if (author.getName()==user.getFullname()&author.getEmail()==user.getEmail()){
                isAuthor = true;
                logger.info(meetingUser.getUsername()+"is author of this paper");
                break;
            }
        }
        if(contributionMemberCommentRepository.findByCIdAndMemberUsername(contribution.getId(),meetingUser.getUsername())!=null){
            isAuthor = true;
            logger.info("已经分配过");
        }
        return isAuthor;
    }

    public List<SimpleUser> getPCMember(String meetingFullname){
        List<MeetingUser> meetingUsers = meetingUserRepository.findByMeetingFullname(meetingFullname);
        List<SimpleUser> member = new ArrayList<>();
        for(MeetingUser meetingUser : meetingUsers){
            if(meetingUser.getIsMember()==1){
                User user = userRepository.findByUsername(meetingUser.getUsername());
                SimpleUser simpleUser = new SimpleUser(user.getUsername(),user.getFullname(),user.getEmail(),user.getDistrict(),user.getWorkUnit());
                member.add(simpleUser);
            }
        }
        return member;
    }

    //发布评审结果
    public String publishEditResult(String meetingFullname,int status){
        List<Contribution> contributions = contributionRepository.findByMeetingFullname(meetingFullname);
        String re ="";
        Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
        if(status ==1 & meeting.getStatus()==3) {
            //初次发布评审结果，需要每篇稿子的状态都为2
            for (Contribution contribution : contributions) {
                logger.info(((Integer) contribution.getStatus()).toString());
                if (contribution.getStatus() != 2) {
                    throw new CanNotPublishEditException();
                }
            }
            //如果每篇都为2
            for (Contribution contribution : contributions) {
                List<ContributionMemberComment> contributionMemberComments = contributionMemberCommentRepository.findByCId(contribution.getId());
                Boolean isPass = true;
                for (ContributionMemberComment c:contributionMemberComments){
                    if (c.getMark()<0){
                        isPass = false;
                    }
                }
                if(!isPass){
                    contribution.setStatus(2);//未通过，待提交rebuttal
                    for (ContributionMemberComment c:contributionMemberComments){
                        c.setStatus(2); // 待提交rebuttal
                        contributionMemberCommentRepository.save(c);
                    }
                    contributionRepository.save(contribution);
                }else {
                    contribution.setStatus(5);//初次审核录用
                    for (ContributionMemberComment c:contributionMemberComments){
                        c.setStatus(4); //最终审核完成
                        contributionMemberCommentRepository.save(c);
                    }
                    contributionRepository.save(contribution);
                }
            }
            //Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
            meeting.setStatus(4);
            meetingRepository.save(meeting);
            re = "初次评审结果发布成功";
        }
        if(status==2 & meeting.getStatus()==5){
            //最终发布审核结果
            for (Contribution contribution : contributions) {
                logger.info(((Integer) contribution.getStatus()).toString());
                if (contribution.getStatus()!=2&contribution.getStatus()!=4&contribution.getStatus()!=5) {
                    throw new CanNotPublishEditException();
                }if(contribution.getStatus()==2){
                    //状态仍为2，即author未提交rebuttal
                    contribution.setStatus(4);
                    contributionRepository.save(contribution);
                }
                List<ContributionMemberComment> contributionMemberComments = contributionMemberCommentRepository.findByCId(contribution.getId());

                Boolean isPass = true;
                for (ContributionMemberComment c:contributionMemberComments){
                    if (c.getMark()<0){
                        isPass = false;
                    }
                }
                if(!isPass){
                    contribution.setStatus(4);//最终审核未录用
                    contributionRepository.save(contribution);

                }else {
                    contribution.setStatus(5);//最终审核录用
                    contributionRepository.save(contribution);
                }
                contributionRepository.save(contribution);
            }
            //Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
            meeting.setStatus(6);
            meetingRepository.save(meeting);
            re = "最终评审结果发布成功";
        }
        //Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
        meeting.setIsAllotSuccess(1);
        meetingRepository.save(meeting);
        return re;
    }

    public List<Contribution> getAllpaper (String meetingFullname){
        List<Contribution> contributions = contributionRepository.findByMeetingFullname(meetingFullname);
        return contributions;
    }
}
