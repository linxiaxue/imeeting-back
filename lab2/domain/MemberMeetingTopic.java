package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity(name = "MEMBER_MEETING_TOPIC")
public class MemberMeetingTopic {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String memberName;
    private String meetingFullname;
    private String topic;

    public MemberMeetingTopic(String memberName, String meetingFullname, String topic) {
        this.memberName = memberName;
        this.meetingFullname = meetingFullname;
        this.topic = topic;
    }

    public MemberMeetingTopic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
