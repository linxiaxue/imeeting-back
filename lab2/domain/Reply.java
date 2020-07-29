package fudan.se.lab2.domain;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;

@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long pid;//主贴id
    private String replyer;
    private Date createTime;
    private String text;

    public Reply() {
    }

    public Reply(Long pid, String replyer, Date createTime, String text) {
        this.pid = pid;
        this.replyer = replyer;
        this.createTime = createTime;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getReplyer() {
        return replyer;
    }

    public void setReplyer(String replyer) {
        this.replyer = replyer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
