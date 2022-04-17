package platform.businesslayer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


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

    private String uuid;


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

   // @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

  //  @JsonInclude(JsonInclude.Include.NON_NULL)
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
}
