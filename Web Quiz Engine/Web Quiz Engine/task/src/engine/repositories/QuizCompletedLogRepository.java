package engine.repositories;

import engine.models.quizdomain.completedrecord.QuizCompleted;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface QuizCompletedLogRepository extends PagingAndSortingRepository<QuizCompleted, Long> {

    @Query(value = "SELECT q FROM QuizCompleted q WHERE q.userId = :id")
    Page<QuizCompleted> findAllById(@Param("id") Long id, Pageable pageable);
}
