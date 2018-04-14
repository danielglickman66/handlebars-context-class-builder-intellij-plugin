package handlebars.classBuilders;

import static javax.lang.model.element.Modifier.PUBLIC;

import java.util.function.Function;

import javax.lang.model.element.Modifier;

import org.jetbrains.annotations.NotNull;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import handlebars.functionNamers.FunctionNamer;

public class FunctionalContextClassBuilder extends ContextClassBuilder {
    private final TypeVariableName genericType = TypeVariableName.get("T");

    public FunctionalContextClassBuilder(String className, FunctionNamer setterNamer, FunctionNamer getterNamer) {
        super(className, setterNamer, getterNamer);
    }

    @Override
    public MethodSpec buildSetterFunction(String inputParam, Class type) {
        return MethodSpec
                .methodBuilder(setterNamer.name(inputParam))
                .addModifiers(PUBLIC)
                .returns(className)
                .addParameter(FunctionFromActualToType(type), "func")
                .addStatement("this.$N = $N", inputParam, "func.apply(actual)")
                .addStatement("return this")
                .build();
    }

    @NotNull
    private ParameterizedTypeName FunctionFromActualToType(Class type) {
        return ParameterizedTypeName.get(ClassName.get(Function.class), genericType, ClassName.get(type));
    }

    public JavaFile build() {
        MethodSpec constructor = MethodSpec
                .constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(genericType, "actual")
                .addStatement("this.actual = actual")
                .build();

        final TypeSpec typeSpec = classBuilder
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(genericType)
                .addMethod(constructor)
                .addField(genericType, "actual", Modifier.PRIVATE)
                .build();

        return JavaFile
                .builder(className.packageName(), typeSpec)
                .build();
    }
}
