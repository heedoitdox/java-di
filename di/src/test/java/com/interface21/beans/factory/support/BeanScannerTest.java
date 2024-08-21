package com.interface21.beans.factory.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.JdbcSampleRepository;
import samples.SampleController;
import samples.SampleService;

class BeanScannerTest {

  @Test
  @DisplayName("@Repository, @Service, @Controller 어노테이션이 존재하는 모든 클래스를 스캔한다")
  void scanTest() {
    BeanScanner scanner = new BeanScanner("samples");

    Set<Class<?>> result = scanner.scan();

    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result).isNotEmpty(),
        () -> assertThat(result.contains(JdbcSampleRepository.class)).isTrue(),
        () -> assertThat(result.contains(SampleController.class)).isTrue(),
        () -> assertThat(result.contains(SampleService.class)).isTrue()
    );
  }

}