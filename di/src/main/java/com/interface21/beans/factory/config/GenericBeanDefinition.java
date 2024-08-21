package com.interface21.beans.factory.config;

import com.interface21.beans.factory.support.BeanFactoryUtils;
import com.interface21.beans.factory.support.BeanInstantiationException;
import com.interface21.beans.factory.support.NoUniqueBeanDefinitionException;
import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.Set;

public class GenericBeanDefinition implements BeanDefinition{

  private final Class<?> type;
  private final String beanClassName;
  private final Constructor<?> constructor;

  private GenericBeanDefinition(Class<?> type, String beanClassName, Constructor<?> constructor) {
    this.type = type;
    this.beanClassName = beanClassName;
    this.constructor = constructor;
  }

  public static GenericBeanDefinition from(Class<?> beanClass) {
    return new GenericBeanDefinition(beanClass, getBeanName(beanClass.getSimpleName()), getConstructor(beanClass));
  }

  private static String getBeanName(String beanClassName) {
    // make camelCase
    return beanClassName.substring(0, 1).toLowerCase() + beanClassName.substring(1);
  }

  private static Constructor<?> getConstructor(Class<?> clazz) {
    return getAutowiredConstructor(clazz)
        .orElseGet(() -> getDefaultConstructor(clazz));
  }

  private static Optional<Constructor> getAutowiredConstructor(Class<?> clazz) {
    Set<Constructor> injectedConstructors = BeanFactoryUtils.getInjectedConstructors(clazz);

    if (injectedConstructors.size() > 1) {
      throw new NoUniqueBeanDefinitionException("No qualifying bean of type " + clazz.getTypeName());
    }

    return injectedConstructors.stream().findAny();
  }

  private static Constructor<?> getDefaultConstructor(Class<?> clazz) {
    Constructor<?>[] constructors = clazz.getConstructors();

    if (constructors.length == 1) {
      return constructors[0];
    } else if (constructors.length == 0) {
      return null;
    }

    throw new BeanInstantiationException(clazz, "No default constructor found.");
  }

  @Override
  public Class<?> getType() {
    return type;
  }

  @Override
  public String getBeanClassName() {
    return beanClassName;
  }

  @Override
  public Constructor<?> getConstructor() {
    return constructor;
  }
}
