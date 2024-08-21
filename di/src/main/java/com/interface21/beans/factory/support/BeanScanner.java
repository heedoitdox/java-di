package com.interface21.beans.factory.support;

import com.interface21.context.stereotype.Controller;
import com.interface21.context.stereotype.Repository;
import com.interface21.context.stereotype.Service;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;

public final class BeanScanner {

  private final Reflections reflections;
  private final List<Class<? extends Annotation>> beanAnnotations;

  public BeanScanner(String... basePackages) {
    this.reflections = new Reflections(basePackages);
    this.beanAnnotations = List.of(Controller.class, Service.class, Repository.class);
  }

  public Set<Class<?>> scan() {
    return beanAnnotations.stream()
        .map(reflections::getTypesAnnotatedWith)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());
  }


  // TODO 로 진행하겠습니다
//  public Set<Class<?>> scan() {
//    Set<Class<?>> result = new HashSet<>();
//
//    for (Class<? extends Annotation> annotation : beanAnnotations) {
//      Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(annotation);
//
//      for (Class<?> clazz : annotatedClasses) {
//
//        if (clazz.isAnnotationPresent(Configuration.class)) {
//          collectBeanMethodReturnTypes(clazz, result);
//        } else {
//          result.add(clazz);
//        }
//      }
//    }
//
//    return result;
//  }

  // TODO 로 진행하겠습니다
//  private void collectBeanMethodReturnTypes(Class<?> configurationClass, Set<Class<?>> result) {
//    Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(configurationClass, Bean.class);
//
//    for (Method method : beanMethods) {
//      result.add(method.getReturnType());
//    }
//  }
}
