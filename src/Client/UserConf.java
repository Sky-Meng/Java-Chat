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
 * �����û���Ϣ����Ի������ ���û������Լ����û���
 */
public class UserConf extends JDialog {
	JPanel panelUserConf=new JPanel();
	JButton save=new JButton();
	JButton cancel=new JButton();
    JLabel DLGINFO=new JLabel("Ĭ���û���Ϊ:������һ");
    
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
		//��������λ�ã�ʹ�Ի������
		  Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		  this.setLocation((screenSize.width-400)/2+50,(screenSize.height-600)/2+150);
		  this.setResizable(false);

	}

	private void jbInit() throws Exception{
		// TODO Auto-generated method stub
		this.setSize(new Dimension(300, 120));
		this.setTitle("�û�����");
		message.setText("�������û���:");
		userName=new JTextField(10);
		userName.setText(userInputName);
		save.setText("����");
		cancel.setText("ȡ��");
		
		panelUserConf.setLayout(new FlowLayout());
		panelUserConf.add(message);
		panelUserConf.add(userName);
		
		panelSave.add(new Label("           "));//���ð�ť���Ҿ���
		panelSave.add(save);
		panelSave.add(cancel);
		panelSave.add(new Label("           "));
		
		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(panelUserConf, BorderLayout.NORTH);
		contentPane.add(DLGINFO, BorderLayout.CENTER);
		contentPane.add(panelSave, BorderLayout.SOUTH);
		
		//���水ť���¼�����
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				if (userName.getText().equals("")) {
					DLGINFO.setText("�û�������Ϊ��!");
					userName.setText(userInputName);
					return;
				} else if (userName.getText().length()>15) {
					DLGINFO.setText("�û������Ȳ��ܴ���15���ַ�!");
					userName.setText(userInputName);
					return;
				}
				userInputName=userName.getText();
				dispose();
			}
		});
		
		//�رնԻ���Ĳ���
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DLGINFO.setText("Ĭ���û���Ϊ:������һ");
			}
		});
		//ȡ����ť���¼�����
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				DLGINFO.setText("Ĭ���û���Ϊ:������һ");
				dispose();
			}
		});
	}

}
