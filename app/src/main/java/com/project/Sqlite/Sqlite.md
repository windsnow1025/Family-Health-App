# Sqlite文档

没有经过测试
### 查询账号的信息
* public UserInfo findUser(Account account)

### 查询指定账号是否存在在本地数据库中
* public boolean is_Exist(Account account)

### 用户登录后添加用户信息到数据库中
* public void addUser(Account account, UserInfo userInfo)

### 修改用户信息
* public void updateUser(Account account, UserInfo userInfo)

### 账号登出
* public void userLoginout(Account account)

### 多账号添加 需要账号信息存入本地数据库
* public void MultiUserAdd(Account account)

### 多账号删除
* public void MultiUserDelete(Account account)

### 多账号切换
* public void MultiUserChange(Account old_account,Account new_account)

### 获得所有多账号的信息
* public List<UserInfo> getAllUser()