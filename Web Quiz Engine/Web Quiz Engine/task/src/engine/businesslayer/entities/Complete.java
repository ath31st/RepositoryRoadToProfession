package engine.businesslayer.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complete")
public class Complete {

    @Id
    @JsonIgnore
    @Column(name = "id_complete", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idComplete;


    @JsonProperty("id")
    private Long quizId;


    @Column
    private Long userId;

    @Column
    private LocalDateTime completedAt;

    public Complete() {
    }

    public Complete(Long quizId, Long userId, LocalDateTime completedAt) {
        this.quizId = quizId;
        this.userId = userId;
        this.completedAt = completedAt;
    }

    public Long getIdComplete() {
        return idComplete;
    }

    public void setIdComplete(Long idComplete) {
        this.idComplete = idComplete;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
