package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.service.ChairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ChairController {
    @Autowired
    private ChairService chairService;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    public ChairController(ChairService authService) {
        this.chairService = authService;
    }

    @PostMapping("/user/myMeeting/handle/chair/getMember")
    public ResponseEntity<?> getPCMember(@RequestBody GetPCMemberRequest request){
        String meetingFullname = request.getMeetingFullname();
        logger.info(meetingFullname);
        return ResponseEntity.ok(chairService.getPCMember(meetingFullname));
    }
    @PostMapping("/user/myMeeting/handle/chair/invitation")
    public ResponseEntity<?>chairSearchUser(@RequestBody SearchUserRequest request){
        String fullname = request.getFullname();
        logger.info(fullname);
        return ResponseEntity.ok(chairService.handleChairSearchUser(fullname));
    }

    @PostMapping("/user/myMeeting/handle/chair/invitation/makeInvitation")
    public ResponseEntity<?>chairMakeInvitation(@RequestBody MakeInvitationRequest request){
        String chairName = request.getChairName();
        String inviteeName = request.getInviteeName();
        String meetingFullname = request.getMeetingFullname();
        logger.info(chairName+","+inviteeName+","+meetingFullname);
        return  ResponseEntity.ok(chairService.handleChairMakeInvitation(chairName,inviteeName,meetingFullname));
    }

    @PostMapping("/user/myMeeting/handle/chair/invitation/history")
    public ResponseEntity<?>invitationHistory(@RequestBody InvitationHistoryRequest request){
        String username = request.getUsername();
        logger.info(username);
        return ResponseEntity.ok(chairService.invitationHistory(username));
    }


    @PostMapping("/user/myMeeting/handle/chair/opencon")
    public ResponseEntity<?>contributionValid(@RequestBody ChangeConValidRequest request){
        String meetingFullname = request.getMeetingFullname();
        int status = request.getStatus();
        logger.info(meetingFullname);
        return ResponseEntity.ok(chairService.handleChangeContributionValid(meetingFullname,status));
    }

    @PostMapping("/user/myMeeting/handle/chair/openEdit")
    public ResponseEntity<?>openEdit(@RequestBody OpenEditRequest request){
        String meetingFullname = request.getMeetingFullname();
        int status = request.getStatus();
        int strategy = request.getStrategy();
        logger.info(meetingFullname+","+status+","+strategy);
        return ResponseEntity.ok(chairService.handleOpenEdit(meetingFullname,status,strategy));
    }

    @PostMapping("/user/myMeeting/handle/chair/publishEditResult")
    public ResponseEntity<?>pulishEditResult (@RequestBody PublishEditResultRequest request){
        String meetingFullname = request.getMeetingFullname();
        int status = request.getStatus();
        logger.info(meetingFullname+","+status);
        return ResponseEntity.ok(chairService.publishEditResult(meetingFullname,status));

    }

    @PostMapping("/user/myMeeting/handle/chair/getAllPaper")
    public ResponseEntity<?>getAllPaper(@RequestBody GetMeetingAllPaperRequest request){
        String meetingFullname = request.getMeetingFullname();
        logger.info(meetingFullname);
        return ResponseEntity.ok(chairService.getAllpaper(meetingFullname));
    }

}
