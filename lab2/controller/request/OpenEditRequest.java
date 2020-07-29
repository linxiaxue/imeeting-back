package fudan.se.lab2.controller.request;

public class OpenEditRequest {
    private String meetingFullname;
    private int status;
    private int strategy;

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

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }
}
