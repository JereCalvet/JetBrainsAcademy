package engine.models.quizdomain.completedrecord;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QuizCompleted {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long logId;

    @Column(name = "quizID")
    private long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long userId;

    private LocalDateTime completedAt;

    public QuizCompleted() {
    }

    public QuizCompleted(long quizId, long userId) {
        this.id = quizId;
        this.userId = userId;
        completedAt = LocalDateTime.now();
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
