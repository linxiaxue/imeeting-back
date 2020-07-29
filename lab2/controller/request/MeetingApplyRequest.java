package fudan.se.lab2.controller.request;

import java.util.Date;
import java.util.List;

public class MeetingApplyRequest {

    private String chairUsername;
    private String abbreviation;
    private String venue;
    private Date holdingTime;
    private Date ddlForSubmission;
    private Date releaseDate;
    private String meetingFullname;

    private List<String> topics;

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getChairUsername() {
        return chairUsername;
    }

    public void setChairUsername(String chairUsername) {
        this.chairUsername = chairUsername;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
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
}
