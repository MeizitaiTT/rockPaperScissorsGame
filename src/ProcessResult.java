/**
 * Created by tanghaojie on 2020/8/29
 */
public class ProcessResult {
    public static final int OVER = 0; // 处理完成
    public static final int KEEP = 1; // 等待结果
    public static final int MULTI = 2; // 对战结果

    public String username; // 客户端的用户名
    public String otherUsername; // 对手的用户名
    public int state; // 命令处理状态
    public String msg; // 返回给客户端的信息
    public String otherMsg; // 发送给对手的信息
    public int fightResult; // 对战结果  0 输 1 赢 2 平

    public ProcessResult(String username, int state, String msg){
        this.username = username;
        this.state = state;
        this.msg = msg;
    }
}
