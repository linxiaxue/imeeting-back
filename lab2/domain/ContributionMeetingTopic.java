package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity(name = "CONTRIBUTION_MEETING_TOPIC")
public class ContributionMeetingTopic {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long CId;
    private String meetingFullname;
    private String topic;

    public ContributionMeetingTopic(Long CId, String meetingFullname, String topic) {
        this.CId = CId;
        this.meetingFullname = meetingFullname;
        this.topic = topic;
    }

    public ContributionMeetingTopic() {
    }

    public Long getCId() {
        return CId;
    }

    public void setCId(Long CId) {
        this.CId = CId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
