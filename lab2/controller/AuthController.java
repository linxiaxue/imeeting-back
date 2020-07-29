package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Author;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.repository.ContributionRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LBW
 */
@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthService authService;

    private JwtTokenUtil jwtTokenUtil;

    private ContributionRepository contributionRepository;


    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(AuthService authService,ContributionRepository contributionRepository) {
        this.authService = authService;
        this.contributionRepository = contributionRepository;
    }


    @PostMapping("/home/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.info("RegistrationForm: " + request.toString());
        String username = request.getUsername();
        String password = request.getPassword();
        String fullname = request.getFullname();
        String email = request.getEmail();
        String district = request.getDistrict();
        String workUnit = request.getWorkUnit();

        return ResponseEntity.ok(authService.register(username,password,fullname,email,district,workUnit));
    }

    @PostMapping("/home/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        logger.info("username: " + request.getUsername());
        logger.info("password: " + request.getPassword());

        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/user/application")
    public ResponseEntity<?>meetingApply(@RequestBody MeetingApplyRequest request){
        logger.info("meetingFullname:" + request.getMeetingFullname());
        logger.info("username:"+request.getChairUsername());
        logger.info(request.getTopics().toString());
        return ResponseEntity.ok(authService.application(request));
    }


    @PostMapping("/user/application/history")
    public ResponseEntity<?>applicationHistory(@RequestBody ApplicationHistoryRequest request){
        String username = request.getUsername();
        logger.info(username);
        return ResponseEntity.ok(authService.applicationsHistory(username));
    }

    @PostMapping ("/user/myMeeting")
    public ResponseEntity<?>meetingQuery(@RequestBody MyMeetingQueryRequest request){
        String username = request.getUsername();
        logger.info("username:"+username);
        return ResponseEntity.ok(authService.myMeeting(username));
    }

    @PostMapping("/user/myMeeting/handle")
    public ResponseEntity<?>CheckMeetingRole(@RequestBody CheckMeetingRole request){
        String meetingFullname = request.getMeetingFullname();
        String username = request.getUsername();
        String role =request.getRole();
        logger.info((meetingFullname+","+username));
        return ResponseEntity.ok(authService.checkMeetingRole(meetingFullname,username,role));
    }

    @GetMapping("/admin/check")
    public ResponseEntity<?>getMeetingApplication(){
        logger.info("admin get meeting application");
        return ResponseEntity.ok(authService.getMeetingApplication());
    }

    @PostMapping("/admin/check")
    public ResponseEntity<?>AdminCheck(@RequestBody AdminCheckRequest request){
        logger.info(request.getMeetingFullname());
        return ResponseEntity.ok(authService.adminCheck(request.getMeetingFullname(),request.getStatus()));
    }

    @PostMapping("/user/invited")
    public ResponseEntity<?>getInvitation(@RequestBody GetInvitationRequest request){

        String username = request.getUsername();
        logger.info(username);
        return ResponseEntity.ok(authService.getInvitation(username));

    }

    @PostMapping("/user/invited/handle")
    public ResponseEntity<?>handleInvitation(@RequestBody HandleInvitation request){
        Long id = request.getId();
        int status = request.getStatus();
        List<String> topics = request.getTopics();
        return ResponseEntity.ok(authService.handleInvitation(id,status,topics));
    }

    @PostMapping("/user/invited/getTopic")
    public ResponseEntity<?>getMeetingTopic(@RequestBody GetMeetingTopicsRequest request){
        logger.info(request.getMeetingFullname());
        return ResponseEntity.ok(authService.getMeetingTopic(request.getMeetingFullname()));
    }



    @GetMapping("/user/contribution")
    public ResponseEntity<?>getAllMeetings(){
        return ResponseEntity.ok(authService.getAllMeetings());
    }

    @PostMapping("/user/contribution/check")
    public ResponseEntity<?>checkContribution(@RequestBody CheckContributionValidRequest request){
        String username = request.getUsername();
        String meetingFullname = request.getMeetingFullname();
        logger.info(username+","+meetingFullname);
        return ResponseEntity.ok(authService.checkContribution(username,meetingFullname));
    }

    @PostMapping("user/contribution/makeContribution/fileUpload")
    public ResponseEntity<?>uploadFile(@RequestParam("file") MultipartFile file){
        logger.info(file.getName());
        return ResponseEntity.ok(authService.uploadFile(file));
    }

    @PostMapping("user/contribution/makeContribution")
    public ResponseEntity<?>uploadOtherInformation(@RequestBody ContributionMake request){
        logger.info(request.getMeetingFullname()+","+request.getContributor());
        String contributor = request.getContributor();
        String meetingFullname = request.getMeetingFullname();
        String filename = request.getFileName();
        String title = request.getTitle();
        String digest = request.getDigest();
        List<String> topics = request.getTopics();
        List<Author> authors = request.getAuthors();
        Long id = request.getId();
        logger.info(topics.toString());
        logger.info(authors.toString());
        return ResponseEntity.ok(authService.uploadOtherInformation(contributor,meetingFullname,filename,title,digest,topics,authors,id));
    }

    @PostMapping("user/contribution/modify/get")
    public ResponseEntity<?>ModifyGetOldMessage(@RequestBody GetContributionDetailRequest request){
        logger.info(request.getId().toString());
        return ResponseEntity.ok(authService.modifyGet(request.getId()));
    }

    @PostMapping("user/contribution/history")
    public ResponseEntity<?>allContributionHistory(@RequestBody AllContributionHistoryRequest request){
        logger.info(request.getUsername());
        String username = request.getUsername();
        return ResponseEntity.ok(authService.allContributionHistory(username));
    }

    @PostMapping("user/download")
    public String downLoad(HttpServletResponse response, @RequestBody DownloadRequest request) throws IOException {
        Long cid = request.getCid();
        Contribution contribution = contributionRepository.findById(cid).get();
        //String filePath = "C:/Users/dhg/Desktop/软件工程/pj与lab/离散作业8_林夏雪_18302010075.pdf_member1.pdf";
        String filePath = contribution.getPath();
        File file = new File(filePath );
        String[] s = filePath.split("/");
        String filename = s[s.length-1];
        logger.info(filePath);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/pdf;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
            byte[] buffer = new byte[1024];
            //文件输入流

            //输出流
            try (OutputStream os = response.getOutputStream(); FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {

                logger.info(e.getMessage());
            }


        }
        return "文件不存在";
    }

    /**
     * 预览pdf文件
     * @param cid
     */
    @RequestMapping(value = "/preview",method = RequestMethod.GET)
    public void pdfStreamHandler(Long cid,HttpServletRequest request,HttpServletResponse response) throws IOException {
        logger.info(cid.toString());
        Contribution contribution = contributionRepository.findById(cid).get();
        logger.info(contribution.getContributor());
        String filePath = contribution.getPath();
        //String filePath = "C:/Users/dhg/Desktop/软件工程/pj与lab/离散作业8_林夏雪_18302010075.pdf_member1.pdf";
        logger.info(filePath);
        File file = new File(filePath);
        if (file.exists()){
            byte[] data = null;

            try(FileInputStream  input = new FileInputStream(file)) {

                data = new byte[input.available()];

                int count = 0;
                while ((count = input.read(data)) > 0){
                    response.getOutputStream().write(data);
                }

                input.close();
            } catch (Exception e) {
                logger.error("pdf文件处理异常：" + e.getMessage());
            }

        }
    }


    @PostMapping("/user/myMeeting/handle/bbs")
    public ResponseEntity<?> getBbs(@RequestBody BbsRequest request){
        String meetingFullname = request.getMeetingFullname();
        String username = request.getUsername();
        return ResponseEntity.ok(authService.getBbs(meetingFullname,username));
    }

    @PostMapping("/user/myMeeting/handle/bbs/publish")
    public ResponseEntity<?> publishPost(@RequestBody PublishPostRequest request){
        Long cid = request.getCid();
        String meetingFullname = request.getMeetingFullname();
        String publisher = request.getPublisher();
        return ResponseEntity.ok(authService.publishPost(cid,meetingFullname,publisher));
    }

    @PostMapping("/user/myMeeting/handle/bbs/reply")
    public ResponseEntity<?>reply(@RequestBody ReplyPostRequest request){
        Long id = request.getId();
        String replyer = request.getReplyer();
        String text = request.getText();
        return ResponseEntity.ok(authService.reply(id,replyer,text));

    }


    @PostMapping("/user/myMeeting/handle/bbs/details")
    public ResponseEntity<?>postDetails(@RequestBody PostDetailsRequest request){
        Long id = request.getId();
        return ResponseEntity.ok(authService.postDetails(id));
    }

    @PostMapping("/user/myMeeting/handle/bbs/pulish/getCid")
    public ResponseEntity<?>publishGetCid(@RequestBody PublishGetCidRequest request){
        String meetingFullname = request.getMeetingFullname();
        String username = request.getUsername();
        return ResponseEntity.ok(authService.publishGetCid(meetingFullname,username));
    }






    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to 2020 Software Engineering Lab2 xxx. ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }



}



