import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanghaojie on 2020/8/28
 */
public class GameManager {
    private static Map<String, Socket> socketMap = new HashMap<>();
    private static Map<String, User> userMap = new HashMap<>();
    private static Map<String, User> fighterMap = new HashMap<>(); // 保存对手的信息
    private static GameConfig conf = GameConfig.getInstance();

    public ProcessResult processCmd(String cmd){
        ProcessResult result = null;
        Logger.info("User cmd: " + cmd);
        String[] parts = cmd.split(" ");
        String username = parts.length > 1 ? parts[1] : "";
        String msg = "";

        String curUser = "用户 " + username;

        User otherUser = null; // 对手用户
        User user = null; // 当前用户

        switch(parts[0]){
            case "9"://用户登录
                login(username);
                Logger.info(curUser + " 加入了游戏。");
                result = new ProcessResult(username, ProcessResult.OVER, "登录成功。");
                break;
            case "1":  // 查询在线列表
                Logger.info(curUser + " 查询在线列表。");
                for(User u:userMap.values()){
                    msg += u + "\n";
                }
                result = new ProcessResult(username, ProcessResult.OVER, msg);
                break;
            case "2": // 开启战局
                Logger.info(curUser + " 开启一个战局。");
                result = startGame(username);
                break;
            case "3":  // 参加战局
                String other = parts[2];
                Logger.info(curUser + " 参加 " + other + " 的战局。");
                user = userMap.get(username);
                otherUser = userMap.get(other);
                if(otherUser == null){
                    result = new ProcessResult(username, ProcessResult.OVER, "对手不在线！");
                }else if(otherUser.state != 2){
                    result = new ProcessResult(username, ProcessResult.OVER, "对手没有开局！");
                }else{
                    result = new ProcessResult(username, ProcessResult.MULTI, "@start");
                    result.otherUsername = other;
                    result.otherMsg = "@start";
                    user.startGame();
                    otherUser.startGame();
                    fighterMap.put(username, otherUser);
                    fighterMap.put(other, user);
                }
                break;
            case "4": // 出拳
                Logger.info(curUser + " 出拳 " + parts[2]);
                user = userMap.get(username);
                otherUser = fighterMap.get(username);
                if(otherUser.word.isEmpty()){//若对手未出拳
                    Logger.info(curUser + " 出拳 " + parts[2] + " ,对手未出拳。");
                    user.word = parts[2];
                    result = new ProcessResult(username, ProcessResult.KEEP, "");
                }else{//若都了出拳
                    Logger.info(curUser + " 出拳 " + parts[2] + " ,对手出拳 " + otherUser.word + "。");
                    user.word = parts[2];
                    result = new ProcessResult(username, ProcessResult.MULTI, "");
                    result.otherUsername = otherUser.name;
                    result.msg = user.fight(otherUser.word);
                    result.otherMsg = otherUser.fight(user.word);
                    DataUtil.updateUser(user);
                    DataUtil.updateUser(otherUser);
                    String fightRecord = "|"+otherUser.name+"|"+otherUser.score+"|"+otherUser.delta
                            +"|"+user.name+"|"+user.score+"|"+user.delta;
                    Logger.logResult(fightRecord);
                }
                break;
            case "0": // 退出游戏
                Logger.info(curUser + " 离开了游戏。");
                userMap.remove(username);
                result = new ProcessResult(username, ProcessResult.OVER, "下次再见。");
                break;
            default:
                result =  new ProcessResult(username, ProcessResult.OVER, cmd);
        }
        return result;
    }


    public void keepSocket(ProcessResult res, Socket socket) {
        socketMap.put(res.username, socket);
    }
    public Socket findSocket(String username){
        return socketMap.get(username);
    }
    public ProcessResult startGame(String username){
        User user = userMap.get(username);
        user.state = 2;
        return new ProcessResult(username, ProcessResult.KEEP, "");
    }
    private void login(String username){
        User user = DataUtil.findUser(username);
        if(user == null){
            user = new User(username);
            DataUtil.insertUser(user);
        }
        userMap.put(user.name, user);
    }
}
