import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by tanghaojie on 2020/8/28
 */
public class GameServer {
    public static int port = 10888;

    boolean running = true;

    GameManager manager;

    public GameServer(){
        manager = new GameManager();
    }

    private void start() {
        //创建绑定到特定端口的服务器套接字
        System.out.println("----------客户端服务启动-----------");
        while(running) {
            try(ServerSocket serversocket = new ServerSocket(port);){
                //建立连接，获取socket对象
                Socket socket=serversocket.accept();
                System.out.println("有客户端连接到了本机端口");
                // 读取输入
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                // 如果服务器是读取行，那客户端一定要写入换行符，否则服务器会一直等换行的
                char[] aa = new char[40];
                int readCount = bufferedReader.read(aa);
                String readContent = new String(aa, 0, readCount);
                ProcessResult res = manager.processCmd(readContent);
                if(ProcessResult.OVER == res.state){
                    sendMsg(socket, res.msg);
                }else if(ProcessResult.KEEP == res.state){
                    manager.keepSocket(res, socket);
                }else if(ProcessResult.MULTI == res.state){
                    sendMsg(socket, res.msg);
                    Socket otherSocket = manager.findSocket(res.otherUsername);
                    sendMsg(otherSocket, res.otherMsg);
                }else{
                    System.out.println("无效的处理结果");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch(Exception e2){
                Logger.error(e2.toString());
                e2.printStackTrace();
            }

        }
    }
    private void sendMsg(Socket socket, String msg) throws IOException {
        OutputStream os=socket.getOutputStream();//字节输出流
        BufferedWriter pw=new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        pw.write(msg);
        pw.write("\n");
        pw.flush();
        socket.close();
    }
    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }
}
