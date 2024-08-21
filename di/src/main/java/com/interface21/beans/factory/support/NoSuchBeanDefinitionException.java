package com.interface21.beans.factory.support;

public class NoSuchBeanDefinitionException extends IllegalArgumentException {
  public NoSuchBeanDefinitionException(String name) {
    super("No bean named '" + name + "' available");
  }
}
