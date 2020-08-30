import java.sql.*;

/**
 * Created by tanghaojie on 2020/8/26
 */
public class DataUtil {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:8889/user?useSSL=true";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        System.out.println(findUser("mimi12"));
        // User user = new User("zhangsan", 88);
        // updateUser(user);
    }
    public static User findUser(String username) {
        String sql = "SELECT username, score FROM rock_paper_scissors_user where username='" + username + "'";
        return execute("query", sql);
    }

    public static void insertUser(User user){
        String sql = "insert into rock_paper_scissors_user(username, score)values('"+user.name+"',"+user.score+")";
        execute("update", sql);
    }

    public static void updateUser(User user){
        String sql = "update rock_paper_scissors_user set score="+user.score+" where username='"+user.name+"'";
        execute("update", sql);
    }
    public static User execute(String type, String sql) {
        Connection conn = null;
        Statement stmt = null;
        User user = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            // System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();

            if("query".equals(type)){

                ResultSet rs = stmt.executeQuery(sql);
                // 展开结果集数据库
                if(rs.next()){
                    // 通过字段检索
                    String name = rs.getString("username");
                    int score = rs.getInt("score");
                    user = new User(name, score);
                }
                rs.close();
            }else{
                stmt.executeUpdate(sql);
            }
            // 完成后关闭
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return user;
    }
}
