package fudan.se.lab2.service;

import fudan.se.lab2.controller.AuthController;
import fudan.se.lab2.controller.request.MeetingApplyRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.*;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

/**
 * @author LBW
 */
@Service
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private ApplicationRepository applicationRepository;
    private InvitationRepository invitationRepository;
    private MeetingRepository meetingRepository;
    private MeetingUserRepository meetingUserRepository;
    private ContributionRepository contributionRepository;
    private JwtTokenUtil jwtTokenUtil;
    private TopicRepository topicRepository;
    private MemberMeetingTopicRepository memberMeetingTopicRepository;
    private ContributionMeetingTopicRepository contributionMeetingTopicRepository;
    private AuthorRepository authorRepository;
    private PublishRepository publishRepository;
    private ReplyRepository replyRepository;
    private ContributionMemberCommentRepository contributionMemberCommentRepository;

    //private final String filePath = "/usr/local/sanjiaomao/uploadfiles/";

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository, ApplicationRepository applicationRepository,InvitationRepository invitationRepository,MeetingRepository meetingRepository,MeetingUserRepository meetingUserRepository,ContributionRepository contributionRepository,JwtTokenUtil jwtTokenUtil,TopicRepository topicRepository,MemberMeetingTopicRepository memberMeetingTopicRepository,ContributionMeetingTopicRepository contributionMeetingTopicRepository,AuthorRepository authorRepository,PublishRepository publishRepository,ReplyRepository replyRepository,ContributionMemberCommentRepository contributionMemberCommentRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.applicationRepository = applicationRepository;
        this.invitationRepository = invitationRepository;
        this.meetingRepository = meetingRepository;
        this.meetingUserRepository = meetingUserRepository;
        this.contributionRepository = contributionRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.topicRepository = topicRepository;
        this.memberMeetingTopicRepository = memberMeetingTopicRepository;
        this.contributionMeetingTopicRepository = contributionMeetingTopicRepository;
        this.authorRepository = authorRepository;
        this.publishRepository = publishRepository;
        this.replyRepository = replyRepository;
        this.contributionMemberCommentRepository = contributionMemberCommentRepository;
    }





    public String register(String username,String password,String fullname,String email,String district,String workUnit)throws UsernameHasBeenRegisteredException {
        // TODO: Implement the function.
        //Set<String> authorities = request.getAuthorities();
        Authority authority = authorityRepository.findByAuthority("Reviewer");
        if (authority == null) {
            authority = new Authority("Reviewer");
            authorityRepository.save(authority);
        }
        User user;
        if(userRepository.findByUsername(username)!=null){
            throw new UsernameHasBeenRegisteredException(username);
        }else {
             user = new User(
                    username,
                    password,
                    fullname,
                    email,
                    district,
                    workUnit,
                    new HashSet<>(Collections.singletonList(authority))
            );
            userRepository.save(user);
        }
        return user.getUsername();
    }

    public HashMap<String, String> login(String username, String password)throws WrongPasswordException {

        JwtUserDetailsService jwtUserDetailsService = new JwtUserDetailsService(userRepository);

        UserDetails user = jwtUserDetailsService.loadUserByUsername(username);
        HashMap<String,String> re = new HashMap<>();
        if(!password.equals(user.getPassword())){
            throw new WrongPasswordException();
        }else {
            String jwt = jwtTokenUtil.generateToken((User) user);
            re.put("token",jwt);
            return re;
            //return "登陆成功";
        }

    }

    public String application(MeetingApplyRequest request) throws MeetingHasBeenAppliedException{
        String chairUsername = request.getChairUsername();
        String abbreviation = request.getAbbreviation();
        String meetingFullname = request.getMeetingFullname();
        String venue = request.getVenue();
        Date holdingTime = request.getHoldingTime();
        Date ddlForSubmission = request.getDdlForSubmission();
        Date releaseDate = request.getReleaseDate();
        Date createTime = new Date();
        List<String> topics = request.getTopics();
        int status = 0;

        if(applicationRepository.findByMeetingFullname(meetingFullname)!=null || meetingRepository.findByMeetingFullname(meetingFullname)!=null){
            throw new MeetingHasBeenAppliedException();
        }else {
            String topics1="";
            for(String to:topics){
                if(to.equals(topics.get(0))){
                    topics1 += to;
                }else {
                    topics1 += ","+to;
                }
            }
            logger.info(topics1);
            Application newMeeting = new Application(chairUsername,abbreviation,venue,holdingTime,ddlForSubmission,releaseDate,status,createTime,meetingFullname,topics1);

            applicationRepository.save(newMeeting);
            return "申请成功";
        }

    }

    public List<Application> getMeetingApplication(){
        List<Application> applicationList = applicationRepository.findByStatus(0);
        return applicationList;
    }

    public Set<Meeting> myMeeting(String username){
        User user = userRepository.findByUsername(username);
        return user.getMeetings();
    }

    public String checkMeetingRole(String meetingFullname,String username,String role){
        MeetingUser meetingUser = meetingUserRepository.findByMeetingFullnameAndUsername(meetingFullname,username);
        int valid ;
        switch (role){
            case "chair":
                valid = meetingUser.getIsChair();
                break;
            case "member":
                valid = meetingUser.getIsMember();
                break;
            case "author":
                valid = meetingUser.getIsAuthor();
                break;
            default:
                valid = 0;
        }
        if(valid == 0){
            throw new NotHaveRoleException(); //身份错误
        }else {
            return "操作成功";
        }
    }

    public String adminCheck(String meetingFullname,int status){
        Application application = applicationRepository.findByMeetingFullname(meetingFullname);
        if(status==1){
            Meeting newMeeting = new Meeting(application.getChairUsername(),application.getAbbreviation(),application.getVenue(),application.getHoldingTime(),application.getDdlForSubmission(),application.getReleaseDate(),application.getMeetingFullname());
            String[] topics = application.getTopics().split(",");
            for(String s:topics){
                if(topicRepository.findByTopicName(s)==null){
                    Topic topic = new Topic(s);
                    topicRepository.save(topic);
                    newMeeting.getTopics().add(topic);
                }else {
                    Topic topic = topicRepository.findByTopicName(s);
                    newMeeting.getTopics().add(topic);
                }
                MemberMeetingTopic memberMeetingTopic = new MemberMeetingTopic(application.getChairUsername(),meetingFullname,s);
                memberMeetingTopicRepository.save(memberMeetingTopic);
            }
            newMeeting.setStatus(1);
            meetingRepository.save(newMeeting);//建立新会议
            User chair = userRepository.findByUsername(application.getChairUsername());
            chair.getMeetings().add(newMeeting);
            userRepository.save(chair); //建立关系
            MeetingUser meetingUser = new MeetingUser(application.getMeetingFullname(),application.getChairUsername(),1,1,0);
            meetingUserRepository.save(meetingUser); //记录角色
            application.setStatus(status);//改变申请表中的状态
        }else {
            application.setStatus(status);
        }
        applicationRepository.save(application);
        return "操作成功";
    }

    public List<Invitation> getInvitation(String inviteeName){
        List<Invitation> invitations = invitationRepository.findByInviteeNameAndStatus(inviteeName,0);
        return invitations;
    }

    public List<String> getMeetingTopic(String meetingFullname){
        Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
        List<String> topics = new ArrayList<>();
        Set<Topic> to = meeting.getTopics();
        for (Topic topic : to){
            topics.add(topic.getTopicName());
        }
        return topics;
    }

    public String handleInvitation(Long id,int status,List<String> topics){
        Invitation invitation = invitationRepository.findById(id)
                .orElse(null);
        invitation.setStatus(status);
        invitationRepository.save(invitation);
        if(status==1){

            Meeting meeting = meetingRepository.findByMeetingFullname(invitation.getMeetingFullname());
            User invitee = userRepository.findByUsername(invitation.getInviteeName());
            invitee.getMeetings().add(meeting);
            userRepository.save(invitee);
            meetingRepository.save(meeting);
            if(meetingUserRepository.findByMeetingFullnameAndUsernameAndIsMember(meeting.getMeetingFullname(),invitee.getUsername(),1)==null){
            MeetingUser meetingUser = new MeetingUser(meeting.getMeetingFullname(),invitee.getUsername(),0,1,0);
            meetingUserRepository.save(meetingUser);
            }
            for (String topic:topics){
                Topic to = topicRepository.findByTopicName(topic);
                MemberMeetingTopic memberMeetingTopic = new MemberMeetingTopic(invitation.getInviteeName(),invitation.getMeetingFullname(),to.getTopicName());
                memberMeetingTopicRepository.save(memberMeetingTopic);

            }
        }

        return "操作成功";

    }



    public List<Application> applicationsHistory(String username){
        List<Application> list =applicationRepository.findByChairUsername(username);
        return list;
    }




    public List<Meeting> getAllMeetings(){
        List<Meeting> list = meetingRepository.findAllByOrderById();
        List<Meeting> list1 = new ArrayList<>();
        for(Meeting meeting:list){
            if(meeting.getStatus()==1){
                list1.add(meeting);
            }
        }
        return list1;
    }

    public List<String> checkContribution (String username,String meetingFullname) throws CanNotContributeException{
        Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
        List<String> topics = new ArrayList<>();
        Set<Topic> to = meeting.getTopics();
        for (Topic topic : to){
            topics.add(topic.getTopicName());
        }

        if(meetingUserRepository.findByMeetingFullnameAndUsername(meetingFullname,username)==null){
            return topics;
        }else {
            MeetingUser meetingUser = meetingUserRepository.findByMeetingFullnameAndUsername(meetingFullname,username);

            if (meetingUser.getIsChair() == 1 || meeting.getStatus()!=1) {
                throw new CanNotContributeException();
            } else {
                return topics;
            }
        }

    }

    public ReContribution modifyGet (Long id){
        ReContribution reContribution;
        Contribution contribution = contributionRepository.findById(id).get();
        List<String> topics = new ArrayList();
        List<ContributionMeetingTopic> cs = contributionMeetingTopicRepository.findByCId(id);
        for(ContributionMeetingTopic c : cs){
            topics.add(c.getTopic());
        }
        List<Author> authors = authorRepository.findByCId(id);
        reContribution = new ReContribution(contribution,authors,topics);
        return reContribution;

    }

    public String uploadFile(MultipartFile file){
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        //String[] fileNames = file.getOriginalFilename().split(".");

        String fileName = file.getOriginalFilename()+"_"+ SecurityContextHolder.getContext().getAuthentication().getName()+".pdf";
        logger.info(fileName);

        //String filePath = "C:/Users/dhg/Desktop/软件工程/pj与lab/";
        String filePath = "/usr/local/sanjiaomao/uploadfiles/";
        File dest = new File(filePath , fileName);
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return "上传失败!";

    }

    @Transactional
    public String uploadOtherInformation(String contributor,String meetingFullname,String filename,String title,String digest,List<String> tps,List<Author>ars,Long cid)throws PaperHasBeenSubmittedException{
        String filepath = "/usr/local/sanjiaomao/uploadfiles/"+filename+"_"+contributor+".pdf";
        List<String> topics = tps;
        List<Author> authors = ars;
        String re = "" ;

        //String filepath = "C:/Users/dhg/Desktop/软件工程/pj与lab/"+filename+"_"+contributor+".pdf";
        //不是修改，不可一稿多投
        if (contributionRepository.findByTitleAndContributor(title,contributor)!=null && contributionRepository.findByMeetingFullnameAndContributorAndTitle(meetingFullname,contributor,title)==null){
            throw  new PaperHasBeenSubmittedException();
        }else if(contributionRepository.findByMeetingFullnameAndContributorAndTitle(meetingFullname,contributor,title)!=null|| cid!=null){
            //修改投稿
            logger.info("修改投稿");
            Contribution contribution ;
            if(contributionRepository.findByMeetingFullnameAndContributorAndTitle(meetingFullname,contributor,title)!=null){
                contribution=contributionRepository.findByMeetingFullnameAndContributorAndTitle(meetingFullname,contributor,title);
            }else {
                contribution = contributionRepository.findById(cid).get();
            }
            Long id = contribution.getId();
            contribution.setDigest(digest);
            contribution.setCreateTime(new Date());
            contribution.setPath(filepath);
            contribution.setTitle(title);
            contribution.setStatus(0);
            contributionRepository.save(contribution);
            contributionMeetingTopicRepository.deleteAllByCId(id);//先删掉
            for(String topic :topics){ //再添加
                ContributionMeetingTopic contributionMeetingTopic = new ContributionMeetingTopic(id,meetingFullname,topic);
                contributionMeetingTopicRepository.save(contributionMeetingTopic);
            }
            authorRepository.deleteAllByCId(id);//先删掉
            for (Author author:authors){ //再添加
                author.setCId(id);
                authorRepository.save(author);
            }
            re = "修改投稿成功";

        }else {
            logger.info("正常投稿");
            Contribution contribution = new Contribution(contributor, meetingFullname, filepath, 0, new Date(), title, digest);
            contributionRepository.save(contribution);
            Long id = contributionRepository.findByMeetingFullnameAndContributorAndTitle(meetingFullname,contributor,title).getId();

            for(String topic :topics){
                ContributionMeetingTopic contributionMeetingTopic = new ContributionMeetingTopic(id,meetingFullname,topic);
                contributionMeetingTopicRepository.save(contributionMeetingTopic);
            }

            for (Author author:authors){
                author.setCId(id);
                authorRepository.save(author);
            }

            MeetingUser meetingUser = meetingUserRepository.findByMeetingFullnameAndUsername(meetingFullname, contributor);
            if (meetingUser == null) {
                meetingUser = new MeetingUser(meetingFullname, contributor, 0, 0, 1);
                meetingUserRepository.save(meetingUser);
                Meeting meeting = meetingRepository.findByMeetingFullname(meetingFullname);
                User user = userRepository.findByUsername(contributor);
                user.getMeetings().add(meeting);
                userRepository.save(user);
            } else {
                meetingUser.setIsAuthor(1);
                meetingUserRepository.save(meetingUser);

            }
            re = "投稿成功";
        }
        return re;

    }

    public List<Contribution> allContributionHistory (String username){
        List<Contribution> list = contributionRepository.findByContributor(username);
        return list;
    }

    //讨论区界面，chair返回与所有稿件有关的帖子，member返回与他审核的稿件有关的帖子
    public List<Publish> getBbs(String meetingFullname,String username){
        List<Publish> re = new ArrayList<>();
        if(meetingUserRepository.findByMeetingFullnameAndUsername(meetingFullname,username).getIsChair()==1){
            List<Contribution> contributions = contributionRepository.findByMeetingFullname(meetingFullname);
            for (Contribution c:contributions){
                if (publishRepository.findByCid(c.getId()) != null) {
                    re.add(publishRepository.findByCid(c.getId()));
                }
            }
        }else {
            List<ContributionMemberComment> contributionMemberComments = contributionMemberCommentRepository.findByMeetingFullnameAndMemberUsername(meetingFullname, username);
            for (ContributionMemberComment c : contributionMemberComments) {
                if (publishRepository.findByCid(c.getCId()) != null) {
                    re.add(publishRepository.findByCid(c.getCId()));
                }

            }
        }
        return  re;
    }

    public String publishPost(Long cid,String meetingFullname,String publisher){
        if(publishRepository.findByCid(cid)!=null){
            throw new CanNotPublishPostException();
        }else {
            Contribution contribution = contributionRepository.findById(cid).get();
            String title = "【讨论】"+contribution.getTitle();
            Publish publish = new Publish(meetingFullname,publisher,new Date(),title,cid);
            publishRepository.save(publish);
            return "发表成功";
        }
    }

    public String reply(Long id,String replyer,String text){
        Reply reply = new Reply(id,replyer,new Date(),text);
        replyRepository.save(reply);
        return "回复成功";
    }

    public Post postDetails(Long id){
        Publish publish = publishRepository.findById(id).get();
        List<Reply> replies = new ArrayList<>();
        replies = replyRepository.findByPidOrderByCreateTime(id);
        return new Post(id,publish.getTitle(),publish.getPublisher(),publish.getPublishTime(),replies);
    }

    //发帖时获取cid和title
    public List<Contribution> publishGetCid(String meetingFullname,String username){
        List<Contribution> re = new ArrayList<>();
        if(meetingUserRepository.findByMeetingFullnameAndUsername(meetingFullname,username).getIsChair()==1){
            re = contributionRepository.findByMeetingFullname(meetingFullname);
        }else {
            List<ContributionMemberComment> contributionMemberComments = contributionMemberCommentRepository.findByMeetingFullnameAndMemberUsername(meetingFullname, username);
            for (ContributionMemberComment c:contributionMemberComments){
                Contribution contribution = contributionRepository.findById(c.getCId()).get();
                re.add(contribution);
            }
        }
        return re;
    }



}
