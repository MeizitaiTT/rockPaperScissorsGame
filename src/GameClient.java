import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tanghaojie on 2020/8/27
 */
public class GameClient {
    private String username;
    Scanner sc = new Scanner(System.in);


    // 一轮消息
    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.start();
    }

    private void printHint(int type){
        if(type == 0){
            System.out.println("1 查看在线用户 2 开战局 3 加入战局 0 退出游戏");
            System.out.println("请输入序号执行对应的菜单:");
        }else if(type == 1){
            System.out.println("昵称规则:由英文字母、数字组合，首字符不能是数字。");
            System.out.println("请输入你的昵称:");
        }else if(type == 2){
            System.out.println("请输入对手的昵称:");
        }
    }
    private void start() {
        //登录注册昵称
        printHint(1);
        String username = sc.nextLine().trim();
        while(!username.matches("[a-zA-Z]+[a-zA-Z0-9]*")){
            System.out.println("昵称不符合规则！");
            printHint(1);
            username = sc.nextLine().trim();
        }

        this.username = username;

        //用户登录
        sendMsg("9 " + username);

        //登录后选择命令
        printHint(0);
        String line = sc.nextLine().trim();
        String[] parts = line.split("\\s+");
        String cmd = parts[0];
        String result = null;
        while(!"q".equals(cmd)){
            switch(cmd){
                case "1":
                    System.out.println("查看在线用户");
                    sendMsg(cmd + " " + username);
                    break;
                case "2":
                    System.out.println("开战局");
                    result = sendMsg(cmd + " " + username);
                    if("start".equals(result)){
                        sendWord();
                    }
                    break;
                case "3":
                    System.out.println("加入战局");
                    printHint(2);
                    line = sc.nextLine().trim();
                    result = sendMsg(cmd + " " + username + " " + line);
                    if("start".equals(result)){
                        sendWord();
                    }
                    break;
                case "0":
                    System.out.println("退出游戏");
                    sendMsg(cmd + " " + username);
                    break;
                default:
                    System.out.println("错误的菜单序号！");
            }
            if("0".equals(cmd)){
                break;
            }else{
                printHint(0);
                cmd = sc.nextLine().trim();
            }
        }

        // sendMsg("abc");

    }
    private void sendWord(){
        System.out.println("请输入你要出的拳(A石头 S剪刀 D布):");
        List<String> words = Arrays.asList(new String[]{"A", "S", "D"});
        String word = sc.nextLine().toUpperCase();
        while(!words.contains(word)){
            System.out.println("输入出拳错误！");
            System.out.println("请输入你要出的拳(A石头 S剪刀 D布):");
            word = sc.nextLine().toUpperCase();
        }
        sendMsg("4 " + this.username + " " + word);
    }
    private String sendMsg(String cmd) {
        String result = "";

        //1.创建socket用来与服务器端进行通信，发送请求建立连接，指定服务器地址和端口
        try (Socket socket=new Socket("localhost", GameServer.port);){
            //2.获取输出流用来向服务器端发送登陆的信息
            OutputStream os = socket.getOutputStream();//字节输出流
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));//将输出流包装成打印流

            // 如果服务器是读取行，那客户端一定要写入换行符，否则服务器会一直等换行
            bufferedWriter.write(cmd);
            bufferedWriter.flush();
            socket.shutdownOutput();//关闭输出流

            //3.获取输入流，用来读取服务器端的响应信息
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String info = null;
            List<String> lines = new ArrayList<>();
            while((info = br.readLine()) != null){
                lines.add(info);
            }
            System.out.print("服务器端返回:");
            for(String line: lines){
                System.out.println(line);
            }
            if(lines.size() == 1 && "@start".equals(lines.get(0))){
                result = "start";
            }
            //4.关闭其他相关资源
            br.close();
            inputStream.close();
            bufferedWriter.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端连接出错");
        }
        return result;
    }
}
