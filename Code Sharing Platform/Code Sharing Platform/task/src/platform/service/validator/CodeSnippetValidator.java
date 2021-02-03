package platform.service.validator;

import platform.model.CodeSnippet;

import java.util.function.Function;

public interface CodeSnippetValidator extends Function<CodeSnippet, Boolean> {

    static CodeSnippetValidator isSecretCode() {
        return CodeSnippet::isSecretCode;
    }

    static CodeSnippetValidator hasTimeRestriction() {
        return CodeSnippet::isTimeRestricted;
    }

    static CodeSnippetValidator hasViewsRestriction() {
        return CodeSnippet::isViewRestricted;
    }

    static CodeSnippetValidator hasEnoughViews() {
        return codeSnippet -> codeSnippet.getViews() > 0;
    }

    static CodeSnippetValidator hasEnoughTime() {
        return codeSnippet -> codeSnippet.getTime() > 0;
    }

    default CodeSnippetValidator and(CodeSnippetValidator other) {
        return codeSnippet -> this.apply(codeSnippet) && other.apply(codeSnippet);
    }

    static boolean noRestricted(CodeSnippet codeSnippetToValidate) {
        return !isSecretCode().apply(codeSnippetToValidate);
    }

    static boolean isValid(CodeSnippet codeSnippetToValidate) {
        return isSecretCode()
                    .and(hasTimeRestriction())
                    .and(hasEnoughTime()).apply(codeSnippetToValidate) ||
               isSecretCode()
                    .and(hasViewsRestriction())
                    .and(hasEnoughViews()).apply(codeSnippetToValidate) ||
               !isSecretCode().apply(codeSnippetToValidate);
    }
}
