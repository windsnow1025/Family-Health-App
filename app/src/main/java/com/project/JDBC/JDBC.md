# JDBC文档

### WIP

### 进度
user部分修改完成(未做多线程 后续可能进行修改 计划可能修改这部分中的检测)
user部分单元测试无问题 android环境于所在线程有关系
History、Report正在同步修改
ALert计划中

### 问题
JDBC连接有问题(已解决 使用旧版驱动 新版提示class missed)

android主线程无法进行耗时的操作

解决方法1:使用多线程 新问题:数据同步问题 线程通信问题(Thread+handler or AsyncTasks)
解决方法2:禁用android的这一限制 问题主线程容易出现假死
