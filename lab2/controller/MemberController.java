package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.GetAllotRequest;
import fudan.se.lab2.controller.request.GetContributionDetailRequest;
import fudan.se.lab2.controller.request.GetRebuttalRequest;
import fudan.se.lab2.controller.request.MakeEditRequest;
import fudan.se.lab2.service.MemberService;
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
public class MemberController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/user/myMeeting/handle/member/viewPaper")
    public ResponseEntity<?> getAllAllot (@RequestBody GetAllotRequest request){
        String meetingFullname = request.getMeetingFullname();
        String memberUserName = request.getMemberUsername();
        logger.info(meetingFullname+","+memberUserName);
        return  ResponseEntity.ok(memberService.getAllot(meetingFullname,memberUserName));
    }

    @PostMapping("/user/myMeeting/handle/member/edit/get")
    public ResponseEntity<?> getConDetail (@RequestBody GetContributionDetailRequest request){
        Long id = request.getId();
        return ResponseEntity.ok(memberService.getConDetail(id));
    }

    @PostMapping("/user/myMeeting/handle/member/edit")
    public ResponseEntity<?> makeEdit (@RequestBody MakeEditRequest request){
        int mark = request.getMark();
        String comment = request.getComment();
        String confidence = request.getConfidence();
        String username = request.getUsername();
        Long cid =request.getCid();
        Long id = request.getId();
        int editStatus = request.getEditStatus();
        logger.info("cid:"+cid+",member:"+username+",mark"+mark);
        return ResponseEntity.ok(memberService.makeEdit(cid,username,mark,comment,confidence,id,editStatus));
    }

    @PostMapping("/user/myMeeting/handle/member/hasEdit")
    public ResponseEntity<?>getEdittedPaper(@RequestBody GetAllotRequest request){
        String meetingFullname = request.getMeetingFullname();
        String memberUsername = request.getMemberUsername();
        logger.info(meetingFullname+","+memberUsername);
        return ResponseEntity.ok(memberService.hasEdit(meetingFullname,memberUsername));
    }

    @PostMapping("/user/myMeeting/handle/member/getRebuttal")
    public ResponseEntity<?>GetRebuttal(@RequestBody GetRebuttalRequest request){
        Long cid = request.getCid();
        return ResponseEntity.ok(memberService.getRebuttal(cid));
    }
}
