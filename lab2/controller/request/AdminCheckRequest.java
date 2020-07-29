package fudan.se.lab2.controller.request;

public class AdminCheckRequest {
    private int status;
    private String meetingFullname;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }
}
