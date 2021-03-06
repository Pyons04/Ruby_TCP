import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.ButtonGroup;
import java.lang.*;
import java.io.*;
import java.net.*;

public class Javatcp2 implements ActionListener{

        JLabel lab;
        JTextField t;
        //static int p=10;
        static String p="No_data";
   public Javatcp2(){
     
        JFrame frame=new JFrame("受信先行"); //上部のタイトル
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//閉じるボタンを押すとプログラムを自動で終了する。
        frame.setSize(400,400);
        Container contentPane=frame.getContentPane();//必要ない
        JPanel panel=new JPanel();//新しいパネルオブジェクト
        panel.setLayout(new FlowLayout());//Layoutの設定（部品の乗せ方の設定、上下左右）
        panel.setBackground(Color.RED);
        contentPane.add(panel);//Paneにpanelを追加
        

        t = new JTextField("10",15);
        t.addActionListener(this);//TextFiledにアクションリスナー
        lab=new JLabel("入力されている文字:");
        panel.setLayout(new FlowLayout());
        
        panel.add(t);
        panel.add(lab);

        frame.setVisible(true);//windowを見せるor見せない。一番最後が良い（必須）
    }

        public void actionPerformed(ActionEvent event){
            String x=t.getText();
            //int x=Integer.parseInt(t.getText());//文字でも数字に変換してくれるInterger.parsint
            lab.setText("入力されている文字:"+x);
            p=x;
            t.setText("");
}


    public static void main (String [] args){
    Javatcp2 s1=new Javatcp2();
    MultiThread2 mt = new MultiThread2();
    Thread thread = new Thread(mt);
    thread.start();
    }

}


class MultiThread2 implements Runnable {
    public void run() {
             try{

    InputStreamReader i =
          new InputStreamReader(System.in);
      
System.out.print("受信先行側の設定を行います。");
System.out.print("hostを設定します:");
       BufferedReader b=
          new BufferedReader(i);
       String host = b.readLine();
       System.out.println(host);

System.out.print("portを設定します:");

       BufferedReader a=
          new BufferedReader(i);
       String port = a.readLine();
       int port_int = Integer.parseInt(port);
       System.out.println(port);
    

while(true){
     try{

        //受信を担う処理。
    Socket mysocket = new Socket(host,port_int);//相手のIPアドレス,書かなくてもよい（クライアント側のみ）
        BufferedReader in = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));//inはサーバーから受信するためのメソッド

System.out.print("受信したメッセージ:");
    while(!in.ready()){}
    System.out.println(in.readLine());
    System.out.print("\n");
    in.close();

    try{
  Thread.sleep(3000);
}catch (InterruptedException e){
}
//送信を担う処理
        ServerSocket srvr=new ServerSocket(port_int);
        Socket skt=srvr.accept();//接続するまでここで止まる
        System.out.print("送信するメッセージを入力してください。");
        PrintWriter out=new PrintWriter(skt.getOutputStream(),true);//メッセージを送信
        System.out.print("送信されたメッセージは"+Javatcp2.p+"です\n");
        out.print(Javatcp2.p);
        out.close();
        skt.close();
        srvr.close();


           if(Javatcp2.p!="No_data"){
                   Javatcp2.p="No_data";
           }
   
     }


catch(Exception e){
    System.out.print("待機中\n");
    }
}




}catch(IOException a){
      System.out.println("例外処理、入力エラー");
     }
    }
}