package fudan.se.lab2.controller.request;

import java.util.Set;

/**
 * @author LBW
 */
public class RegisterRequest {
    private String username;
    private String password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    // public Set<String> getAuthorities() {
    //    return authorities;
   // }

   // public void setAuthorities(Set<String> authorities) {
    //    this.authorities = authorities;
   // }
}

