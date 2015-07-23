package com.tadpole.yojmb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DataSourceSystem.
 */
@Entity
@Table(name = "DATASOURCESYSTEM")
public class DataSourceSystem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "identity")
    private String identity;

    @Column(name = "login_page_url")
    private String loginPageUrl;

    @Column(name = "query_page_url")
    private String queryPageUrl;

    @Column(name = "query_parameter1")
    private String queryParameter1;

    @Column(name = "query_parameter2")
    private String queryParameter2;

    @Column(name = "query_parameter3")
    private String queryParameter3;

    @Column(name = "single_detail_url")
    private String singleDetailUrl;

    @OneToOne(mappedBy = "dataSourceSystem")
    @JsonIgnore
    private Job job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getLoginPageUrl() {
        return loginPageUrl;
    }

    public void setLoginPageUrl(String loginPageUrl) {
        this.loginPageUrl = loginPageUrl;
    }

    public String getQueryPageUrl() {
        return queryPageUrl;
    }

    public void setQueryPageUrl(String queryPageUrl) {
        this.queryPageUrl = queryPageUrl;
    }

    public String getQueryParameter1() {
        return queryParameter1;
    }

    public void setQueryParameter1(String queryParameter1) {
        this.queryParameter1 = queryParameter1;
    }

    public String getQueryParameter2() {
        return queryParameter2;
    }

    public void setQueryParameter2(String queryParameter2) {
        this.queryParameter2 = queryParameter2;
    }

    public String getQueryParameter3() {
        return queryParameter3;
    }

    public void setQueryParameter3(String queryParameter3) {
        this.queryParameter3 = queryParameter3;
    }

    public String getSingleDetailUrl() {
        return singleDetailUrl;
    }

    public void setSingleDetailUrl(String singleDetailUrl) {
        this.singleDetailUrl = singleDetailUrl;
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

        DataSourceSystem dataSourceSystem = (DataSourceSystem) o;
        
        if ( ! Objects.equals(id, dataSourceSystem.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DataSourceSystem{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", identity='" + identity + "'" +
                ", loginPageUrl='" + loginPageUrl + "'" +
                ", queryPageUrl='" + queryPageUrl + "'" +
                ", queryParameter1='" + queryParameter1 + "'" +
                ", queryParameter2='" + queryParameter2 + "'" +
                ", queryParameter3='" + queryParameter3 + "'" +
                ", singleDetailUrl='" + singleDetailUrl + "'" +
                '}';
    }
}
