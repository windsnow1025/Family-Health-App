# JDBC文档

### WIP

### 进度
user部分修改完成(未做多线程 后续可能进行修改 计划可能修改这部分中的检测)
userDao部分单元测试无问题 android环境与所在线程有关系
HistoryDao、ReportDao单元测试可用，暂未使用无效等价类测试 估计和user部分相似在android环境中运行与线程相关
AlertDao单元测试可用 未测试无效等价类

准备开发sqllite的Dao
### 问题
JDBC连接有问题(已解决 使用旧版驱动 新版提示class missed)

android主线程无法进行耗时的操作

解决方法1:使用多线程 新问题:数据同步问题 线程通信问题(Thread+handler or AsyncTasks)
解决方法2:禁用android的这一限制 问题主线程容易出现假死
