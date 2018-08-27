## BYR校园商铺 V1.0

>旨在开发一个校园 o2o 商铺平台，方便校园商户与学生间的交易。商户入驻平台后可以发布店铺及商品信息，学生可以通过此平台了解校园周边的服务。

### 功能模块
![功能模块](https://github.com/WavyPeng/CampusShops/blob/master/src/main/resources/images/BYR%E5%8A%9F%E8%83%BD%E6%A8%A1%E5%9D%97.jpg)
### 平台架构
![平台架构](https://github.com/WavyPeng/CampusShops/blob/master/src/main/resources/images/BYR%E5%B9%B3%E5%8F%B0%E6%9E%B6%E6%9E%84.jpg)

### 开发环境
IntelliJ IDEA 15.0.6

JDK1.8

### 技术栈
前端：SUI Mobile 

后端：Spring+Spring MVC+MyBatis

数据库：MySQL

缓存：Redis

### 相关优化
本项目主要做了如下几个方面的优化：
- 对头条信息、店铺分类、商品分类等不经常更新的信息，用Redis缓存技术进行处理，以减少对数据库的访问
- 为防止用户恶意操作，实现相关拦截器对非法请求进行拦截处理
- 为了减轻MySQL的访问压力，在DAO层设计MyBatis级别的拦截器，实现主从复制、读写分离

项目中相关优化的具体实现可以参考我的博客，链接如下：
- [拦截器实现]()
- [MySQL主从复制与读写分离](http://wavy.top/mysql%E4%B9%8B%E4%B8%BB%E4%BB%8E%E5%A4%8D%E5%88%B6%E4%B8%8E%E8%AF%BB%E5%86%99%E5%88%86%E7%A6%BB.html)



