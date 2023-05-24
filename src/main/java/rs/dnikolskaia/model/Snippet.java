package rs.dnikolskaia.model;

import lombok.Data;

@Data
public class Snippet {
    private String text;
    private String fullMethodName;
    private Usage usage;

    public Snippet(Usage usage, String fileText) {
        this.usage = usage;
        this.text = fileText.substring(usage.context().absoluteRange().start(), usage.context().absoluteRange().end());
        String methodName = usage.method().signature().name();
        String packageName = usage.method().address().clazz().packazh();
        String className = usage.method().address().clazz().name();
        this.fullMethodName = packageName + "." + className + "." + methodName + "()";
    }

    public int getCodeLineCount() {
        return text.split("\n").length;
    }

    @Override
    public String toString() {
        return text;
    }
}
