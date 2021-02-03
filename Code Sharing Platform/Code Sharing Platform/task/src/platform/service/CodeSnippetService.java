package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import platform.model.CodeSnippet;
import platform.service.validator.CodeSnippetValidator;
import platform.repositories.CodeSnippetRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CodeSnippetService {
    private final CodeSnippetRepository codeSnippetRepository;

    @Autowired
    public CodeSnippetService(CodeSnippetRepository codeSnippetRepository) {
        this.codeSnippetRepository = codeSnippetRepository;
    }

    public List<CodeSnippet> getLatest() {
        return codeSnippetRepository.findAll()
                .stream()
                .filter(CodeSnippetValidator::noRestricted)
                .sorted((a, b) -> b.getDateAsLocalDateTime().compareTo(a.getDateAsLocalDateTime()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public CodeSnippet getById(UUID id) {
        final CodeSnippet codeSnippetFound = codeSnippetRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Code snippet %s not found", id)));

        if (CodeSnippetValidator.isValid(codeSnippetFound)) {
            return checkSecretCodeAndChangeStatus(codeSnippetFound);
        } else {
            codeSnippetRepository.delete(codeSnippetFound);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Code snippet %s was invalid", id));
        }
    }

    private CodeSnippet checkSecretCodeAndChangeStatus(CodeSnippet codeSnippetFound) {
        if (codeSnippetFound.isSecretCode()) {
            codeSnippetFound.setViews(codeSnippetFound.getViews() - 1);
            codeSnippetRepository.save(codeSnippetFound);
        }
        return codeSnippetFound;
    }
}
