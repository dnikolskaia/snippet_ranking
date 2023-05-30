package rs.dnikolskaia.model;

import lombok.Data;

@Data
public class Snippet {
    private String text;
    private String fullMethodName;
    private Usage usage;

    public Snippet(Usage usage, String fileText) {
        this.usage = usage;
        this.text = getSnippetText(fileText, usage);
        this.fullMethodName = getPackageName() + "." + getClassName() + "." + getFunctionName();
    }

    public int getCodeLineCount() {
        return text.split("\n").length;
    }

    public String getPackageName() {
        return usage.method().address().clazz().packazh();
    }

    public String getClassName() {
        return usage.method().address().clazz().name();
    }

    public String getFunctionName() {
        return usage.method().signature().name();
    }

    @Override
    public String toString() {
        return text;
    }

    private String getSnippetText(String fileText, Usage usage) {
        int start = usage.context().absoluteRange().start();
        int end = usage.context().absoluteRange().end();
        return fileText.substring(start, end);
    }
}
