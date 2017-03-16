package Client;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class help extends JDialog {
	JPanel titlePanel=new JPanel();
	JPanel contentPanel=new JPanel();
	JPanel closePanel=new JPanel();
	
	JButton close=new JButton();
	JLabel title=new JLabel("聊天室服务端帮助");
	JTextArea help=new JTextArea();
	Color bg=new Color(255, 255, 255);
	
	public help( JFrame frame) {
		// TODO 自动生成的方法存根
		super(frame, true);
		try {
			jbInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//设置运行位置，使对话框居中
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-400)/2,(screenSize.height-320)/2);
		this.setResizable(false);//设置对话框大小不可改变

	}

	private void jbInit() throws Exception{
		// TODO 自动生成的方法存根
		this.setSize(new Dimension(420, 200));
		this.setTitle("帮助");
		
		//设置背景颜色和提示框一致
		titlePanel.setBackground(bg);
		contentPanel.setBackground(bg);
		closePanel.setBackground(bg);
		
		help.setText("1、设置服务端的侦听端口(默认端口为:8888)。\n"+
					 "2、单击“启动服务”按钮便可在指定的端口启动服务。\n"+
					 "3、选择需要接收消息的用户，在消息栏中写入消息，之后便可以发送消息。\n"+
					 "4、信息状态栏中吸纳是服务器当前的启动与停止状态、"+
					 "用户发送的消息和\n      服务器端发送的系统消息.");
		help.setEditable(false);//设置对话框为不可编辑
		
		titlePanel.add(new Label("          "));//设置标题左右距离
		titlePanel.add(title);
		titlePanel.add(new Label("          "));
		
		contentPanel.add(help);
		contentPanel.add(new Label("          "));//设置按钮左右距离
		contentPanel.add(close);
		contentPanel.add(new Label("          "));
		
		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(titlePanel, BorderLayout.NORTH);
		contentPane.add(contentPanel, BorderLayout.CENTER);
		contentPane.add(closePanel, BorderLayout.SOUTH);
		
		close.setText("关闭");
		//事件处理
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				dispose();
			}
		});
	}

}
