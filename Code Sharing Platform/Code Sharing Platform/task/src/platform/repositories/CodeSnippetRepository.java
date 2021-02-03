package platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platform.model.CodeSnippet;
import java.util.UUID;

@Repository
public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, UUID> {
}
