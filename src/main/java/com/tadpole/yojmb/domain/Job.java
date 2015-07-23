package com.tadpole.yojmb.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Job.
 */
@Entity
@Table(name = "JOB")
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "ws_target_url")
    private String wsTargetUrl;

    @Column(name = "start_hour")
    private Integer startHour;

    @Column(name = "start_min")
    private Integer startMin;

    @Column(name = "stop_hour")
    private Integer stopHour;

    @Column(name = "stop_min")
    private Integer stopMin;

    @Column(name = "login_user_name")
    private String loginUserName;

    @Column(name = "login_password")
    private String loginPassword;

    @Column(name = "interval_minutes")
    private Integer intervalMinutes;

    @OneToOne
    private JobType jobType;
    
    @OneToOne
    private Target target;
    

    public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	@OneToOne
    private DataSourceSystem dataSourceSystem;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWsTargetUrl() {
        return wsTargetUrl;
    }

    public void setWsTargetUrl(String wsTargetUrl) {
        this.wsTargetUrl = wsTargetUrl;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMin() {
        return startMin;
    }

    public void setStartMin(Integer startMin) {
        this.startMin = startMin;
    }

    public Integer getStopHour() {
        return stopHour;
    }

    public void setStopHour(Integer stopHour) {
        this.stopHour = stopHour;
    }

    public Integer getStopMin() {
        return stopMin;
    }

    public void setStopMin(Integer stopMin) {
        this.stopMin = stopMin;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Integer getIntervalMinutes() {
        return intervalMinutes;
    }

    public void setIntervalMinutes(Integer intervalMinutes) {
        this.intervalMinutes = intervalMinutes;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public DataSourceSystem getDataSourceSystem() {
        return dataSourceSystem;
    }

    public void setDataSourceSystem(DataSourceSystem dataSourceSystem) {
        this.dataSourceSystem = dataSourceSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Job job = (Job) o;

        if ( ! Objects.equals(id, job.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", wsTargetUrl='" + wsTargetUrl + "'" +
                ", startHour='" + startHour + "'" +
                ", startMin='" + startMin + "'" +
                ", stopHour='" + stopHour + "'" +
                ", stopMin='" + stopMin + "'" +
                ", loginUserName='" + loginUserName + "'" +
                ", loginPassword='" + loginPassword + "'" +
                ", intervalMinutes='" + intervalMinutes + "'" +
                '}';
    }
}
