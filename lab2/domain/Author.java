package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
public class Author {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
    private String workUnit;
    private String email;
    private String district;
    private int sort;
    private Long CId; //投稿id

    public Author(String name, String workUnit, String email, String district,int sort) {
        this.name = name;
        this.workUnit = workUnit;
        this.email = email;
        this.district = district;
        this.sort = sort;

    }



    public Author() {
    }

    public Long getCId() {
        return CId;
    }

    public void setCId(Long CId) {
        this.CId = CId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
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
}
