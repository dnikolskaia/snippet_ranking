package rs.dnikolskaia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Usage(
        Place place,
        Method method,
        Context context
) {
    public record Place(
            Address address,
            Range range,
            int line
    ) {
    }

    public record Address(
            String url,
            Clazz clazz,
            String path
    ) {
    }

    public record Clazz(
            String packazh,
            String name
    ) {
    }

    public record Range(
            int start,
            int end
    ) {
    }

    public record Method(
            Address address,
            Signature signature,
            ReturnType returnType,
            boolean isStatic,
            boolean isPublic
    ) {
    }

    public record Signature(
            String name,
            List<Type> types
    ) {
    }

    public record Type(
            String packazh,
            String name
    ) {
    }

    public record ReturnType(
            String packazh,
            String name
    ) {
    }

    public record Context(
            int inheritanceLevel,
            List<Variable> variables,
            List<Parameter> parameters,
            Tokens tokens,
            Range absoluteRange,
            Range relativeRange
    ) {
    }

    public record Variable(
            Type type,
            VariableName name,
            boolean initialized,
            boolean isStatic,
            boolean isPublic
    ) {
    }

    public record VariableName(
            Clazz clazz,
            String name
    ) {
    }


    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = ExpressionParameter.class, name = "expression"),
            @JsonSubTypes.Type(value = VariableParameter.class, name = "var"),
            @JsonSubTypes.Type(value = LiteralParameter.class, name = "literal"),
            @JsonSubTypes.Type(value = CallParameter.class, name = "call"),
            @JsonSubTypes.Type(value = NewParameter.class, name = "new")
    })
    public interface Parameter {
    }

    public record ExpressionParameter(
            String type,
            ExpressionParameterValue parameter
    ) implements Parameter {
    }

    public record VariableParameter(
            String type,
            VariableParameterValue parameter
    ) implements Parameter {
    }

    public record LiteralParameter(
            String type,
            LiteralParameterValue parameter
    ) implements Parameter {
    }

    public record CallParameter(
            String type,
            CallParameterValue parameter
    ) implements Parameter {
    }

    public record NewParameter(
            String type,
            NewParameterValue parameter
    ) implements Parameter {
    }

    public record ExpressionParameterValue(
            Type type,
            String projectUrl
    ) {
    }

    public record VariableParameterValue(
            Variable variable,
            String projectUrl
    ) {
    }

    public record LiteralParameterValue(
            String text,
            Type type,
            String projectUrl
    ) {
    }

    public record CallParameterValue(
            String text,
            Method method,
            boolean isStatic,
            boolean isPublic
    ) {
    }

    public record NewParameterValue(
            String text,
            Type type,
            Method method
    ) {
    }


    public record Tokens(
            Range function,
            String packageName,
            Range nameRange,
            Range bodyRange,
            Range paramRange,
            Range place,
            Range declTypeRange,
            Range declNameRange
    ) {
    }

}