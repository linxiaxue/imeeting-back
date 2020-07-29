package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Publish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String meetingFullname;
    private String publisher;
    private Date publishTime;
    private String title;
    private Long cid;

    public Publish() {
    }

    public Publish(String meetingFullname, String publisher, Date publishTime, String title, Long cid) {
        this.meetingFullname = meetingFullname;
        this.publisher = publisher;
        this.publishTime = publishTime;
        this.title = title;
        this.cid = cid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }


}
