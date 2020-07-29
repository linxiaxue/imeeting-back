package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fudan.se.lab2.service.AuthService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @author LBW
 */
@Entity
public class User implements UserDetails {

    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String fullname;
    private String email;
    private String district;
    private String workUnit;


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Meeting> meetings = new HashSet<>();



    public User() {}

    public User(String username, String password, String fullname) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }

    public User(String username, String password, String fullname, Set<Authority> authorities) {
        this.username = username;
        this.password= password;
        this.fullname = fullname;
        this.authorities = authorities;
    }
    public User(String username, String password, String fullname, String email, String district, String workUnit, Set<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.district = district;
        this.workUnit = workUnit;
        this.authorities = authorities;

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



    public String getEmail() {
        return email;
    }


    public String getDistrict() {
        return district;
    }


    public String getWorkUnit() {
        return workUnit;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }
}
