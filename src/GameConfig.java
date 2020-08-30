import java.util.List;

/**
 * Created by tanghaojie on 2020/8/28
 */
public class GameConfig {

    public int winScore = 3;    // 赢者得分
    public int loseScore = -3;  // 输者得分
    public int drawScore = 1;   // 平局得分

    private static GameConfig instance;

    private GameConfig(){
        // 读取游戏配置
        try{
            List<String> lines = FileUtil.readLines("config.txt");
            String[] parts = lines.get(0).split(" ");
            winScore = Integer.parseInt(parts[0]);
            loseScore = Integer.parseInt(parts[1]);
            drawScore = Integer.parseInt(parts[2]);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static GameConfig getInstance(){
        //双检锁模式构建单例
        if(instance == null){
            synchronized (GameConfig.class){
                if(instance == null){
                    instance = new GameConfig();
                }
            }
        }
        return instance;
    }
}
