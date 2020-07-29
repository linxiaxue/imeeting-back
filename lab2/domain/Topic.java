package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Topic {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String topicName;

    @ManyToMany(mappedBy = "topics")
    @JsonIgnore
    private Set<Meeting> meetings = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public Topic(){}
}
