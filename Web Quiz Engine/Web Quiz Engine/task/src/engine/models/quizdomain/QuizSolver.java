package engine.models.quizdomain;

import engine.models.quizdomain.completedrecord.QuizCompleted;
import engine.models.quizdomain.completedrecord.QuizCompletedLogDao;
import engine.models.userdomain.User;

import java.util.Arrays;

public class QuizSolver {
    private boolean success;
    private String feedback;
    private final Quiz quizToSolve;
    private final Answer userAnswer;
    private final User userSolving;

    private final QuizCompletedLogDao logDao;

    public QuizSolver(Quiz quizToSolve, Answer userAnswer, User userSolving, QuizCompletedLogDao logDao) {
        this.userAnswer = userAnswer;
        this.quizToSolve = quizToSolve;
        this.userSolving = userSolving;
        this.logDao = logDao;
        solveQuiz();
    }

    private void solveQuiz() {
        if (Arrays.equals(quizToSolve.getAnswer(), userAnswer.getAnswer())) {
            this.success = true;
            this.feedback = "Congratulations, you're right!";
            persistCompletionTimeInDB();
        } else {
            this.success = false;
            this.feedback = "Wrong answer! Please, try again.";
        }
    }

    private void persistCompletionTimeInDB() {
        logDao.save(new QuizCompleted(quizToSolve.getId(), userSolving.getId()));
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
