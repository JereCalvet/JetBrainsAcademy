package engine.repositories;

import engine.models.quizdomain.completedrecord.QuizCompleted;
import engine.models.quizdomain.completedrecord.QuizCompletedLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository("JpaQuizCompletedLogImpl")
public class QuizCompletedLogDaoImpl implements QuizCompletedLogDao {

    private final int DEFAULT_PAGE_SIZE = 10;
    private final QuizCompletedLogRepository logRepository;

    @Autowired
    public QuizCompletedLogDaoImpl(QuizCompletedLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public QuizCompleted save(QuizCompleted quizCompleted) {
        return logRepository.save(quizCompleted);
    }

    @Override
    public Page<QuizCompleted> findAllById(Long id, int pageNo) {
        Pageable paging = PageRequest.of(pageNo, DEFAULT_PAGE_SIZE, Sort.by("completedAt").descending());
        return logRepository.findAllById(id, paging);
    }
}
