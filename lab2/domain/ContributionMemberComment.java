package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity(name = "Contribution_Member_Comment")

public class ContributionMemberComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long CId;
    private String memberUsername;
    private int mark;
    private String comment;
    private String confidence;
    private String meetingFullname;
    private int status; //0:待审核，1：待初次讨论，2：初次评审结束；3：待讨论rebuttal；4：最终审核结束

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public ContributionMemberComment(Long CId, String memberUsername,String meetingFullname) {
        this.CId = CId;
        this.memberUsername = memberUsername;
        this.meetingFullname = meetingFullname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCId() {
        return CId;
    }

    public void setCId(Long CId) {
        this.CId = CId;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public ContributionMemberComment() {
    }
}
