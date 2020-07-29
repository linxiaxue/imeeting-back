package fudan.se.lab2.domain;

import java.util.Date;

public class RePCGetAllot {
    private Long id; //稿件id
    private String contributor;
    private int status; //状态码，0：初次审核未完成，1：初次修改审核信息，2：初次评审结束，3：待讨论rebuttal，4：最终审核未录用，5：已录用
    private Date createTime;
    //不可以题目和contributor同时相同
    private String title;
    private String meetingFullname;
    private int editStatus; //审稿状态

    public RePCGetAllot(Long id, String contributor, int status, Date createTime, String title, String meetingFullname, int editStatus) {
        this.id = id;
        this.contributor = contributor;
        this.status = status;
        this.createTime = createTime;
        this.title = title;
        this.meetingFullname = meetingFullname;
        this.editStatus = editStatus;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }
}
