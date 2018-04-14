package handlebars.functionNamers;

@FunctionalInterface
public interface FunctionNamer {
    String name(String inputParam);

    default String toCamelCase(String inputParam) {
        return inputParam.substring(0, 1).toUpperCase() + inputParam.substring(1);
    }
}
