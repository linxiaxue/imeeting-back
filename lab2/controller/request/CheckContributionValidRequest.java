package fudan.se.lab2.controller.request;

public class CheckContributionValidRequest {
    private String username;
    private String meetingFullname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }
}
