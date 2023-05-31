package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.Collections;
import java.util.List;

public class TestDataUtil {
    public static Snippet createTestSnippet() {
        var range = new Usage.Range(0, 1);
        var method = new Usage.Method(
            new Usage.Address(
                "url",
                new Usage.Clazz("package", "class"),
                "path"
            ),
            new Usage.Signature("name", Collections.emptyList()),
            new Usage.ReturnType("package", "name"),
            false,
            false);
        var context = new Usage.Context(0, null, null, null, range, range);
        Usage usage = new Usage(null, method, context, null);
        String fileText = "public class SomeClass{}";
        return new Snippet(usage, fileText);
    }

    public static Snippet createTestSnippetWithVariables(List<Usage.Variable> variables) {
        var range = new Usage.Range(0, 1);
        var method = new Usage.Method(
            new Usage.Address(
                "url",
                new Usage.Clazz("package", "class"),
                "path"
            ),
            new Usage.Signature("name", Collections.emptyList()),
            new Usage.ReturnType("package", "name"),
            false,
            false);
        var context = new Usage.Context(0, variables, null, null, range, range);
        Usage usage = new Usage(null, method, context, null);
        String fileText = "public class SomeClass{}";
        return new Snippet(usage, fileText);
    }

    public static Snippet createTestSnippet(List<Usage.Parameter> parameters) {
        var range = new Usage.Range(0, 1);
        var method = new Usage.Method(
            new Usage.Address(
                "url",
                new Usage.Clazz("package", "class"),
                "path"
            ),
            new Usage.Signature("name", Collections.emptyList()),
            new Usage.ReturnType("package", "name"),
            false,
            false);
        var context = new Usage.Context(0, null, parameters, null, range, range);
        Usage usage = new Usage(null, method, context, null);
        String fileText = "public class SomeClass{}";
        return new Snippet(usage, fileText);
    }

    public static Snippet createTestSnippet(Usage.ParentFunction parentFunction) {
        var range = new Usage.Range(0, 1);
        var method = new Usage.Method(
            new Usage.Address(
                "url",
                new Usage.Clazz("package", "class"),
                "path"
            ),
            new Usage.Signature("name", Collections.emptyList()),
            new Usage.ReturnType("package", "name"),
            false,
            false);
        var context = new Usage.Context(0, null, null, null, range, range);
        var featuresPack = new Usage.FeaturesPack(
            new Usage.CategoryFeatures(parentFunction, null),
            null
        );
        Usage usage = new Usage(null, method, context, featuresPack);
        String fileText = "public class SomeClass{}";
        return new Snippet(usage, fileText);
    }

    public static Snippet createTestSnippet(Usage.Chain chain) {
        var range = new Usage.Range(0, 1);
        var method = new Usage.Method(
            new Usage.Address(
                "url",
                new Usage.Clazz("package", "class"),
                "path"
            ),
            new Usage.Signature("name", Collections.emptyList()),
            new Usage.ReturnType("package", "name"),
            false,
            false);
        var context = new Usage.Context(0, null, null, null, range, range);
        var featuresPack = new Usage.FeaturesPack(
            new Usage.CategoryFeatures(null, chain),
            null
        );
        Usage usage = new Usage(null, method, context, featuresPack);
        String fileText = "public class SomeClass{}";
        return new Snippet(usage, fileText);
    }

    public static Snippet createTestSnippet(Usage.PreviousCalls previousCalls) {
        var range = new Usage.Range(0, 1);
        var method = new Usage.Method(
            new Usage.Address(
                "url",
                new Usage.Clazz("package", "class"),
                "path"
            ),
            new Usage.Signature("name", Collections.emptyList()),
            new Usage.ReturnType("package", "name"),
            false,
            false);
        var context = new Usage.Context(0, null, null, null, range, range);
        var featuresPack = new Usage.FeaturesPack(
            null,
            previousCalls
        );
        Usage usage = new Usage(null, method, context, featuresPack);
        String fileText = "public class SomeClass{}";
        return new Snippet(usage, fileText);
    }

    public static Usage.Parameter createLiteralParameter() {
        var literalParameter = new Usage.LiteralParameterValue(
            "text",
            new Usage.Type("package", "type"),
            "url"
        );
        return new Usage.LiteralParameter("literal", literalParameter);
    }

    public static Usage.Parameter createNewParameter() {
        var cls = new Usage.Clazz("package", "class");
        Usage.Method method = new Usage.Method(
            new Usage.Address("url", cls, "path"),
            new Usage.Signature("name", Collections.emptyList()),
            new Usage.ReturnType("package", "NewType"),
            false,
            false
        );
        var parameterValue = new Usage.NewParameterValue(
            "text",
            new Usage.Type("package", "type"),
            method
        );
        return new Usage.NewParameter("new", parameterValue);
    }

    public static Usage.Parameter createExpressionParameter(String typeName) {
        var parameterValue = new Usage.ExpressionParameterValue(
            new Usage.Type("package", typeName),
            "url"
        );
        return new Usage.ExpressionParameter("expression", parameterValue);
    }

    public static Usage.Parameter createVariableParameter(boolean initialized) {
        Usage.Variable variable = new Usage.Variable(
            new Usage.Type("package", "type"),
            new Usage.VariableName(new Usage.Clazz("package", "name"), "name"),
            initialized,
            false,
            false
        );
        return new Usage.VariableParameter("var", new Usage.VariableParameterValue(variable, "url"));
    }

    public static Usage.CallParameter createCallParameter() {
        Usage.Method method = new Usage.Method(
            new Usage.Address("url", new Usage.Clazz("package", "class"), "path"),
            new Usage.Signature("methodName", Collections.emptyList()),
            new Usage.ReturnType("package", "name"),
            false,
            false
        );
        Usage.CallParameterValue parameterValue = new Usage.CallParameterValue(
            "callText",
            method,
            false,
            false
        );
        return new Usage.CallParameter("call", parameterValue);
    }

    public static Usage.Method createMethod(String methodName) {
        return new Usage.Method(
            new Usage.Address("url", new Usage.Clazz("package", "class"), "path"),
            new Usage.Signature(methodName, Collections.emptyList()),
            new Usage.ReturnType("package", "name"),
            false,
            false);
    }

    public static Usage.ParentFunction createParentFunction(Usage.Method method) {
        return new Usage.ParentFunction(
            "type",
            new Usage.ParentFunction.ParentFunctionFact(
                method,
                false,
                new Usage.Range(0, 1)
            )
        );
    }

    public static Usage.Chain createChain(List<Usage.Method> methods) {
        return new Usage.Chain(
          "type",
            new Usage.Chain.ChainFact(methods, new Usage.Range(0, 1))
        );
    }

    public static Usage.PreviousCalls createPreviousCalls(List<Usage.Method> methods) {
        return new Usage.PreviousCalls(
            "type",
            new Usage.PreviousCalls.PreviousCallsFact(methods)
        );
    }

    public static Usage.Variable createVariable(String className) {
        return new Usage.Variable(
            new Usage.Type("package", className),
            new Usage.VariableName(new Usage.Clazz("", ""), "name"),
            false,
            false,
            false
        );
    }

}
