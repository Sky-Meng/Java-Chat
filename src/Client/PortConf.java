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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PortConf extends JDialog{
	JPanel panelPort=new JPanel();
	JButton save=new JButton();
	JButton cancel=new JButton();
	public static JLabel DLGINFO=new JLabel("Ĭ�϶˿ں�Ϊ:8888");
	JPanel panelSave=new JPanel();
	JLabel message=new JLabel();
	public static JTextField portNumber;
	
	public PortConf(JFrame frame) {
		// TODO �Զ����ɵķ������
		super(frame, true);
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

	private void jbInit() throws Exception {
		// TODO �Զ����ɵķ������
		this.setSize(new Dimension(300, 120));
		this.setTitle("�˿�����");
		message.setText("�����������Ķ˿ں�:");
		portNumber=new JTextField(10);
		portNumber.setText(""+ChatServer.Port);
		save.setText("����");
		cancel.setText("ȡ��");
		
		panelPort.setLayout(new FlowLayout());
		panelPort.add(message);
		panelPort.add(portNumber);
		
		panelSave.add(new Label("           "));//���ð�ť���Ҿ���
		panelSave.add(save);
		panelSave.add(cancel);
		panelSave.add(new Label("           "));
		
		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(panelPort, BorderLayout.NORTH);
		contentPane.add(DLGINFO, BorderLayout.CENTER);
		contentPane.add(panelSave, BorderLayout.SOUTH);
		
		//���水ť���¼�����
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				// TODO �Զ����ɵķ������
				int savePort;
				try {
					savePort=Integer.parseInt(PortConf.portNumber.getText());
					if (savePort<1||savePort>65535) {
						PortConf.DLGINFO.setText("�����˿ڱ�����0��65535֮���������");
						PortConf.portNumber.setText("");
						return;
					}
					ChatServer.Port=savePort;
					dispose();
				} catch (NumberFormatException e) {
					PortConf.DLGINFO.setText("����Ķ˿ں�,�˿ںű���Ϊ����");
					PortConf.portNumber.setText("");
					return;
				}
			
			}
		}
		);
		//�رնԻ���Ĳ���
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DLGINFO.setText("Ĭ�϶˿ں�Ϊ:8888");
			}
		});
		//ȡ����ť���¼�����
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DLGINFO.setText("Ĭ�϶˿ں�Ϊ:8888");
				dispose();
			}
		});
	}

}
