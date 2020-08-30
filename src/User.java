/**
 * Created by tanghaojie on 2020/8/26
 */
public class User {
    public String name; // 昵称
    public int score; // 分数
    public int state; // 状态 1 空闲 2 等待 3 正在游戏
    public String word; // 客户出的拳
    public int delta;

    private GameConfig conf = GameConfig.getInstance();

    public User(String name){
        this.name = name;
        this.score = 0;
        this.state = 1;
    }
    public User(String name, int score){
        this.name = name;
        this.score = score;
        this.state = 1;
    }

    public void startGame(){
        state = 3;
        word = "";
    }

    public String toString(){
        String stateStr = "";
        switch(state){
            case 1:
                stateStr = "空闲";
                break;
            case 2:
                stateStr = "等待";
                break;
            case 3:
                stateStr = "正在游戏";
                break;
            default:
                stateStr = "状态不明";
        }
        return "昵称:" + name + " 分数:" + score + " 状态:" + stateStr;
    }

    // A石头 S剪刀 D布
    public String fight(String otherWord){
        String all = word + otherWord;
        int result = -1;
        String msg = null;
        switch(all){
            case "SA":
            case "AD":
            case "DS":
                result =  0;
                break;
            case "AS":
            case "DA":
            case "SD":
                result = 1;
                break;
            default:
                result = 2;
        }
        if(result == 0){
            msg = "输了";
            delta = conf.loseScore;
        }else if(result == 1){
            msg = "赢了";
            delta = conf.winScore;
        }else{
            msg = "打平了";
            delta = conf.drawScore;
        }
        score += delta;
        return msg;
    }
}
