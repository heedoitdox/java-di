package com.interface21.beans.factory.support;

public class BeanInstantiationException extends IllegalArgumentException{

  public BeanInstantiationException(final Class<?> beanClass, String message) {
    super("Failed to instantiate %s: %s".formatted(beanClass.getSimpleName(), message));
  }
}
