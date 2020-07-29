package fudan.se.lab2.domain;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Application {
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
    private int status;
    private Date createTime;
    private String meetingFullname;
    private String topics; //用，隔开

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChairUsername() {
        return chairUsername;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Application(String chairUsername, String abbreviation, String venue, Date holdingTime, Date ddlForSubmission, Date releaseDate, int status, Date createTime, String meetingFullname,String topics) {
        this.chairUsername = chairUsername;
        this.abbreviation = abbreviation;
        this.venue = venue;
        this.holdingTime = holdingTime;
        this.ddlForSubmission = ddlForSubmission;
        this.releaseDate = releaseDate;
        this.status = status;
        this.createTime = createTime;
        this.meetingFullname = meetingFullname;
        this.topics = topics;
    }

    public Application(String chairUsername, String abbreviation, int status) {
        this.chairUsername = chairUsername;
        this.abbreviation = abbreviation;
        this.status = status;
    }
    public Application(){}
}
