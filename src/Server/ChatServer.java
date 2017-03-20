package Server;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class ChatServer extends JFrame implements ActionListener {
	private static final String WindowEvent = null;
	public static int Port = 8888; // ����˵������˿�
	ServerSocket serverSocket; // �����Socket
	Image icon; // ����ͼ��
	JComboBox combobox; // ѡ������Ϣ�Ľ�����
	JTextArea messageShow; //����˵���Ϣ��ʾ
	JScrollPane messageScrollPane; // ��Ϣ��ʾ������
	JTextField showStatus; // ��ʾ�û�����״̬
	JLabel sendToLabel, messageLabel;
	JTextField sysMessage; // �������Ϣ�ķ���
	JButton sysMessageButton; // �������Ϣ�ķ��Ͱ�ť
	UserLinkList userLinkList; // �û�����
	
	//�����˵���
	JMenuBar jMenuBar=new JMenuBar();
	//�����˵���
	JMenu serviceMenu=new JMenu("����(V)");
	//�����˵���
	JMenuItem portItem = new JMenuItem ("�˿�����(P)");
	JMenuItem startItem = new JMenuItem ("��������(S)");
	JMenuItem stopItem=new JMenuItem ("ֹͣ����(T)");
	JMenuItem exitItem=new JMenuItem ("�˳�(X)");
	
	JMenu helpMenu=new JMenu("����(H)");
	JMenuItem helpItem=new JMenuItem("����(H)");
	
	//����������
	JToolBar toolBar=new JToolBar();
	
	//�����������еİ�ť���
	JButton portSet;	//��������˶˿�
	JButton startServer; //�������������
	JButton stopServer;	//�رշ��������
	JButton exitButton; //�˳���ť
	
	//��ܵĴ�С
	Dimension faceSize = new Dimension(400, 600);
	
	ServerListen listenThread;
	
	JPanel downPanel;
	GridBagLayout girdBag;
	GridBagConstraints girdBagCon;
	
	/**
	 * ����˹��캯��
	 */
	public ChatServer() {
		init(); //��ʼ������
		
		//��ӿ�ܵĹر�ʱ�䴦��
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		//���ÿ�ܵĴ�С
		this.setSize(faceSize);
		//��������ʱ���ڵ�λ��
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-faceSize.getWidth())/2, (int)(screenSize.height-faceSize.getHeight())/2);
		this.setResizable(false);
		
		this.setTitle("�����ҷ����");//���ñ���
		
		//����ͼ��
		icon = getImage("");
		this.setIconImage(icon); //���ó���ͼ��
		show();
		
		//Ϊ����˵������ȼ�V
		serviceMenu.setMnemonic('V');
		
		//Ϊ�˿������ȼ��Ϳ�ݼ�P
		portItem.setMnemonic('P');
		portItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
		
		//Ϊ�������������ȼ��Ϳ�ݼ�S
		startItem.setMnemonic('S');
		startItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		
		//Ϊ�˿������ȼ��Ϳ�ݼ�T
		stopItem.setMnemonic('T');
		stopItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK));
		
		//Ϊ�˳������ȼ��Ϳ�ݼ�X
		exitItem.setMnemonic('X');
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
		
		//Ϊ�����˵������ȼ�H
		helpMenu.setMnemonic('H');
		
		//Ϊ���������ȼ��Ϳ�ݼ�H
		helpItem.setMnemonic('H');
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,InputEvent.CTRL_MASK));
		
	}
	
	/**
	 * �����ʼ������
	 */
	public void init() {
		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//��Ӳ˵���
		serviceMenu.add(portItem);
		serviceMenu.add(startItem);
		serviceMenu.add(stopItem);
		serviceMenu.add(exitItem);
		jMenuBar.add(serviceMenu);
		helpMenu.add(helpItem);
		jMenuBar.add(helpMenu);
		setJMenuBar(jMenuBar);
		//��ʼ����ť
		portSet=new JButton("�˿�����");
		startServer=new JButton("��������");
		stopServer=new JButton("ֹͣ����");
		exitButton=new JButton("�˳�");
		//����ť��ӵ�������
		toolBar.add(portSet);
		toolBar.addSeparator();
		toolBar.add(startServer);
		toolBar.add(stopServer);
		toolBar.addSeparator();
		toolBar.add(exitButton);
		contentPane.add(toolBar, BorderLayout.NORTH);
		//��ʼʱ����ֹͣ����ť������
		stopServer.setEnabled(false);
		stopItem.setEnabled(false);
		//Ϊ�˵������ʱ�����
		portItem.addActionListener(this);
		startItem.addActionListener(this);
		stopItem.addActionListener(this);
		exitItem.addActionListener(this);
		helpItem.addActionListener(this);
		//��Ӱ�ť���¼�����
		portSet.addActionListener(this);
		startServer.addActionListener(this);
		stopServer.addActionListener(this);
		exitButton.addActionListener(this);
		
		combobox=new JComboBox();
		combobox.insertItemAt("������", 0); //�����Ŀ 0�����һ��
		combobox.setSelectedIndex(0);
		
		messageShow =new JTextArea();
		messageShow.setEditable(false);
		//��ӹ�����
		messageScrollPane= new JScrollPane(messageShow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messageScrollPane.setPreferredSize(new Dimension(400, 400));
		messageScrollPane.revalidate();
		
		showStatus=new JTextField(35);
		showStatus.setEditable(false);
		
		sysMessage=new JTextField(24);
		sysMessage.setEnabled(false);
	//	sysMessage.setEditable(false);
		sysMessageButton=new JButton();
		sysMessageButton.setText("����");
		//���ϵͳ��Ϣ���¼�����
		sysMessage.addActionListener(this);
		sysMessageButton.addActionListener(this);
		
		sendToLabel=new JLabel("������");
		messageLabel=new JLabel("������Ϣ");
		downPanel=new JPanel();
		girdBag=new GridBagLayout();
		downPanel.setLayout(girdBag);
		
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=0;
		girdBagCon.gridy=0;
		girdBagCon.gridwidth=3;
		girdBagCon.gridheight=2;
		girdBagCon.ipadx=5;
		girdBagCon.ipady=5;
		JLabel none=new JLabel("  ");
		girdBag.setConstraints(none, girdBagCon);
		downPanel.add(none);
		//���ַ����� ����λ��
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=0;
		girdBagCon.gridy=2;
		girdBagCon.insets=new Insets(1, 0, 0, 0);
		girdBagCon.ipadx=5;
		girdBagCon.ipady=5;
		girdBag.setConstraints(sendToLabel, girdBagCon);
		downPanel.add(sendToLabel);
		//��Ӳ��ַ�����ѡ����
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=1;
		girdBagCon.gridy=2;
		girdBagCon.anchor=GridBagConstraints.LINE_START;
		girdBag.setConstraints(combobox, girdBagCon);
		downPanel.add(combobox);
		//��ӷ�����Ϣ ����λ��
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=0;
		girdBagCon.gridy=3;
		girdBag.setConstraints(messageLabel, girdBagCon);
		downPanel.add(messageLabel);
		//��Ӳ�����Ϣ���� �ı���
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=1;
		girdBagCon.gridy=3;
		girdBag.setConstraints(sysMessage, girdBagCon);
		downPanel.add(sysMessage);
		//��Ӳ�����Ϣ���Ͱ�ť
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=2;
		girdBagCon.gridy=3;
		girdBag.setConstraints(sysMessageButton, girdBagCon);
		downPanel.add(sysMessageButton);
		
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=0;
		girdBagCon.gridy=4;
		girdBagCon.gridwidth=3;
		girdBag.setConstraints(showStatus, girdBagCon);
		downPanel.add(showStatus);
		
		contentPane.add(messageScrollPane, BorderLayout.CENTER);
		contentPane.add(downPanel, BorderLayout.SOUTH);
		//�رճ���Ĳ���
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stopService();
				System.exit(0);
			}
		}
		);
	}
	
	
	/**
	 * �¼�����
	 */
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if (obj==startServer||obj==startItem) {
			startService();
		} else if (obj==stopServer||obj==stopItem) {
			int j=JOptionPane.showConfirmDialog(this, "���ֹͣ������", "ֹͣ����", JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
			if (j==JOptionPane.YES_OPTION) {
				stopService();
			}
		}else if (obj==portSet||obj==portItem) {
			//�����˿ڶԻ���
			PortConf portConf=new PortConf(this);
			portConf.show();
		}else if (obj==exitButton||obj==exitItem) {
			int j=JOptionPane.showConfirmDialog(this, "���Ҫ�˳���", "�˳�", JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
			if (j==JOptionPane.YES_OPTION) {
				stopService();
				System.exit(0);
			}
		}else if (obj==helpItem) {
			help heloDialog=new help(this);
			heloDialog.show();
		}else if (obj==sysMessage||obj==sysMessageButton) {
			sendSystemMessage();
		}
	}
	
	/**
	 * ���������
	 */
	public void startService() {
		try {
			serverSocket=new ServerSocket(Port, 10);
			messageShow.append("������Ѿ���������"+Port+"�˿ڼ�����\n");
			
			startServer.setEnabled(false);
			startItem.setEnabled(false);
			portSet.setEnabled(false);
			portItem.setEnabled(false);
			
			stopServer.setEnabled(true);
			stopItem.setEnabled(true);
			sysMessage.setEnabled(true);;
		} catch (IOException e) {
			// TODO �Զ����ɵĲ�׽��
			e.printStackTrace();
		}
		userLinkList=new UserLinkList();
		listenThread=new ServerListen(serverSocket,combobox,messageShow,showStatus,userLinkList);
		listenThread.start();
	}
	
	/**
	 * �رշ����
	 */
	public void stopService() {
		//�������˷��ͷ������رյ���Ϣ
		try {
			sendStopToAll();
			listenThread.isStop=true;
			serverSocket.close();
			
			int count = userLinkList.getCount();
			
			int i =0;
			while( i < count){
				Node node = userLinkList.findUser(i);
				
				node.input .close();
				node.output.close();
				node.socket.close();
				i++;
			}
			stopServer.setEnabled(false);
			stopItem.setEnabled(false);
			startServer.setEnabled(true);
			startItem.setEnabled(true);
			portSet.setEnabled(true);
			portItem.setEnabled(true);
			sysMessage.setEnabled(false);
			
			messageShow.append("������Ѿ��ر�\n");
			
			combobox.removeAllItems();
			combobox.addItem("������");
		} catch (NullPointerException e) {
			// TODO �Զ����ɵĲ�׽��
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * �������˷��ͷ������ر���Ϣ
	 */
	public void sendStopToAll() {
		int count=userLinkList.getCount();
		int i=0;
		while (i<count) {
			Node node=userLinkList.findUser(i);
			if (node==null) {
				i++;
				continue;
			}
			try {
				node.output.writeObject("����ر�");
				node.output.flush();
				
			} catch (IOException e) {
				// TODO �Զ����ɵĲ�׽��
				e.printStackTrace();
			}
				i++;
		}
	}
	
	/**
	 * �������˷�����Ϣ
	 */
	public void sendMsgToAll(String msg) {
		int count=userLinkList.getCount();
		int i=0;
		while (i<count) {
			Node node=userLinkList.findUser(i);
			if (node==null) {
				i++;
				continue;
			}
			try {
				node.output.writeObject("ϵͳ��Ϣ");
				node.output.flush();
				node.output.writeObject(msg);
				node.output.flush();
				
			} catch (IOException e) {
				// TODO �Զ����ɵĲ�׽��
				e.printStackTrace();
			}
				i++;
		}
			sysMessage.setText("");
	}
	
	/**
	 * ��ͻ����û�������Ϣ
	 */
	public void sendSystemMessage(){
		String toSomebody = combobox.getSelectedItem().toString();
		String message = sysMessage.getText() + "\n";
		
		messageShow.append(message);
		
		if(toSomebody.equalsIgnoreCase("������")){
	
			sendMsgToAll(message);
		} else {
			Node node=userLinkList.findUser(toSomebody);
			try {
				node.output.writeObject("ϵͳ��Ϣ");
				node.output.flush();
				node.output.writeObject(message);
				node.output.flush();
				
			} catch (IOException e) {
				// TODO �Զ����ɵĲ�׽��
				e.printStackTrace();
			}	
			sysMessage.setText("");
		}
	}
	
	/**
	 * ͨ���������ļ������ͼ��
	 */
	Image getImage(String filename){
		URLClassLoader urlLoader=(URLClassLoader) this.getClass().getClassLoader();
		URL url=null;
		Image image=null;
		url=urlLoader.findResource(filename);
		image=Toolkit.getDefaultToolkit().getImage(url);
		MediaTracker mediaTracker=new MediaTracker(this);
		try {
			mediaTracker.addImage(image, 0);
			mediaTracker.waitForID(0);
		} catch (InterruptedException e) {
			// TODO �Զ����ɵĲ�׽��
			image=null;
		}
		if (mediaTracker.isErrorID(0)) {
			image=null;
		}
		return image;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatServer app=new ChatServer();
	}
}
