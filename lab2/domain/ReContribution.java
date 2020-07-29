package fudan.se.lab2.domain;

import java.util.Date;
import java.util.List;

//规范返回投稿信息的类
public class ReContribution {
    private Contribution contribution;
    private List<Author> authors;
    private List<String> topics;
    private List<String> members;
    private String memberUsername;
    private Long id; //审稿id
    private int mark;
    private String comment;
    private String confidence;
    private int editStatus; //0:待审核，1：待初次讨论，2：初次评审结束；3：待讨论rebuttal；4：最终审核结束

    public ReContribution(Contribution contribution, List<Author> authors, List<String> topics) {
        this.contribution = contribution;
        this.authors = authors;
        this.topics = topics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
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


    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public Contribution getContribution() {
        return contribution;
    }

    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
