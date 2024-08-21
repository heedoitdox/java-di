package com.interface21.beans.factory.support;

public class NoUniqueBeanDefinitionException extends IllegalArgumentException{

  public NoUniqueBeanDefinitionException(String message) {
    super(message);
  }
}
