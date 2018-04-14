package handlebars.functionNamers;

public class SetterFunctionNamer implements FunctionNamer {
    @Override
    public String name(String inputParam) {
        return "with" + toCamelCase(inputParam);
    }

}
