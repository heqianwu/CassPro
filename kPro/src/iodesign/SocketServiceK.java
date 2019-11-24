package iodesign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServiceK {
    public static void main(String[] args) throws IOException {
        SocketServiceK sk = new SocketServiceK();
        sk.server();
    }


    public void server() {
        try {
            ServerSocket server = null;
            try {
                server = new ServerSocket(8000);
                System.out.println("服务器启动成功");
            } catch (Exception ex) {
                System.out.println("启动服务监听失败" + ex);
                return;
            }

            Socket socket = null;
            try {
                socket = server.accept();
                System.out.println("成功建立客户端连接");
            } catch (Exception ex) {
                System.out.println("建立客户端连接失败" + ex);
                return;
            }

            String line;
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer =new PrintWriter(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            line = in.readLine();
            while(!line.equals("end")){
                System.out.println("Client:"+line);
                line=br.readLine();
                writer.println(line);
                writer.flush();
                System.out.println("Server:"+line);
                line=in.readLine();
            }

            writer.close();
            in.close();
            br.close();
            socket.close();
            server.close();


        } catch (Exception ex) {

        }
    }

}
