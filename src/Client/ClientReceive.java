package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Server.Node;
import Server.UserLinkList;

/*
 * 聊天客户端消息收发类
 */
public class ClientReceive extends Thread {
	private JComboBox comboBox; // 选择发送消息的接受者
	private JTextArea textArea; // 服务端的信息显示

	Socket socket;
	ObjectOutputStream output;
	ObjectInputStream  input;
	JTextField showStatus;
	
	public ClientReceive(Socket socket, ObjectOutputStream output, ObjectInputStream input, JComboBox comboBox,
			JTextArea textArea, JTextField showStatus) {
		// TODO Auto-generated constructor stub
		this.socket=socket;
		this.output=output;
		this.input=input;
		this.comboBox=comboBox;
		this.textArea=textArea;
		this.showStatus=showStatus;
	}

	public void run() {
		// TODO Auto-generated method stub
		while (!socket.isClosed()) {
			try {
				String type=(String) input.readObject();
				if(type.equalsIgnoreCase("系统信息")){
					String sysmsg=(String) input.readObject();
					textArea.append("系统信息: "+sysmsg);
					
				}else if(type.equalsIgnoreCase("服务关闭")){
					output.close();
					input.close();
					socket.close();
					textArea.append("服务器已关闭！\n");
					break;
					
				}else if(type.equalsIgnoreCase("聊天信息")){
					String message=(String) input.readObject();
					textArea.append(message);
					
				}else if(type.equalsIgnoreCase("用户列表")){
					String userlist=(String) input.readObject();
					String usernames[]=userlist.split("\n");
					comboBox.removeAllItems();
					int i=0;
					comboBox.addItem("所有人");
					while (i<usernames.length) {
						comboBox.addItem(usernames[i]);
						i++;
					}
					comboBox.setSelectedItem(0);
					showStatus.setText("在线用户 " + usernames.length + " 人");
				} 
			} catch (ClassNotFoundException e) {		
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
	}


	

	
}
