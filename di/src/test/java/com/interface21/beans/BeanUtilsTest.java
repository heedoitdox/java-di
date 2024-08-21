package com.interface21.beans;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BeanUtilsTest {

  static class FooService {
    private final BarRepository barRepository;

    public FooService(BarRepository barRepository) {
      this.barRepository = barRepository;
    }
  }

  static class BarRepository {
  }

  @Test
  @DisplayName("생성자와 인자로 인스턴스를 생성한다")
  void instantiateClassTest() {
    final Class<?> clazz = FooService.class;
    final Constructor<?> constructor = clazz.getConstructors()[0];
    final Object[] arguments = new Object[1];
    arguments[0] = new BarRepository();

    final Object result = BeanUtils.instantiateClass(constructor, arguments);

    assertThat(result instanceof FooService).isTrue();
  }
}