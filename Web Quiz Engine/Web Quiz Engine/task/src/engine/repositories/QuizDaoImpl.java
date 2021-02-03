package engine.repositories;

import engine.models.quizdomain.Quiz;
import engine.models.quizdomain.QuizDao;
import engine.security.SecurityUtils;
import engine.models.userdomain.User;
import engine.models.userdomain.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("JpaQuizImpl")
public class QuizDaoImpl implements QuizDao {

    private final int DEFAULT_PAGE_SIZE = 10;
    private final QuizRepository quizRepository;
    private final UserDao userDao;

    @Autowired
    public QuizDaoImpl(QuizRepository quizRepository, UserDao userDao) {
        this.quizRepository = quizRepository;
        this.userDao = userDao;
    }

    @Override
    public Quiz save(Quiz quiz) {
        Optional<User> userAuthenticated = userDao.selectByEmail(SecurityUtils.getCurrentAuthenticatedUser());
        quiz.setUser(userAuthenticated.get());
        return quizRepository.save(quiz);
    }

    @Override
    public Optional<Quiz> findById(Long aLong) {
        return quizRepository.findById(aLong);
    }

    @Override
    public Page<Quiz> findAll(int pageNo) {
        Pageable paging = PageRequest.of(pageNo, DEFAULT_PAGE_SIZE);
        return quizRepository.findAll(paging);
    }

    @Override
    public void deleteById(Long aLong) {
        quizRepository.deleteById(aLong);
    }
}
