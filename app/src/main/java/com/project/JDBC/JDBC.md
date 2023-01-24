# JDBC文档

这部分代码因为时间原因测试

### public Integer Login(Account account)
* 登录 需要account 返回Interger表示登录结果
* 200表示正常登录
* 301表示密码错误
* 404表示账号不存在


### public UserInfo getInfo(Account account)
获取用户信息 返回UserInfo

### public Integer AlterPassword(Account account,String newPassword)
* Login()函数调用查询账户
* 200表示成功查询账户但因某些原因没有成功修改密码
* 201表示表示密码修改成功
* 301表示密码错误
* 404表示账号不存在


### public Integer Register(Account account,String sex,String birthday)
* 注册 必须填入账户，性别和生日
* 生日使用String类型 格式为yyyy-MM-dd如2022-07-08等
* 此处类型问题原本想使用LocalDate类型 但setDate似乎不支持LocalDate
* 200表示账户存在无法注册
* 202注册成功


### public Integer AlterUserName(Account account,String newUserName)
* 修改用户名
* 返回200表示修改成功

### public Integer AlterEmail(Account account,String newEmail)
* 修改邮箱
* 返回200表示修改成功