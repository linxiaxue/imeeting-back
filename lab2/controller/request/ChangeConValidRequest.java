package fudan.se.lab2.controller.request;

public class ChangeConValidRequest {
    private String meetingFullname;
    private int status;


    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
