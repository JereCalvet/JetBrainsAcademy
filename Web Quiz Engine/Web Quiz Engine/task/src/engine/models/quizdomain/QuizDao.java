package engine.models.quizdomain;

import org.springframework.data.domain.Page;
import java.util.Optional;

public interface QuizDao {
    Quiz save(Quiz quiz);
    Optional<Quiz> findById(Long aLong);
    Page<Quiz> findAll(int pageNo);
    void deleteById(Long aLong);
}
