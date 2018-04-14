package handlebars.classBuilders;

import static javax.lang.model.element.Modifier.PUBLIC;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import handlebars.functionNamers.FunctionNamer;

public class ContextClassBuilder {

    public ContextClassBuilder(String className, FunctionNamer setterNamer, FunctionNamer getterNamer) {
        this.setterNamer = setterNamer;
        this.getterNamer = getterNamer;

        this.className = ClassName.get("", className);
        this.classBuilder = TypeSpec.classBuilder(className);
    }

    protected TypeSpec.Builder classBuilder;
    protected FunctionNamer setterNamer;
    protected ClassName className;
    protected FunctionNamer getterNamer;

    public MethodSpec buildSetterFunction(String inputParam, Class type) {
        final String name = setterNamer.name(inputParam);
        return MethodSpec.methodBuilder(name)
                .addModifiers(PUBLIC)
                .returns(className)
                .addParameter(type, inputParam)
                .addStatement("this.$N = $N", inputParam, inputParam)
                .addStatement("return this")
                .build();
    }

    public MethodSpec buildGetterFunction(String inputParam, Class type) {
        final String name = getterNamer.name(inputParam);
        return MethodSpec.methodBuilder(name)
                .addModifiers(PUBLIC)
                .returns(type)
                .addStatement("return $N", inputParam)
                .build();
    }

    public void addField(String inputParam) {
        addField(inputParam, Object.class);
    }

    public void addField(String inputParam, Class type) {
        final MethodSpec setter = buildSetterFunction(inputParam, type);
        final MethodSpec getter = buildGetterFunction(inputParam, type);
        classBuilder
                .addField(type, inputParam, Modifier.PRIVATE)
                .addMethod(setter)
                .addMethod(getter);
    }

    public JavaFile build() {
        final TypeSpec typeSpec = classBuilder.addModifiers(Modifier.PUBLIC)
                .build();
        return JavaFile.builder(className.packageName(), typeSpec)
                .build();

    }
}
