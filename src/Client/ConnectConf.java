package Client;

import java.awt.*;
import javax.swing.border.*;

import Server.ChatServer;
import Server.PortConf;

import java.net.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * 生成连接信息输入的对话框 让用户输入连接服务器的IP和端口
 */
public class ConnectConf extends JDialog {
	JPanel panelUserConf=new JPanel();
	JButton save=new JButton();
	JButton cancel=new JButton();
    JLabel DLGINFO=new JLabel("默认连接设置为  127.0.0.1:8888");
    
	JPanel panelSave=new JPanel();
	JLabel message=new JLabel();
	
	String userInputIp;
	int userInputPort;
	
	JTextField inputIp;
	JTextField inputPort;

	public ConnectConf(ChatClient chatClient, String ip, int port) {
		// TODO Auto-generated constructor stub
		super(chatClient, true);
		this.userInputIp=ip;
		this.userInputPort=port;
		try {
			JbInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//设置运行位置，使对话框居中
		  Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		  this.setLocation((screenSize.width-400)/2+50,(screenSize.height-600)/2+150);
		  this.setResizable(false);
	}

	private void JbInit() throws Exception{
		// TODO Auto-generated method stub
		this.setSize(new Dimension(300, 130));
		this.setTitle("连接设置");
		message.setText("请输入服务器的IP地址:");
		inputIp=new JTextField(10);
		inputIp.setText(userInputIp);
		inputPort=new JTextField(4);
		inputPort.setText(""+userInputPort);
		save.setText("保存");
		cancel.setText("取消");
		
		panelUserConf.setLayout(new GridLayout(2, 2, 1, 1));
		panelUserConf.add(message);
		panelUserConf.add(inputIp);
		panelUserConf.add(new JLabel("请输入服务器的端口号:"));
		panelUserConf.add(inputPort);
		
		panelSave.add(new Label("           "));//设置按钮左右距离
		panelSave.add(save);
		panelSave.add(cancel);
		panelSave.add(new Label("           "));
		
		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(panelUserConf, BorderLayout.NORTH);
		contentPane.add(DLGINFO, BorderLayout.CENTER);
		contentPane.add(panelSave, BorderLayout.SOUTH);
	
		//保存按钮的事件处理
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				// TODO 自动生成的方法存根
				int savePort;
					//判断IP地址是否合法
				try {
					
					//为了使获取的地址变成String类型所以添加""+,注意中间不要有空格
					userInputIp=""+InetAddress.getByName(inputIp.getText());
					
					//应为使用自动类型转换后会自动添加/符号，使用substring(1)截取
					userInputIp=userInputIp.substring(1);
					
				}catch(UnknownHostException e){
						DLGINFO.setText("错误的IP地址！");	
						return;
				}
				
					//判断端口号是否合法
				try {
						savePort = Integer.parseInt(inputPort.getText());
					if (savePort<1||savePort>65535) {
						DLGINFO.setText("侦听端口必须是0～65535之间的整数！");
						inputPort.setText("");
						return;
					}
						userInputPort = savePort;
					dispose();
				} catch (NumberFormatException e) {
					DLGINFO.setText("错误的端口号,端口号必须为整数");
					inputPort.setText("");
					return;
				}
			
			}
		});
		
		//关闭对话框的操作
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DLGINFO.setText("默认连接设置为  127.0.0.1:8888");
			}
		});
		//取消按钮的事件处理
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				DLGINFO.setText("默认连接设置为  127.0.0.1:8888");
				dispose();
			}
		});
		}
}
