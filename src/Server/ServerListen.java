package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerListen extends Thread{
	ServerSocket server;// 服务端Socket
	
	JComboBox comboBox; // 选择发送消息的接受者
	JTextArea textArea; //服务端的信息显示
	JTextField textField; // 显示用户连接状态
	UserLinkList userLinkList;//用户链表
	
	Node client;
	ServerReceive recvThread;
	
	public boolean isStop;
	
	/*
	 * 聊天服务器端的用户上下下线侦听
	 */
	public ServerListen(ServerSocket serverSocket, JComboBox combobox, JTextArea messageShow, JTextField showStatus,
			UserLinkList userLinkList) {
		// TODO 自动生成的方法存根
		this.server=server;
		this.comboBox=comboBox;
		this.textArea=textArea;
		this.textField=textField;
		this.userLinkList=userLinkList;
		
		isStop=false;
	}
	
	public void run() {
		while (!isStop && !server.isClosed()) {
			try {
				client=new Node();
				client.socket=server.accept();
				client.output=new ObjectOutputStream(client.socket.getOutputStream());
				client.output.flush();
				client.input=new ObjectInputStream(client.socket.getInputStream());
				client.username=(String) client.input.readObject();
				
				//显示提示信息
				comboBox.addItem(client.username);
				userLinkList.addUser(client);
				textArea.append("用户"+client.username+"上线"+"\n");
				textField.setText("在线用户"+userLinkList.getCount()+"人\n");
				recvThread=new ServerReceive(textArea,textField,comboBox,client,userLinkList);
				recvThread.start();
			} catch (IOException e) {
				// TODO 自动生成的捕捉块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的捕捉块
				e.printStackTrace();
			}
		}
	}
	

}
