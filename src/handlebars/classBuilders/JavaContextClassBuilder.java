package handlebars.classBuilders;

import com.squareup.javapoet.JavaFile;

import handlebars.HandleBars.HandleBarsVarCaptioner;

public class JavaContextClassBuilder {
    public static JavaFile createContextClass(String hbsContent, ContextClassBuilder contextClassBuilder) {
        new HandleBarsVarCaptioner()
                .caption(hbsContent)
                .forEach(var -> contextClassBuilder.addField(var, getTypeFromVarName(var)));
        return contextClassBuilder.build();
    }

    private static Class getTypeFromVarName(String var) {
        return var.startsWith("is") ? Boolean.class : String.class;
    }
}
