

一站式聚合搜索平台、简化版的企业级搜索中台



基于 Spring Boot + Elastic Stack（+ Vue 3）的一站式聚合搜索平台。用户可在同一页面集中搜索出不同来源、不同类型的内容（建议具体列举具体的数据类别，比如文章、图片、用户、专栏、视频等），提升搜索体验。也可以直接将各项目的数据接入搜索平台，复用同一套搜索后端，提升开发效率、降低系统维护成本。



### 技术选型

#### 前端

- Vue 3
- Ant Design Vue 组件库



#### 后端

- Spring Boot 2.7 框架 + springboot-init 脚手架
- MySQL 数据库（8.x 版本）
- Elastic Stack

- - Elasticsearch 搜索引擎（重点）
  - Logstash 数据管道
  - Kibana 数据可视化

- 数据抓取（jsoup、HttpClient 爬虫）

- - 离线
  - 实时

- 设计模式

- - 门面模式
  - 适配器模式
  - 注册器模式

- 数据同步（4 种同步方式）

- - 定时
  - 双写
  - Logstash
  - Canal

- JMeter 压力测试