package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import platform.controllers.DtoToCodeSnippetEntity;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static platform.utils.DateFormatter.FORMATTER;

@Entity
public class CodeSnippet {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Id
    @Column(name = "CodeSnippetId")
    private UUID codeSnippetId;

    @Column(name = "Code", nullable = false)
    private String code;

    @Column(name = "Date")
    private LocalDateTime date;

    @Column(name = "AccessibleTimeInSeconds", nullable = false)
    private long time;

    @Column(name = "ViewsAllowed", nullable = false)
    private int views;

    @Column(name = "secretCode", nullable = false)
    private boolean secretCode;

    @Column(name = "timeRestriction", nullable = false)
    private boolean timeRestricted;

    @Column(name = "ViewsRestriction", nullable = false)
    private boolean viewRestricted;

    public CodeSnippet() {
    }

    public CodeSnippet(DtoToCodeSnippetEntity dto) {
        this.code = dto.getCode();
        this.time = dto.getTime();
        this.views = dto.getViews();
        date = LocalDateTime.now();
        manageRestrictions();
        codeSnippetId = UUID.randomUUID();
    }

    public UUID getCodeSnippetId() {
        return codeSnippetId;
    }

    public String getDate() {
        return FORMATTER.format(date);
    }

    @JsonIgnore
    public LocalDateTime getDateAsLocalDateTime() {
        return date;
    }

    public String getCode() {
        return code;
    }

    @JsonIgnore
    public boolean isSecretCode() {
        return secretCode;
    }

    public long getTime() {
        LocalDateTime expirationTime = date.plusSeconds(time);
        final Duration timeLeft = Duration.between(LocalDateTime.now(), expirationTime);
        return timeLeft.toSeconds() < 0 ? 0 : timeLeft.toSeconds();
    }

    public int getViews() {
        return views > 0 ? views : 0;
    }

    @JsonIgnore
    public boolean isTimeRestricted() {
        return timeRestricted;
    }

    @JsonIgnore
    public boolean isViewRestricted() {
        return viewRestricted;
    }

    public void setCodeSnippetId(UUID codeSnippetId) {
        this.codeSnippetId = codeSnippetId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setSecretCode(boolean secretCode) {
        this.secretCode = secretCode;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        this.timeRestricted = timeRestricted;
    }

    public void setViewRestricted(boolean viewRestricted) {
        this.viewRestricted = viewRestricted;
    }

    @Override
    public String toString() {
        return "ID: " + codeSnippetId + "\n" +
                "Date: " + getDate() + "\n" +
                "Code: " + code + "\n" +
                "Time: " + time + "\n" +
                "Views: " + views + "\n" +
                "SecretCode: " + secretCode + "\n" +
                "ResViews: " + viewRestricted + "\n" +
                "ResTime: " + timeRestricted + "\n";
    }

    private void manageRestrictions() {
        if (views > 0 || time > 0) {
            secretCode = true;
        }

        if (views > 0) {
            viewRestricted = true;
        }

        if (time > 0) {
            timeRestricted = true;
        }
    }
}
