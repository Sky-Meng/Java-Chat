package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerListen extends Thread{
	ServerSocket server;// �����Socket
	
	JComboBox comboBox; // ѡ������Ϣ�Ľ�����
	JTextArea textArea; //����˵���Ϣ��ʾ
	JTextField textField; // ��ʾ�û�����״̬
	UserLinkList userLinkList;//�û�����
	
	Node client;
	ServerReceive recvThread;
	
	public boolean isStop;
	
	/*
	 * ����������˵��û�������������
	 */
	public ServerListen(ServerSocket serverSocket, JComboBox combobox, JTextArea messageShow, JTextField showStatus,
			UserLinkList userLinkList) {
		// TODO �Զ����ɵķ������
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
				
				//��ʾ��ʾ��Ϣ
				comboBox.addItem(client.username);
				userLinkList.addUser(client);
				textArea.append("�û�"+client.username+"����"+"\n");
				textField.setText("�����û�"+userLinkList.getCount()+"��\n");
				recvThread=new ServerReceive(textArea,textField,comboBox,client,userLinkList);
				recvThread.start();
			} catch (IOException e) {
				// TODO �Զ����ɵĲ�׽��
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵĲ�׽��
				e.printStackTrace();
			}
		}
	}
	

}
