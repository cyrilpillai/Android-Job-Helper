package com.cyrilpillai.compiler.processor;

import com.cyrilpillai.annotation.IsJob;
import com.cyrilpillai.compiler.util.BaseUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class JobAnnotationProcessor extends AbstractProcessor {

    private Filer filer;
    private Elements elementUtils;


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(IsJob.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        filer = env.getFiler();
        elementUtils = env.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        StringBuilder switchCase = new StringBuilder();
        switchCase.append("switch (tag) {\n");
        for (Element element : roundEnv.getElementsAnnotatedWith(IsJob.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                String className = elementUtils.getPackageOf(element).getQualifiedName().toString()
                        + "." + element.getSimpleName();
                switchCase.append("    case ")
                        .append(className)
                        .append(".TAG:\n")
                        .append("                return new ")
                        .append(className)
                        .append("();\n");
            }
        }

        switchCase.append("    default:\n" +
                "                return null;\n" +
                "        }\n");

        String jobPackage = "com.evernote.android.job";
        ClassName jobClass = ClassName.get(jobPackage, "Job");
        ClassName jobCreatorClass = ClassName.get(jobPackage, "JobCreator");

        MethodSpec createSpec = MethodSpec.methodBuilder("create")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "tag")
                .returns(jobClass)
                .addCode(switchCase.toString())
                .build();

        TypeSpec jobSpec = TypeSpec.classBuilder("AppJobCreator")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(BaseUtil.getGenerationDetails(JobAnnotationProcessor.class))
                .addSuperinterface(jobCreatorClass)
                .addMethod(createSpec)
                .build();


        JavaFile javaFile = JavaFile.builder(jobPackage, jobSpec)
                .build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}