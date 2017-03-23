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
 * ����ͻ�����Ϣ�շ���
 */
public class ClientReceive extends Thread {
	private JComboBox comboBox; // ѡ������Ϣ�Ľ�����
	private JTextArea textArea; // ����˵���Ϣ��ʾ

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
				if(type.equalsIgnoreCase("ϵͳ��Ϣ")){
					String sysmsg=(String) input.readObject();
					textArea.append("ϵͳ��Ϣ: "+sysmsg);
					
				}else if(type.equalsIgnoreCase("����ر�")){
					output.close();
					input.close();
					socket.close();
					textArea.append("�������ѹرգ�\n");
					break;
					
				}else if(type.equalsIgnoreCase("������Ϣ")){
					String message=(String) input.readObject();
					textArea.append(message);
					
				}else if(type.equalsIgnoreCase("�û��б�")){
					String userlist=(String) input.readObject();
					String usernames[]=userlist.split("\n");
					comboBox.removeAllItems();
					int i=0;
					comboBox.addItem("������");
					while (i<usernames.length) {
						comboBox.addItem(usernames[i]);
						i++;
					}
					comboBox.setSelectedItem(0);
					showStatus.setText("�����û� " + usernames.length + " ��");
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
