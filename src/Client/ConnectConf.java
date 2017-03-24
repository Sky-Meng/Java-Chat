package Client;

import java.awt.*;
import javax.swing.border.*;

import Server.ChatServer;
import Server.PortConf;

import java.net.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * ����������Ϣ����ĶԻ��� ���û��������ӷ�������IP�Ͷ˿�
 */
public class ConnectConf extends JDialog {
	JPanel panelUserConf=new JPanel();
	JButton save=new JButton();
	JButton cancel=new JButton();
    JLabel DLGINFO=new JLabel("Ĭ����������Ϊ  127.0.0.1:8888");
    
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
		//��������λ�ã�ʹ�Ի������
		  Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		  this.setLocation((screenSize.width-400)/2+50,(screenSize.height-600)/2+150);
		  this.setResizable(false);
	}

	private void JbInit() throws Exception{
		// TODO Auto-generated method stub
		this.setSize(new Dimension(300, 130));
		this.setTitle("��������");
		message.setText("�������������IP��ַ:");
		inputIp=new JTextField(10);
		inputIp.setText(userInputIp);
		inputPort=new JTextField(4);
		inputPort.setText(""+userInputPort);
		save.setText("����");
		cancel.setText("ȡ��");
		
		panelUserConf.setLayout(new GridLayout(2, 2, 1, 1));
		panelUserConf.add(message);
		panelUserConf.add(inputIp);
		panelUserConf.add(new JLabel("������������Ķ˿ں�:"));
		panelUserConf.add(inputPort);
		
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
				// TODO �Զ����ɵķ������
				int savePort;
					//�ж�IP��ַ�Ƿ�Ϸ�
				try {
					
					//Ϊ��ʹ��ȡ�ĵ�ַ���String�����������""+,ע���м䲻Ҫ�пո�
					userInputIp=""+InetAddress.getByName(inputIp.getText());
					
					//ӦΪʹ���Զ�����ת������Զ����/���ţ�ʹ��substring(1)��ȡ
					userInputIp=userInputIp.substring(1);
					
				}catch(UnknownHostException e){
						DLGINFO.setText("�����IP��ַ��");	
						return;
				}
				
					//�ж϶˿ں��Ƿ�Ϸ�
				try {
						savePort = Integer.parseInt(inputPort.getText());
					if (savePort<1||savePort>65535) {
						DLGINFO.setText("�����˿ڱ�����0��65535֮���������");
						inputPort.setText("");
						return;
					}
						userInputPort = savePort;
					dispose();
				} catch (NumberFormatException e) {
					DLGINFO.setText("����Ķ˿ں�,�˿ںű���Ϊ����");
					inputPort.setText("");
					return;
				}
			
			}
		});
		
		//�رնԻ���Ĳ���
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DLGINFO.setText("Ĭ����������Ϊ  127.0.0.1:8888");
			}
		});
		//ȡ����ť���¼�����
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				DLGINFO.setText("Ĭ����������Ϊ  127.0.0.1:8888");
				dispose();
			}
		});
		}
}
