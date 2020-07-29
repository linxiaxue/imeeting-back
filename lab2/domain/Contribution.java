package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Contribution {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String contributor;
    private String path;
    private int status; //状态码，0：初次审核未完成，1：初次修改审核信息，2：初次评审结束，3：待讨论rebuttal，4：最终审核未录用，5：已录用
    private Date createTime;
    //不可以题目和contributor同时相同
    private String title;
    private String digest;
    private String meetingFullname;

    //private List<SimpleUser> author ;

    public Contribution(String contributor, String meetingFullname, String path, int status, Date createTime, String title, String digest) {
        this.contributor = contributor;
        this.meetingFullname = meetingFullname;
        this.path = path;
        this.status = status;
        this.createTime = createTime;
        this.title = title;
        this.digest = digest;
    }
    public Contribution(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
