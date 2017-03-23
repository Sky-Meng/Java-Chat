package Server;

import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerReceive extends Thread {
	JComboBox comboBox; // ѡ������Ϣ�Ľ�����
	JTextArea textArea; // ����˵���Ϣ��ʾ
	JTextField textField; // ��ʾ�û�����״̬
	UserLinkList userLinkList;// �û�����
	Node client;

	public boolean isStop;

	public ServerReceive(JTextArea textArea, JTextField textField, JComboBox comboBox, Node client,
			UserLinkList userLinkList) {
		// TODO Auto-generated constructor stub
		this.comboBox = comboBox;
		this.textArea = textArea;
		this.textField = textField;
		this.client = client;
		this.userLinkList = userLinkList;

		isStop = false;
	}

	public void run() {
		// �������˷����û����б�
		sendUserList();
		while (!isStop && !client.socket.isClosed()) {
			try {
				String type = (String) client.input.readObject();
				if (type.equalsIgnoreCase("������Ϣ")) {
					String toSomebody = (String)client.input.readObject();
					String status = (String) client.input.readObject();
					String action = (String) client.input.readObject();
					String message = (String) client.input.readObject();
					String msg = client.username + " " + action + "��" + toSomebody + "˵:" + message + "\n";
					if (status.equalsIgnoreCase("���Ļ�")) {
						msg = "[���Ļ�]" + msg;
					}
					textArea.append(msg);
					if (toSomebody.equalsIgnoreCase("������")) {
						sendToall(msg);// �������˷�����Ϣ
					} else {
						try {
							client.output.writeObject("������Ϣ");
							client.output.flush();
							client.output.writeObject(msg);
							client.output.flush();
						} catch (Exception e) {
							
						}
						Node node = userLinkList.findUser(toSomebody);

						if (node != null) {
							node.output.writeObject("������Ϣ");
							node.output.flush();
							node.output.writeObject(msg);
							node.output.flush();
						}

					}
				} else if (type.equalsIgnoreCase("�û�����")) {
					Node node = userLinkList.findUser(client.username);
					userLinkList.delUser(node);

					String msg = "�û�" + client.username + "����\n";
					int count = userLinkList.getCount();

					comboBox.removeAllItems();
					comboBox.addItem("������");
					int i = 0;
					while (i < count) {
						node = userLinkList.findUser(i);
						if (node == null) {
							i++;
							continue;
						}
						comboBox.addItem(node.username);
						i++;
					}
					comboBox.setSelectedIndex(0);
					textArea.append(msg);
					textField.setText("�����û�" + userLinkList.getCount() + "��/n");
					sendToall(msg); //�������˷�����Ϣ
					sendUserList();//���·����û��б�,ˢ��
					break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * �������˷�����Ϣ
	 */
	public void sendToall(String msg) {
		// TODO Auto-generated method stub
		int count = userLinkList.getCount();
		int i = 0;
		while (i < count) {
			Node node = userLinkList.findUser(i);
			if (node == null) {
				i++;
				continue;
			}
			try {
				node.output.writeObject("������Ϣ");
				node.output.flush();
				node.output.writeObject(msg);
				node.output.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
	}

	/*
	 * �������˷����û����б�
	 */
	public void sendUserList() {
		// TODO Auto-generated method stub
		String userlist = "";
		int count = userLinkList.getCount();
		int i = 0;
		while (i < count) {
			Node node = userLinkList.findUser(i);
			if (node == null) {
				i++;
				continue;
			}
			userlist += node.username;
			userlist += '\n';
			i++;
		}
		i = 0;
		while (i < count) {
			Node node = userLinkList.findUser(i);
			if (node == null) {
				i++;
				continue;
			}
			try {
				node.output.writeObject("�û��б�");
				node.output.flush();
				node.output.writeObject(userlist);
				node.output.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
	}

}
