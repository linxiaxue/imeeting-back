package fudan.se.lab2.domain;
import javax.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String chairUsername;
    private String abbreviation;
    private String venue;
    private Date holdingTime;
    private Date ddlForSubmission;
    private Date releaseDate;
    private String meetingFullname;
    private int status; //0：不能投稿，1：投稿中，2：开启审稿，3：初次审稿结束，4：初次评审结果发布，5：评审结果修改中，6：最终评审结果发布
    private int strategy; //审稿策略
    private int isAllotSuccess; //记录是否审稿成功，0为否，1为是

    @ManyToMany(mappedBy = "meetings")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @ManyToMany(cascade =CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Topic> topics = new HashSet<>();

    public Meeting(String chairUserName, String abbreviation,  String venue, Date holdingTime, Date ddlForSubmission, Date releaseDate,String meetingFullname) {
        this.chairUsername = chairUserName;
        this.abbreviation = abbreviation;
        this.venue = venue;
        this.holdingTime = holdingTime;
        this.ddlForSubmission = ddlForSubmission;
        this.releaseDate = releaseDate;
        this.status = 1;
        this.meetingFullname = meetingFullname;

    }

    public int getIsAllotSuccess() {
        return isAllotSuccess;
    }

    public void setIsAllotSuccess(int isAllotSuccess) {
        this.isAllotSuccess = isAllotSuccess;
    }

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public Meeting(String chairUsername, String meetingFullname) {
        this.chairUsername = chairUsername;
        this.meetingFullname = meetingFullname;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getChairUsername() {
        return chairUsername;
    }

    public void setChairUsername(String chairUsername) {
        this.chairUsername = chairUsername;
    }


    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }



    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Date getHoldingTime() {
        return holdingTime;
    }

    public void setHoldingTime(Date holdingTime) {
        this.holdingTime = holdingTime;
    }

    public Date getDdlForSubmission() {
        return ddlForSubmission;
    }

    public void setDdlForSubmission(Date ddlForSubmission) {
        this.ddlForSubmission = ddlForSubmission;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Meeting(){}
}
