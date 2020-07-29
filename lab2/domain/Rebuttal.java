package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Rebuttal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    Long cid;
    String author;
    String text;
    Date createTime;

    public Rebuttal() {
    }

    public Rebuttal(Long cid, String author, String text, Date createTime) {
        this.cid = cid;
        this.author = author;
        this.text = text;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
