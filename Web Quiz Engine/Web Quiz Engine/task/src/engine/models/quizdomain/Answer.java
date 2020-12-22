package engine.models.quizdomain;

import javax.validation.constraints.NotNull;

public class Answer {
    @NotNull(message = "Answer required.")
    private int[] answer;

    public Answer() {
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
