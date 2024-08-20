package com.interface21.beans.factory.support;

import com.interface21.beans.BeanInstantiationException;
import com.interface21.beans.BeanUtils;
import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.config.BeanDefinition;
import com.interface21.beans.factory.config.GenericBeanDefinition;
import com.interface21.context.stereotype.Controller;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultListableBeanFactory implements BeanFactory {

  private static final Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);

  private final String[] basePackages;

  private final Map<Class<?>, BeanDefinition> beanDefinitionMap = new HashMap<>();

  private final Map<Class<?>, Object> singletonObjects = new HashMap<>();

  public DefaultListableBeanFactory(String... basePackages) {
    this.basePackages = basePackages;
  }

  @Override
  public Set<Class<?>> getBeanClasses() {
    return beanDefinitionMap.keySet();
  }

  public Map<Class<?>, Object> getControllers() {
    Map<Class<?>, Object> controllers = new HashMap<>();
    for (Map.Entry<Class<?>, Object> map : singletonObjects.entrySet()) {
      findController(map, controllers);
    }

    return controllers;
  }

  private void findController(Entry<Class<?>, Object> map, Map<Class<?>, Object> controllers) {
    final Class<?> clazz = map.getKey();
    if (clazz.isAnnotationPresent(Controller.class)) {
      controllers.put(clazz, map.getValue());
    }
  }

  @Override
  public <T> T getBean(final Class<T> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }

    Object object = singletonObjects.get(clazz);
    if(ObjectUtils.isEmpty(object)) {
      createBean(clazz);
      return clazz.cast(singletonObjects.get(clazz));
    }

    return clazz.cast(object);
  }

  public void initialize() {
    Set<Class<?>> beans = new BeanScanner(basePackages).scan();
    for (Class<?> clazz : beans) {
      final GenericBeanDefinition beanDefinition = GenericBeanDefinition.from(clazz);
      beanDefinitionMap.put(clazz, beanDefinition);
    }

    for(Map.Entry<Class<?>, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
      createBean(entry.getKey());
    }
  }

  private void createBean(Class<?> beanClass) {
    Optional<Class<?>> concreteClass = BeanFactoryUtils.findConcreteClass(beanClass, getBeanClasses());
    if (concreteClass.isEmpty()) {
      throw new BeanInstantiationException(beanClass, "Could not find concrete class for bean class");
    }

    final Object instance = instantiateConstructor(concreteClass.get());
    singletonObjects.put(concreteClass.get(), instance);

    log.info("Registered bean of type [{}] with name [{}] in the singleton context.",
        beanClass.getName(), instance.getClass().getName());
  }

  private Object instantiateConstructor(Class<?> concreteClass) {
    final BeanDefinition beanDefinition = beanDefinitionMap.get(concreteClass);
    Constructor<?> constructor = beanDefinition.getConstructor();
    final Class<?>[] parameterTypes = beanDefinition.getParameterTypes();
    final Object[] arguments = findArguments(parameterTypes);

    return BeanUtils.instantiateClass(constructor, arguments);
  }

  private Object[] findArguments(Class<?>[] parameterTypes) {
    Object[] arguments = new Object[parameterTypes.length];

    for (int i = 0; i < parameterTypes.length; i++) {
      Optional<Class<?>> implClass = BeanFactoryUtils.findConcreteClass(parameterTypes[i], getBeanClasses());
      if (implClass.isEmpty()) {
        throw new NoSuchBeanDefinitionException("Could not find concrete class for bean class");
      }
      arguments[i] = this.getBean(implClass.get());
    }

    return arguments;
  }

  @Override
  public void clear() {
    beanDefinitionMap.clear();
    singletonObjects.clear();
  }
}
