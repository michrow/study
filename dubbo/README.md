# 子项目说明

- base-service

  基础服务，提供了数据的增删改查

  ```java
  	void add(Emp emp);
  	void delete(Integer id);
  	void update(Emp emp);
  	Emp find(Emp emp);
  	Emp findById(Integer id);
  ```

  api-config

  Dubbo服务调用的API形式，API 仅用于 OpenAPI, ESB, Test, Mock 等系统集成

  - provider

    服务提供者

  - consumer

    服务消费者

  