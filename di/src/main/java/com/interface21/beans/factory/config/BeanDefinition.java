package com.interface21.beans.factory.config;

import java.lang.reflect.Constructor;

public interface BeanDefinition {

    Class<?> getType();

    String getBeanClassName();

    Constructor<?> getConstructor();
}
