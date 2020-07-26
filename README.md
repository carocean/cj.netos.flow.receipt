# 流转收单服务器
调度的功能由flow项目提供，它负责对网域的推送队列、任务生成及完成过程管理。flow依赖于link以查询推送范围，依赖于networks以实现推送，
但它不直接与coluddisk打交道。

* 依赖项目
- rabbitmq

* 结构：
