package com.tadpole.yojmb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A JobType.
 */
@Entity
@Table(name = "JOBTYPE")
public class JobType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "identity")
    private String identity;

    @Column(name = "column1")
    private String column1;

    @Column(name = "value1")
    private String value1;

    @Column(name = "column2")
    private String column2;

    @Column(name = "value2")
    private String value2;

    @OneToOne(mappedBy = "jobType")
    @JsonIgnore
    private Job job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobType jobType = (JobType) o;

        if ( ! Objects.equals(id, jobType.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobType{" +
                "id=" + id +
                ", description='" + description + "'" +
                ", identity='" + identity + "'" +
                ", column1='" + column1 + "'" +
                ", value1='" + value1 + "'" +
                ", column2='" + column2 + "'" +
                ", value2='" + value2 + "'" +
                '}';
    }
}
