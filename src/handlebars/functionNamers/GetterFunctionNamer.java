package handlebars.functionNamers;

public class GetterFunctionNamer implements FunctionNamer {
    @Override
    public String name(String inputParam) {
        return "get" + toCamelCase(inputParam);
    }
}
