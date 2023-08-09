package ua.lann.protankiserver.reflection;

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
    if (annotation == null || basePackage == null) {
      return annotatedClasses; 
    }
    
    String basePackagePath = basePackage.replace('.', '/');

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      return annotatedClasses;
    }

    Enumeration<URL> resources = classLoader.getResources(basePackagePath);
    if (resources == null) {
      return annotatedClasses;
    }

    while(resources.hasMoreElements()) {

      URL resource = resources.nextElement();
      if (resource == null) {
        continue;
      }

      File file = new File(resource.getFile());
      if (file == null) {
        continue;
      }

      findAnnotatedClasses(annotation, basePackage, file, annotatedClasses);
    }

    return annotatedClasses;
  }

  private static void findAnnotatedClasses(Class<? extends Annotation> annotation, String packageName, File directory, List<Class<?>> classList) throws ClassNotFoundException {
    
    if (annotation == null || packageName == null || directory == null) {
      return;
    }
    
    if (classList == null) {
      classList = new ArrayList<>(); 
    }

    File[] files = directory.listFiles();
    if (files == null) {
      return;
    }

    for (File file : files) {

      if (file.isDirectory()) {
        findAnnotatedClasses(annotation, packageName + "." + file.getName(), file, classList);
      } else if (file.getName().endsWith(".class")) {
      
        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
        Class<?> clazz = Class.forName(className);
        if (clazz != null && clazz.isAnnotationPresent(annotation)) {
          classList.add(clazz);  
        }
      }
    }
  }
}