package ua.lann.protankiserver.reflection;

import ua.lann.protankiserver.reflection.annotations.PacketHandler;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class AnnotationHelper {
    public static List<Class<?>> load(Class<? extends Annotation> annotation, String basePackage) throws IOException, ClassNotFoundException {
        List<Class<?>> annotatedClasses = new ArrayList<>();
        String basePackagePath = basePackage.replace('.', '/');

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(basePackagePath);

        while(resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File file = new File(resource.getFile());
            findAnnotatedClasses(annotation, basePackage, file, annotatedClasses);
        }

        return annotatedClasses;
    }

    private static void findAnnotatedClasses(Class<? extends Annotation> annotation, String packageName, File directory, List<Class<?>> classList) throws ClassNotFoundException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                findAnnotatedClasses(annotation, packageName + "." + file.getName(), file, classList);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(annotation)) {
                    classList.add(clazz);
                }
            }
        }
    }
}
