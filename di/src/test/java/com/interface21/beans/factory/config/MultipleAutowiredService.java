package com.interface21.beans.factory.config;

import com.interface21.beans.factory.annotation.Autowired;
import com.interface21.context.stereotype.Service;

@Service
public class MultipleAutowiredService {

  private final NoAutowiredService noAutowiredService;

  @Autowired
  public MultipleAutowiredService(NoAutowiredService noAutowiredService) {
    this.noAutowiredService = noAutowiredService;
  }

  @Autowired
  public MultipleAutowiredService() {
    this.noAutowiredService = new NoAutowiredService();
  }
}
