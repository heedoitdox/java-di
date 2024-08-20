package com.interface21.context;

import java.util.Map;
import java.util.Set;

public interface ApplicationContext {
    <T> T getBean(Class<T> clazz);

    Set<Class<?>> getBeanClasses();

    Map<Class<?>, Object> getControllers();
}
