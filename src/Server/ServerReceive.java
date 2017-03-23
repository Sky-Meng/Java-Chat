package Server;

import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerReceive extends Thread {
	JComboBox comboBox; // 选择发送消息的接受者
	JTextArea textArea; // 服务端的信息显示
	JTextField textField; // 显示用户连接状态
	UserLinkList userLinkList;// 用户链表
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
		// 向所有人发送用户的列表
		sendUserList();
		while (!isStop && !client.socket.isClosed()) {
			try {
				String type = (String) client.input.readObject();
				if (type.equalsIgnoreCase("聊天信息")) {
					String toSomebody = (String)client.input.readObject();
					String status = (String) client.input.readObject();
					String action = (String) client.input.readObject();
					String message = (String) client.input.readObject();
					String msg = client.username + " " + action + "对" + toSomebody + "说:" + message + "\n";
					if (status.equalsIgnoreCase("悄悄话")) {
						msg = "[悄悄话]" + msg;
					}
					textArea.append(msg);
					if (toSomebody.equalsIgnoreCase("所有人")) {
						sendToall(msg);// 向所有人发送消息
					} else {
						try {
							client.output.writeObject("聊天信息");
							client.output.flush();
							client.output.writeObject(msg);
							client.output.flush();
						} catch (Exception e) {
							
						}
						Node node = userLinkList.findUser(toSomebody);

						if (node != null) {
							node.output.writeObject("聊天信息");
							node.output.flush();
							node.output.writeObject(msg);
							node.output.flush();
						}

					}
				} else if (type.equalsIgnoreCase("用户下线")) {
					Node node = userLinkList.findUser(client.username);
					userLinkList.delUser(node);

					String msg = "用户" + client.username + "下线\n";
					int count = userLinkList.getCount();

					comboBox.removeAllItems();
					comboBox.addItem("所有人");
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
					textField.setText("在线用户" + userLinkList.getCount() + "人/n");
					sendToall(msg); //向所有人发送消息
					sendUserList();//重新发送用户列表,刷新
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
	 * 向所有人发送消息
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
				node.output.writeObject("聊天信息");
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
	 * 向所有人发送用户的列表
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
				node.output.writeObject("用户列表");
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
