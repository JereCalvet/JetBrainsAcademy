package platform.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import platform.configuration.FreeMakerConfigBean;
import platform.model.CodeSnippet;
import platform.service.CodeSnippetService;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class CodeSnippetControllerWeb {
    private final CodeSnippetService codeSnippetService;
    private final FreeMakerConfigBean configBean;

    @Autowired
    public CodeSnippetControllerWeb(CodeSnippetService codeSnippetService, FreeMakerConfigBean configBean) {
        this.codeSnippetService = codeSnippetService;
        this.configBean = configBean;
    }

    @GetMapping(path = {"/code/new"}, produces = {"text/html"})
    public String saveCode() {
        String htmlInputForm = "";
        try {
            File resource = new ClassPathResource("NewCodeInputForm.html").getFile();
            htmlInputForm = new String(Files.readAllBytes(resource.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlInputForm;
    }

    @GetMapping(path = {"/code/{id}"}, produces = {"text/html"})
    public String getCode(@PathVariable UUID id) {
        //get data
        final CodeSnippet codeSnippet = codeSnippetService.getById(id);

        //prepare freemarker data model
        final Map<String, Object> codeSnippetDataModel = getDataModel("codeSnippet", codeSnippet);

        // get template
        final Template codeSnippetTemplate = getTemplateByName("Code.ftl");

        //get output html
        return getOutputHtml(codeSnippetDataModel, codeSnippetTemplate);
    }

    @GetMapping(path = {"/code/latest"}, produces = {"text/html"})
    public String getLatest() {
        //get data
        final List<CodeSnippet> latestCodeSnippets = codeSnippetService.getLatest();

        //prepare freemarker data model
        final Map<String, Object> latestCodeSnippetsDataModel = getDataModel("latestCodeSnippets", latestCodeSnippets);

        // get template
        final Template latestCodeSnippetsTemplate = getTemplateByName("Latest.ftl");

        //get output html
        return getOutputHtml(latestCodeSnippetsDataModel, latestCodeSnippetsTemplate);
    }

    private Map<String, Object> getDataModel(String tag, Object bean) {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put(tag, bean);
        return dataModel;
    }

    private Template getTemplateByName(String templateName) {
        Template template;
        try {
            final Configuration cfg = configBean.getCfg();
            template = cfg.getTemplate(templateName);
        } catch (IOException e) {

            throw new IllegalStateException(String.format("Template %s not found. %s", templateName, e.getMessage()));
        }
        return template;
    }

    private String getOutputHtml(Map<String, Object> dataModel, Template template) {
        String htmlWithTemplate = "";
        try {
            Writer stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            htmlWithTemplate = stringWriter.toString();
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
        return htmlWithTemplate;
    }
}
