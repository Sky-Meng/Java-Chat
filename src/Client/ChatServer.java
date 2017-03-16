package Client;

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
import java.awt.event.WindowListener;
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
	JMenuItem portItem=new JMenuItem("�˿�����(p)");
	JMenuItem startItem=new JMenuItem("��������(S)");
	JMenuItem stopItem=new JMenuItem("ֹͣ����(T)");
	JMenuItem exitItem=new JMenuItem("�˳�(X)");
	
	JMenu helpMenu=new JMenu("����(H)");
	JMenuItem helpItem=new JMenuItem("����(H)");
	
	//����������
	JToolBar toolBar=new JToolBar();
	
	//�����������еİ�ť���
	JButton portSet;	//��������˶˿�
	JButton startServer; //�������������
	JButton stopSetver;	//�رշ��������
	JButton exitButton; //�˳���ť
	
	//��ܵĴ�С
	Dimension faceSiae=new Dimension(400, 600);
	
	ServerListen listenThread;
	
	JPanel downPanel;
	GridBagLayout gridBag;
	GridBagConstraints gridBagCon;
	
	/**
	 * ����˹��캯��
	 */
	public ChatServer() {
		init(); //��ʼ������
		
		//��ӿ�ܵĹر�ʱ�䴦��
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		//���ÿ�ܵĴ�С
		this.setSize(faceSiae);
		//��������ʱ���ڵ�λ��
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-faceSiae.getWidth())/2, (int)(screenSize.height-faceSiae.getHeight())/2);
		this.setResizable(false);
		
		this.setTitle("�����ҷ����");//���ñ���
		
		//����ͼ��
		icon=getIconImage();
		this.setIconImage(icon);
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
		stopSetver=new JButton("ֹͣ����");
		exitButton=new JButton("�˳�");
		//����ť��ӵ�������
		toolBar.add(portSet);
		toolBar.addSeparator();
		toolBar.add(startServer);
		toolBar.add(stopSetver);
		toolBar.addSeparator();
		toolBar.add(exitButton);
		contentPane.add(toolBar, BorderLayout.NORTH);
		//��ʼʱ����ֹͣ����ť������
		stopSetver.setEnabled(false);
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
		stopSetver.addActionListener(this);
		exitButton.addActionListener(this);
		
		combobox=new JComboBox();
		combobox.insertItemAt("������", 0); //�����Ŀ 0�����һ��
		combobox.setSelectedItem(0);
		
		messageShow =new JTextArea();
		messageShow.setEditable(false);
		//��ӹ�����
		messageScrollPane= new JScrollPane(messageShow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messageScrollPane.setPreferredSize(new Dimension(400, 400));
		messageScrollPane.revalidate();
		
		showStatus=new JTextField(35);
		showStatus.setEditable(false);
		
		sysMessage=new JTextField(24);
		sysMessage.setEditable(false);
		sysMessageButton=new JButton();
		sysMessageButton.setText("����");
		//���ϵͳ��Ϣ���¼�����
		sysMessage.addActionListener(this);
		sysMessageButton.addActionListener(this);
		
		sendToLabel=new JLabel("������");
		messageLabel=new JLabel("������Ϣ");
		downPanel=new JPanel();
		gridBag=new GridBagLayout();
		downPanel.setLayout(gridBag);
		
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=0;
		gridBagCon.gridy=0;
		gridBagCon.gridwidth=3;
		gridBagCon.gridheight=2;
		gridBagCon.ipadx=5;
		gridBagCon.ipady=5;
		JLabel none=new JLabel("  ");
		gridBag.setConstraints(none, gridBagCon);
		downPanel.add(none);
		//���ַ����� ����λ��
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=0;
		gridBagCon.gridy=2;
		gridBagCon.insets=new Insets(1, 0, 0, 0);
		gridBagCon.ipadx=5;
		gridBagCon.ipady=5;
		gridBag.setConstraints(sendToLabel, gridBagCon);
		downPanel.add(sendToLabel);
		//��Ӳ��ַ�����ѡ����
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=1;
		gridBagCon.gridy=2;
		gridBagCon.anchor=GridBagConstraints.LAST_LINE_START;
		gridBag.setConstraints(combobox, gridBagCon);
		downPanel.add(combobox);
		//��ӷ�����Ϣ ����λ��
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=0;
		gridBagCon.gridy=3;
		gridBag.setConstraints(messageLabel, gridBagCon);
		downPanel.add(messageLabel);
		//��Ӳ�����Ϣ���� �ı���
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=1;
		gridBagCon.gridy=3;
		gridBag.setConstraints(sysMessage, gridBagCon);
		downPanel.add(sysMessage);
		//��Ӳ�����Ϣ���Ͱ�ť
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=2;
		gridBagCon.gridy=3;
		gridBag.setConstraints(sysMessageButton, gridBagCon);
		downPanel.add(sysMessageButton);
		
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=0;
		gridBagCon.gridy=4;
		gridBagCon.gridwidth=3;
		gridBag.setConstraints(showStatus, gridBagCon);
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
		} else if (obj==stopSetver||obj==stopItem) {
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
			sendSystenMessage();
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
			
			stopSetver.setEnabled(true);
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
			
			int cuunt=userLinkList.getCount();
			int i=0;
			while (i<cuunt) {
				Node node=userLinkList.findUser(i);
				node.input.close();
				node.output.close();
				node.socket.close();
				i++;
			}
			stopSetver.setEnabled(false);
			stopItem.setEnabled(false);
			startServer.setEnabled(true);
			startItem.setEnabled(true);
			portSet.setEnabled(true);
			portItem.setEnabled(true);
			sysMessage.setEnabled(false);
			
			messageShow.append("������Ѿ��ر�\n");
			
			combobox.removeAllItems();
			combobox.addItem("������");
		} catch (IOException e) {
			// TODO �Զ����ɵĲ�׽��
			System.out.println(e);
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
				node.output.writeObject("");
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
	public void sendSystenMessage() {
		String toSomebody=combobox.getSelectedItem().toString();
		String message=sysMessage.getText()+"\n";
		
		messageShow.append(message);
		
		if (toSomebody.equals("������")) {
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
