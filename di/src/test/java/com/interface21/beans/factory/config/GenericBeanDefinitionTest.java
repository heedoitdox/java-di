package com.interface21.beans.factory.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.interface21.beans.factory.support.BeanInstantiationException;
import com.interface21.beans.factory.support.NoUniqueBeanDefinitionException;
import java.lang.reflect.Constructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.SampleService;

class GenericBeanDefinitionTest {

  @Test
  @DisplayName("@Autowired 어노테이션이 붙은 생성자를 가진 객체를 BeanDefinition 객체로 만든다")
  void withAutowiredConstructorTest() {
    final GenericBeanDefinition beanDefinition = GenericBeanDefinition.from(SampleService.class);
    final String expectedBeanClassName = "sampleService";
    final Constructor<?> constructor = beanDefinition.getConstructor();

    assertAll(
        () -> assertThat(beanDefinition).isNotNull(),
        () -> assertThat(expectedBeanClassName).isEqualTo(beanDefinition.getBeanClassName()),
        () -> assertThat(constructor).isNotNull()
    );
  }

  @Test
  @DisplayName("기본 생성자를 가진 객체를 BeanDefinition 객체로 만든다")
  void testFrom_withDefaultConstructor() {
    final GenericBeanDefinition beanDefinition = GenericBeanDefinition.from(NoAutowiredService.class);
    final String expectedBeanClassName = "noAutowiredService";
    final Constructor<?> constructor = beanDefinition.getConstructor();

    assertAll(
        () -> assertThat(beanDefinition).isNotNull(),
        () -> assertThat(expectedBeanClassName).isEqualTo(beanDefinition.getBeanClassName()),
        () -> assertThat(constructor).isNotNull()
    );
  }

  @Test
  @DisplayName("@Autowired 어노테이션이 붙은 생성자가 여러개라면 에러가 발생한다")
  void noUniqueBeanDefinitionExceptionTest() {
    assertThrows(NoUniqueBeanDefinitionException.class,
        () -> GenericBeanDefinition.from(MultipleAutowiredService.class)
    );
  }

  @Test
  @DisplayName("생성자가 여러개라면 에러가 발생한다")
  void testFrom_beanInstantiationException() {
    assertThrows(BeanInstantiationException.class,
        () -> GenericBeanDefinition.from(NoConstructorService.class)
    );
  }

}