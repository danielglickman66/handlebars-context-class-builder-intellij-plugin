package actions;

import handlebars.classBuilders.ContextClassBuilder;
import handlebars.classBuilders.FunctionalContextClassBuilder;
import handlebars.functionNamers.GetterFunctionNamer;
import handlebars.functionNamers.SetterFunctionNamer;

public class FunctionalGenerateAction extends GenerateAction {
    @Override
    protected ContextClassBuilder getContextClassBuilder(String javaContextClassName) {
        return new FunctionalContextClassBuilder(javaContextClassName, new SetterFunctionNamer(), new GetterFunctionNamer());
    }
}
