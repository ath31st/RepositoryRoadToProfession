package platform.businesslayer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Snippet {

    @Column
    private String code;
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String date;

    @Column
    private Long time = 0L;
    @Column
    private Long views = 0L;
    @Column
    private String uuid;
    @Column
    @JsonIgnore
    private boolean restricted;
    @JsonIgnore
    @Column
    private boolean restrictedByTime;
    @JsonIgnore
    @Column
    private boolean restrictedByViews;


    public Snippet() {
    }

    public Snippet(String code, String date) {
        this.code = code;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    @JsonIgnore
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public boolean isRestrictedByTime() {
        return restrictedByTime;
    }

    public void setRestrictedByTime(boolean restrictedByTime) {
        this.restrictedByTime = restrictedByTime;
    }

    public boolean isRestrictedByViews() {
        return restrictedByViews;
    }

    public void setRestrictedByViews(boolean restrictedByViews) {
        this.restrictedByViews = restrictedByViews;
    }
}
