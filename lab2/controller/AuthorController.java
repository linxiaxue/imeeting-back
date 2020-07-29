package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ContributionHistoryOfOneMeetingRequest;
import fudan.se.lab2.controller.request.GetEditResultRequest;
import fudan.se.lab2.controller.request.GetRebuttalRequest;
import fudan.se.lab2.controller.request.SetRebuttalRequest;
import fudan.se.lab2.service.AuthorService;
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
public class AuthorController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("user/mtMeeting/handle/author/contributionHistory")
    public ResponseEntity<?> oneContributionHistory(@RequestBody ContributionHistoryOfOneMeetingRequest request){
        logger.info(request.getUsername());
        logger.info(request.getMeetingFullname());
        String username = request.getUsername();
        String meetingFullname = request.getMeetingFullname();
        return ResponseEntity.ok(authorService.contributionHistoryOfOneMeeting(username,meetingFullname));
    }

    @PostMapping("/user/myMeeting/handle/author/viewEditResult")
    public ResponseEntity<?> viewEditResult(@RequestBody GetEditResultRequest request){
        Long id = request.getId();
        return ResponseEntity.ok(authorService.viewEditResult(id));
    }

    @PostMapping("/user/myMeeting/handle/author/rebuttal")
    public ResponseEntity<?> rebuttal(@RequestBody SetRebuttalRequest request){
        Long cid = request.getCid();
        String username = request.getUsername();
        String text = request.getText();
        logger.info("cid:"+cid+"username:"+username+"text:"+text);
        return ResponseEntity.ok(authorService.rebuttal(cid,username,text));

    }

    @PostMapping("/user/myMeeting/handle/author/unRebuttal")
    public ResponseEntity<?> unRebuttal(@RequestBody GetRebuttalRequest request){
        Long cid = request.getCid();
        return ResponseEntity.ok(authorService.unRebuttal(cid));
    }
}
