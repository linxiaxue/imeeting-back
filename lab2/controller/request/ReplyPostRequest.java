package fudan.se.lab2.controller.request;

public class ReplyPostRequest {
    private Long id;
    private String replyer;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReplyer() {
        return replyer;
    }

    public void setReplyer(String replyer) {
        this.replyer = replyer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
