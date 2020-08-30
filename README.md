# rockPaperScissorsGame

# 1.项目结构说明
     DataUtil—————— 与mysql相关的处理相关类，关于结果信息的更新以及插入操作
     FileUtil—————— 文件信息读取相关类，关于配置信息的读取、结果信息以及日志信息的写入操作
     GameClient—————— 游戏客户端，单线程单次只发送一次消息请求
     GameConfig—————— 游戏配置信息读取，并构建单例保持信息的交互
     GameManager—————— 游戏实际对局信息处理，采用单例hashmap的方式保存各种对局信息
     GameServer—————— 游戏服务端，单线程监听端口方式构建socket编程，并就将内容给manager处理并与将结果与client交互
     Logger—————— logger相关信息的写入，主要设计写入文件
     ProcessResult—————— manager处理结果的实体类
     User———— 用户实体类
# 2.项目运行说明
    基于jdk14实现的方式，使用以compileJava.bat的方式编译所有java文件，将server打包为jar包（设计到外部jar包的引用）
    启动并运行。使用client.bat命令运行client，即可创建多个用户并加入战局。
# 3.项目运行流程说明
    客户端以及服务端都采用单线程的运行方式，采用while(true)的形式进行运行，并使用socket方式进行短链接，每次发送信息
    都会建立新的socket保持一次会话的短链接，因为采用短链接所以交战过程中需要维护socket来保持两方的通话。
    
    