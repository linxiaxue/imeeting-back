package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String chairName;
    private String inviteeName;
    //private String abbreviation;
    private int status; // 状态码，0为待处理，1为已接受，2为被拒绝
    private Date createTime;//邀请时间
    private String meetingFullname;


    public Invitation(String chairName, String inviteeName, String meetingFullname, int status, Date createTime) {
        this.chairName = chairName;
        this.inviteeName = inviteeName;
        this.meetingFullname = meetingFullname;
        this.status = status;
        this.createTime = createTime;
    }

    public Invitation(){}

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chairName) {
        this.chairName = chairName;
    }

    public String getInviteeName() {
        return inviteeName;
    }

    public void setInviteeName(String inviteeName) {
        this.inviteeName = inviteeName;
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
