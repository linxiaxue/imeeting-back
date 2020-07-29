package fudan.se.lab2.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "MEETING_USER_ROLE")
public class MeetingUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "meetingFullname")
    //private String abbreviation;
    private String meetingFullname;

    @Column(name = "username")
    private String username;

    private int isChair;
    private int isMember;
    private int isAuthor;

    public MeetingUser(String meetingFullname, String username, int isChair, int isMember, int isAuthor) {
        this.meetingFullname = meetingFullname;
        this.username = username;
        this.isChair = isChair;
        this.isMember = isMember;
        this.isAuthor = isAuthor;
    }

    public MeetingUser(){}

    public String getmeetingFullname() {
        return meetingFullname;
    }

    public void setmeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "ischair")
    public int getIsChair() {
        return isChair;
    }

    public void setIsChair(int isChair) {
        this.isChair = isChair;
    }

    @Column(name = "ismember")
    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    @Column(name = "isauthor")
    public int getIsAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(int isAuthor) {
        this.isAuthor = isAuthor;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
