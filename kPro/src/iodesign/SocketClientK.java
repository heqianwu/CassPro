package iodesign;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClientK {

    public static void main(String[] args) throws Exception{
        try{
            Socket socket=new Socket("127.0.0.1",8000);
            System.out.println("客户端启动成功");

            String line;
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer =new PrintWriter(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            line=br.readLine();
            while(!line.equals("end")){
                writer.println(line);
                writer.flush();
                System.out.println("Client:" + line);
                System.out.println("Server:" + in.readLine());
                line=br.readLine();
            }

            writer.close();
            in.close();
            br.close();
            socket.close();


        }catch(Exception ex){
            System.out.println("can not listen to:" + ex);
        }
    }

}
