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
	public static int Port = 8888; // 服务端的侦听端口
	ServerSocket serverSocket; // 服务端Socket
	Image icon; // 程序图标
	JComboBox combobox; // 选择发送消息的接受者
	JTextArea messageShow; //服务端的信息显示
	JScrollPane messageScrollPane; // 信息显示滚动条
	JTextField showStatus; // 显示用户连接状态
	JLabel sendToLabel, messageLabel;
	JTextField sysMessage; // 服务端消息的发送
	JButton sysMessageButton; // 服务端消息的发送按钮
	UserLinkList userLinkList; // 用户链表
	
	//建立菜单栏
	JMenuBar jMenuBar=new JMenuBar();
	//建立菜单组
	JMenu serviceMenu=new JMenu("服务(V)");
	//建立菜单项
	JMenuItem portItem = new JMenuItem ("端口设置(P)");
	JMenuItem startItem = new JMenuItem ("启动服务(S)");
	JMenuItem stopItem=new JMenuItem ("停止服务(T)");
	JMenuItem exitItem=new JMenuItem ("退出(X)");
	
	JMenu helpMenu=new JMenu("帮助(H)");
	JMenuItem helpItem=new JMenuItem("帮助(H)");
	
	//建立工具栏
	JToolBar toolBar=new JToolBar();
	
	//建立工具栏中的按钮组件
	JButton portSet;	//启动服务端端口
	JButton startServer; //启动服务端侦听
	JButton stopServer;	//关闭服务端侦听
	JButton exitButton; //退出按钮
	
	//框架的大小
	Dimension faceSize = new Dimension(400, 600);
	
	ServerListen listenThread;
	
	JPanel downPanel;
	GridBagLayout girdBag;
	GridBagConstraints girdBagCon;
	
	/**
	 * 服务端构造函数
	 */
	public ChatServer() {
		init(); //初始化程序
		
		//添加框架的关闭时间处理
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		//设置框架的大小
		this.setSize(faceSize);
		//设置运行时窗口的位置
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-faceSize.getWidth())/2, (int)(screenSize.height-faceSize.getHeight())/2);
		this.setResizable(false);
		
		this.setTitle("聊天室服务端");//设置标题
		
		//程序图标
		icon = getImage("");
		this.setIconImage(icon); //设置程序图标
		show();
		
		//为服务菜单设置热键V
		serviceMenu.setMnemonic('V');
		
		//为端口设置热键和快捷键P
		portItem.setMnemonic('P');
		portItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
		
		//为启动服务设置热键和快捷键S
		startItem.setMnemonic('S');
		startItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		
		//为端口设置热键和快捷键T
		stopItem.setMnemonic('T');
		stopItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK));
		
		//为退出设置热键和快捷键X
		exitItem.setMnemonic('X');
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
		
		//为帮助菜单设置热键H
		helpMenu.setMnemonic('H');
		
		//为帮助设置热键和快捷键H
		helpItem.setMnemonic('H');
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,InputEvent.CTRL_MASK));
		
	}
	
	/**
	 * 程序初始化函数
	 */
	public void init() {
		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//添加菜单栏
		serviceMenu.add(portItem);
		serviceMenu.add(startItem);
		serviceMenu.add(stopItem);
		serviceMenu.add(exitItem);
		jMenuBar.add(serviceMenu);
		helpMenu.add(helpItem);
		jMenuBar.add(helpMenu);
		setJMenuBar(jMenuBar);
		//初始化按钮
		portSet=new JButton("端口设置");
		startServer=new JButton("启动服务");
		stopServer=new JButton("停止服务");
		exitButton=new JButton("退出");
		//将按钮添加到工具栏
		toolBar.add(portSet);
		toolBar.addSeparator();
		toolBar.add(startServer);
		toolBar.add(stopServer);
		toolBar.addSeparator();
		toolBar.add(exitButton);
		contentPane.add(toolBar, BorderLayout.NORTH);
		//初始时，令停止服务按钮不可用
		stopServer.setEnabled(false);
		stopItem.setEnabled(false);
		//为菜单栏添加时间监听
		portItem.addActionListener(this);
		startItem.addActionListener(this);
		stopItem.addActionListener(this);
		exitItem.addActionListener(this);
		helpItem.addActionListener(this);
		//添加按钮的事件侦听
		portSet.addActionListener(this);
		startServer.addActionListener(this);
		stopServer.addActionListener(this);
		exitButton.addActionListener(this);
		
		combobox=new JComboBox();
		combobox.insertItemAt("所有人", 0); //添加条目 0代表第一个
		combobox.setSelectedIndex(0);
		
		messageShow =new JTextArea();
		messageShow.setEditable(false);
		//添加滚动条
		messageScrollPane= new JScrollPane(messageShow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messageScrollPane.setPreferredSize(new Dimension(400, 400));
		messageScrollPane.revalidate();
		
		showStatus=new JTextField(35);
		showStatus.setEditable(false);
		
		sysMessage=new JTextField(24);
		sysMessage.setEnabled(false);
	//	sysMessage.setEditable(false);
		sysMessageButton=new JButton();
		sysMessageButton.setText("发送");
		//添加系统消息的事件侦听
		sysMessage.addActionListener(this);
		sysMessageButton.addActionListener(this);
		
		sendToLabel=new JLabel("发送至");
		messageLabel=new JLabel("发送消息");
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
		//布局发送至 文字位置
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=0;
		girdBagCon.gridy=2;
		girdBagCon.insets=new Insets(1, 0, 0, 0);
		girdBagCon.ipadx=5;
		girdBagCon.ipady=5;
		girdBag.setConstraints(sendToLabel, girdBagCon);
		downPanel.add(sendToLabel);
		//添加布局发送至选项栏
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=1;
		girdBagCon.gridy=2;
		girdBagCon.anchor=GridBagConstraints.LINE_START;
		girdBag.setConstraints(combobox, girdBagCon);
		downPanel.add(combobox);
		//添加发送消息 文字位置
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=0;
		girdBagCon.gridy=3;
		girdBag.setConstraints(messageLabel, girdBagCon);
		downPanel.add(messageLabel);
		//添加布局消息发送 文本框
		girdBagCon=new GridBagConstraints();
		girdBagCon.gridx=1;
		girdBagCon.gridy=3;
		girdBag.setConstraints(sysMessage, girdBagCon);
		downPanel.add(sysMessage);
		//添加布局消息发送按钮
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
		//关闭程序的操作
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stopService();
				System.exit(0);
			}
		}
		);
	}
	
	
	/**
	 * 事件处理
	 */
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if (obj==startServer||obj==startItem) {
			startService();
		} else if (obj==stopServer||obj==stopItem) {
			int j=JOptionPane.showConfirmDialog(this, "真的停止服务吗", "停止服务", JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
			if (j==JOptionPane.YES_OPTION) {
				stopService();
			}
		}else if (obj==portSet||obj==portItem) {
			//调出端口对话框
			PortConf portConf=new PortConf(this);
			portConf.show();
		}else if (obj==exitButton||obj==exitItem) {
			int j=JOptionPane.showConfirmDialog(this, "真的要退出吗", "退出", JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
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
	 * 启动服务端
	 */
	public void startService() {
		try {
			serverSocket=new ServerSocket(Port, 10);
			messageShow.append("服务端已经启动，在"+Port+"端口监听…\n");
			
			startServer.setEnabled(false);
			startItem.setEnabled(false);
			portSet.setEnabled(false);
			portItem.setEnabled(false);
			
			stopServer.setEnabled(true);
			stopItem.setEnabled(true);
			sysMessage.setEnabled(true);;
		} catch (IOException e) {
			// TODO 自动生成的捕捉块
			e.printStackTrace();
		}
		userLinkList=new UserLinkList();
		listenThread=new ServerListen(serverSocket,combobox,messageShow,showStatus,userLinkList);
		listenThread.start();
	}
	
	/**
	 * 关闭服务端
	 */
	public void stopService() {
		//向所有人发送服务器关闭的消息
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
			
			messageShow.append("服务端已经关闭\n");
			
			combobox.removeAllItems();
			combobox.addItem("所有人");
		} catch (NullPointerException e) {
			// TODO 自动生成的捕捉块
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 向所有人发送服务器关闭消息
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
				node.output.writeObject("服务关闭");
				node.output.flush();
				
			} catch (IOException e) {
				// TODO 自动生成的捕捉块
				e.printStackTrace();
			}
				i++;
		}
	}
	
	/**
	 * 向所有人发送消息
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
				node.output.writeObject("系统信息");
				node.output.flush();
				node.output.writeObject(msg);
				node.output.flush();
				
			} catch (IOException e) {
				// TODO 自动生成的捕捉块
				e.printStackTrace();
			}
				i++;
		}
			sysMessage.setText("");
	}
	
	/**
	 * 向客户端用户发送消息
	 */
	public void sendSystemMessage(){
		String toSomebody = combobox.getSelectedItem().toString();
		String message = sysMessage.getText() + "\n";
		
		messageShow.append(message);
		
		if(toSomebody.equalsIgnoreCase("所有人")){
	
			sendMsgToAll(message);
		} else {
			Node node=userLinkList.findUser(toSomebody);
			try {
				node.output.writeObject("系统信息");
				node.output.flush();
				node.output.writeObject(message);
				node.output.flush();
				
			} catch (IOException e) {
				// TODO 自动生成的捕捉块
				e.printStackTrace();
			}	
			sysMessage.setText("");
		}
	}
	
	/**
	 * 通过给定的文件名获得图像
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
			// TODO 自动生成的捕捉块
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
