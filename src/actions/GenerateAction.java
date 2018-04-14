package actions;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import com.squareup.javapoet.JavaFile;

import handlebars.classBuilders.ContextClassBuilder;
import handlebars.classBuilders.JavaContextClassBuilder;
import handlebars.functionNamers.GetterFunctionNamer;
import handlebars.functionNamers.SetterFunctionNamer;

public class GenerateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String data = e.getData(LangDataKeys.FILE_TEXT);

        String javaContextClassName = createNameOfClassToBeGenerated(e);
        ContextClassBuilder contextClassBuilder = getContextClassBuilder(javaContextClassName);
        JavaFile javaFile = JavaContextClassBuilder.createContextClass(data, contextClassBuilder);
        try {
            javaFile.writeTo(getTriggeringFileDirectory(e));
        } catch (IOException e1) {
        }
    }

    @NotNull
    protected ContextClassBuilder getContextClassBuilder(String javaContextClassName) {
        return new ContextClassBuilder(javaContextClassName, new SetterFunctionNamer(), new GetterFunctionNamer());
    }

    @NotNull
    private String createNameOfClassToBeGenerated(AnActionEvent e) {
        String handlebarsFileName = e.getData(LangDataKeys.PSI_FILE).getName();
        return appendToFileName(handlebarsFileName, "Context");
    }

    private Path getTriggeringFileDirectory(AnActionEvent e) {
        return Paths.get(e.getData(LangDataKeys.VIRTUAL_FILE).getParent().getPath());
    }

    @NotNull
    private String appendToFileName(String className, String suffix) {
        return className.substring(0, className.indexOf(".")) + suffix;
    }

    @Override
    public void update(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        e.getPresentation()
                .setEnabled(isFileWithValidType(psiFile));
    }

    private boolean isFileWithValidType(PsiFile psiFile) {
        return psiFile != null &&
                (psiFile.getName().contains(".hbs") || psiFile.getName().endsWith(".html"));
    }
}
