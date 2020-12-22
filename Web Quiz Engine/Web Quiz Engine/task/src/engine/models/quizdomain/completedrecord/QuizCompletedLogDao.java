package engine.models.quizdomain.completedrecord;

import org.springframework.data.domain.Page;

public interface QuizCompletedLogDao {
    QuizCompleted save(QuizCompleted quizCompleted);
    Page<QuizCompleted> findAllById(Long id, int pageNo);
}
