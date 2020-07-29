package fudan.se.lab2.domain;

import java.util.Date;
import java.util.List;

public class Post {
    private Long id;
    private String title;
    private String publisher;
    private Date publishTime;
    private List<Reply> replies;

    public Post(Long id, String title, String publisher, Date publishTime, List<Reply> replies) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.publishTime = publishTime;
        this.replies = replies;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
