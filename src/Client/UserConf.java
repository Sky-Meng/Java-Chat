package Client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Server.ChatServer;
import Server.PortConf;

/**
 * 生成用户信息输入对话框的类 让用户输入自己的用户名
 */
public class UserConf extends JDialog {
	JPanel panelUserConf=new JPanel();
	JButton save=new JButton();
	JButton cancel=new JButton();
    JLabel DLGINFO=new JLabel("默认用户名为:工藤新一");
    
	JPanel panelSave=new JPanel();
	JLabel message=new JLabel();
	String userInputName;
	
	JTextField userName;

	public UserConf(ChatClient chatClient, String userName) {
		// TODO Auto-generated constructor stub
		super(chatClient, userName);
		this.userInputName=userName;
		try {
			jbInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//设置运行位置，使对话框居中
		  Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		  this.setLocation((screenSize.width-400)/2+50,(screenSize.height-600)/2+150);
		  this.setResizable(false);

	}

	private void jbInit() throws Exception{
		// TODO Auto-generated method stub
		this.setSize(new Dimension(300, 120));
		this.setTitle("用户设置");
		message.setText("请输入用户名:");
		userName=new JTextField(10);
		userName.setText(userInputName);
		save.setText("保存");
		cancel.setText("取消");
		
		panelUserConf.setLayout(new FlowLayout());
		panelUserConf.add(message);
		panelUserConf.add(userName);
		
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
				if (userName.getText().equals("")) {
					DLGINFO.setText("用户名不能为空!");
					userName.setText(userInputName);
					return;
				} else if (userName.getText().length()>15) {
					DLGINFO.setText("用户名长度不能大于15个字符!");
					userName.setText(userInputName);
					return;
				}
				userInputName=userName.getText();
				dispose();
			}
		});
		
		//关闭对话框的操作
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DLGINFO.setText("默认用户名为:工藤新一");
			}
		});
		//取消按钮的事件处理
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				DLGINFO.setText("默认用户名为:工藤新一");
				dispose();
			}
		});
	}

}
