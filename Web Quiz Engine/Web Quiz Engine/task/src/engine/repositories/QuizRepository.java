package engine.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import engine.models.quizdomain.Quiz;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {
}
