package engine.models.quizdomain;

import com.fasterxml.jackson.annotation.JsonProperty;
import engine.models.userdomain.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Quiz {

    @Id
    @Column(name = "QuizId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Title", nullable = false)
    @NotBlank(message = "Title is required.")
    private String title;

    @Column(name = "Text", nullable = false)
    @NotBlank(message = "Text is required.")
    private String text;

    @Column(name = "Options", nullable = false)
    @NotNull(message = "Options is required.")
    @Size(min = 2, message = "At least 2 options.")
    private String[] options;

    @Column(name = "Answer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    @ManyToOne
    @JoinColumn(name = "UserId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    public Quiz() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getAnswer() {
        return answer == null ? new int[0] : answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}