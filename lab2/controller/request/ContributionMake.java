package fudan.se.lab2.controller.request;

import fudan.se.lab2.domain.Author;

import java.util.List;

public class ContributionMake {
    private String contributor;
    private String meetingFullname;
    private String title;
    private String digest;
    private String fileName;
    private List<String> topics;
    private List<Author> authors;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
