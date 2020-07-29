package fudan.se.lab2.controller.request;

public class MakeEditRequest {
    private int mark;
    private String comment;
    private String confidence;
    private String username;
    private Long cid;
    private Long id ; //审稿id
    private int editStatus ; //审稿状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
