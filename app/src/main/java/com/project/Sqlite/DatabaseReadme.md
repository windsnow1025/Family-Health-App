# 数据库说明

## 目前版本的情况

~~目前简单尝试了一下项目的运行，发现一个比较棘手的问题，在activity调用数据库不会有异常 
但是在fragment中调用就会产生空指针异常，无论是sqllite还是mysql都会产生空指针异常 正在尝试解决~~
好像又没有问题 使用时对SQllite的dao记得使用open()方法 对于Mysql部分的记得getConnection和closeConnection 还有新开线程(~~虽然目前大部分的功能都可以通过SQllite实现~~)

### 简单说明

我尽可能减少了使用Mysql数据库的频率，但仍有部分调用需要使用Mysql，而这部分毋庸置疑的需要使用多线程进行调用。
目前关于个人信息的部分以及同步方法均需要多线程调用，而对于类似于体检报告等的数据，目前采取的方案是存于Sqlite数据库，当显式调用
同步时，再同步到Mysql数据库，同步的顺序是现将Mysql数据down到本地后，再将本地数据上传，至此，数据可能存在有一定的同步问题，
正在考虑一个更优秀的方法，解决这一部分的问题。

### 关于测试

测试方面目前依据我做的测试暂时没什么严重的问题，酒吧点了碗炒饭.png　　:(

## 接口方面

### 报告体检提醒
基本都是以同一格式编写

|   方法   |                    函数                    |            备注             |
|:------:|:----------------------------------------:|:-------------------------:|
|  获取列表  |        getXXXList(String account)        | Integer参数用于同步，正常获取不需要另外参数 |
| 　插入　　  |    insertXXX(String account,XXX xxx)     |             无             |
| 　更新　　  |    updateXXX(String account,XXX xxx)     |             无             |
| 　删除　　  | deleteXXX(String account,Integer xxx_No) |             无             |
|  获取序号  |       getXXXCount(String account)        |       获取序号，正常情况无需调用       |
|  获取单个  |   getXXX(xxxArrayList,Integer xxx_No)    |       从列表中获取单个,静态方法       |
|   同步   |                  sync()                  |            同步             |
| 同步(下载) |             sync_Download()              |        同步，从远程下载数据         |
| 同步(上传) |              sync_Upload()               |         同步,将数据上传          |

### 用户相关(本地)

|    方法     |                       函数                        |           备注            |
|:---------:|:-----------------------------------------------:|:-----------------------:|
|   获取用户    |                   getUser( )                    |        获取当前登录用户         |
|  　检查存在　　  |            checkUser(String account)            | 检查用户存在与否，用于向数据库中写入用户是检查 |
| 　获取用户信息　　 |           gerUserInfo(String account)           |      从本地数据获取指定用户信息      |
|  　登出用户　　  |          userLoginOut(String account)           |            无            |
|  用户信息添加   |       addOrUpdateUser(UserInfo userInfo)        |      将用户信息添加或更新到本地      |
|   多用户添加   |            addMulti(String account)             |       多用户仅实现，未测试        |
|   多用户删除   |           deleteMulti(String account)           |           同上            |
|  获取多用户列表  |                 getMultiList()                  |           同上            |
|   多用户切换   | userAlter(String newAccount,String oldAccount)  |           同上            |

### 用户相关(远程)

|    方法     |                                                           函数                                                            |     备注     |
|:---------:|:-----------------------------------------------------------------------------------------------------------------------:|:----------:|
|  检查账号密码   |                                    checkUserPassword(String account,String password)                                    | 正确返回账号，否则空 |
| 　检查用户独特　　 |                                             checkUserUnique(String account)                                             |     无      |
|  　添加用户　　  |                     insertUser(@NonNull String account,String password,String sex,String birthday)                      |   注意生日格式   |
| 　获取用户信息　　 |                                           getUserInformation(String account)                                            |     无      |
|  更新用户信息   |                      updateUserInformation(String account,HashMap<String,String> userInfo_update)                       |   考虑修改参数   |