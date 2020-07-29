package fudan.se.lab2.controller.request;

import fudan.se.lab2.domain.Author;

import java.util.List;

public class TestListRequest {
    private List<String> topics;
    private List<Author> authors;

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
