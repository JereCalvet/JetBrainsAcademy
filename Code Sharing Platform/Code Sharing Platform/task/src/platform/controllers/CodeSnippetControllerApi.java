package platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import platform.model.CodeSnippet;
import platform.repositories.CodeSnippetRepository;
import platform.service.CodeSnippetService;

import java.util.List;
import java.util.UUID;

@RestController
public class CodeSnippetControllerApi {
    private final CodeSnippetRepository codeSnippetRepo;
    private final CodeSnippetService codeSnippetService;

    @Autowired
    public CodeSnippetControllerApi(CodeSnippetRepository codeSnippetRepo, CodeSnippetService codeSnippetService) {
        this.codeSnippetRepo = codeSnippetRepo;
        this.codeSnippetService = codeSnippetService;
    }

    @PostMapping(path = {"/api/code/new"}, consumes = {"application/json"}, produces = {"application/json"})
    public String saveCode(@RequestBody DtoToCodeSnippetEntity codeSnippetToSave) {
        final CodeSnippet codeSnippetSaved = codeSnippetRepo.save(new CodeSnippet(codeSnippetToSave));
        return "{ \"id\": \"" + codeSnippetSaved.getCodeSnippetId() + "\" }";
    }

    @GetMapping(path = {"/api/code/{id}"}, produces = {"application/json"})
    public CodeSnippet getCodeById(@PathVariable("id") UUID id) {
        return codeSnippetService.getById(id);
    }

    @GetMapping(path = {"/api/code/latest"}, produces = {"application/json"})
    public List<CodeSnippet> getLatest() {
        return codeSnippetService.getLatest();
    }
}
