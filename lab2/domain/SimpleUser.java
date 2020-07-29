package fudan.se.lab2.domain;

public class SimpleUser {
    private String username;
    private String fullname;
    private String email;
    private String district;
    private String workUnit;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public SimpleUser(String username, String fullname, String email, String district, String workUnit) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.district = district;
        this.workUnit = workUnit;
    }
}
