package engine.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import engine.models.quizdomain.Answer;
import engine.models.quizdomain.Quiz;
import engine.models.quizdomain.QuizDao;
import engine.models.quizdomain.QuizSolver;
import engine.models.quizdomain.completedrecord.QuizCompleted;
import engine.models.quizdomain.completedrecord.QuizCompletedLogDao;
import engine.security.SecurityUtils;
import engine.models.userdomain.User;
import engine.models.userdomain.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
@Validated
public class QuizController {

    private final QuizDao quizDao;
    private final UserDao userDao;
    private final QuizCompletedLogDao logDao;

    @Autowired
    public QuizController(QuizDao quizDao, UserDao userDao, QuizCompletedLogDao logDao) {
        this.quizDao = quizDao;
        this.userDao = userDao;
        this.logDao = logDao;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodQuizArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
        return quizDao.save(quiz);
    }

    @PostMapping(path = "/{id}/solve", consumes = "application/json", produces = "application/json")
    public QuizSolver solveQuiz(@PathVariable("id") @Min(0) Long id, @Valid @RequestBody Answer answer) {
        return new QuizSolver(getQuizByIdAndHandleError(id), answer, getCurrentAuthenticatedUser(), logDao);
    }

    @GetMapping(produces = "application/json")
    public Page<Quiz> getQuizList(@RequestParam(required = false, defaultValue = "0") int page) {
        return quizDao.findAll(page);
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Quiz> getQuizById(@PathVariable("id") @Min(0) Long id) {
        return ResponseEntity.ok().body(getQuizByIdAndHandleError(id));
    }

    @DeleteMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable("id") @Min(0) Long id) {
        Quiz quizById = getQuizByIdAndHandleError(id);
        if (isUserAuthor(quizById)) {
            quizDao.deleteById(quizById.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the author can delete the quiz.");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/completed", produces = "application/json")
    public Page<QuizCompleted> getCompletedQuizListOfCurrentUser(@RequestParam(required = false, defaultValue = "0") int page) {
        return logDao.findAllById(getCurrentAuthenticatedUser().getId(), page);
    }

    private boolean isUserAuthor(Quiz quizToCheck) {
        User currentUser = getCurrentAuthenticatedUser();
        return isQuizAuthor(currentUser, quizToCheck);
    }

    private User getCurrentAuthenticatedUser() {
        return userDao.selectByEmail(SecurityUtils.getCurrentAuthenticatedUser()).get();
    }

    private boolean isQuizAuthor(User currentUser, Quiz quizToCheck) {
        return quizToCheck.getUser().getId() == currentUser.getId();
    }

    private Quiz getQuizByIdAndHandleError(long id) {
        return quizDao.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found for ID " + id));
    }
}
