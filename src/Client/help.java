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
	JLabel title=new JLabel("�����ҷ���˰���");
	JTextArea help=new JTextArea();
	Color bg=new Color(255, 255, 255);
	
	public help( JFrame frame) {
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
		this.setLocation((screenSize.width-400)/2,(screenSize.height-320)/2);
		this.setResizable(false);//���öԻ����С���ɸı�

	}

	private void jbInit() throws Exception{
		// TODO �Զ����ɵķ������
		this.setSize(new Dimension(420, 200));
		this.setTitle("����");
		
		//���ñ�����ɫ����ʾ��һ��
		titlePanel.setBackground(bg);
		contentPanel.setBackground(bg);
		closePanel.setBackground(bg);
		
		help.setText("1�����÷���˵������˿�(Ĭ�϶˿�Ϊ:8888)��\n"+
					 "2���������������񡱰�ť�����ָ���Ķ˿���������\n"+
					 "3��ѡ����Ҫ������Ϣ���û�������Ϣ����д����Ϣ��֮�����Է�����Ϣ��\n"+
					 "4����Ϣ״̬���������Ƿ�������ǰ��������ֹͣ״̬��"+
					 "�û����͵���Ϣ��\n      �������˷��͵�ϵͳ��Ϣ.");
		help.setEditable(false);//���öԻ���Ϊ���ɱ༭
		
		titlePanel.add(new Label("          "));//���ñ������Ҿ���
		titlePanel.add(title);
		titlePanel.add(new Label("          "));
		
		contentPanel.add(help);
		contentPanel.add(new Label("          "));//���ð�ť���Ҿ���
		contentPanel.add(close);
		contentPanel.add(new Label("          "));
		
		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(titlePanel, BorderLayout.NORTH);
		contentPane.add(contentPanel, BorderLayout.CENTER);
		contentPane.add(closePanel, BorderLayout.SOUTH);
		
		close.setText("�ر�");
		//�¼�����
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				dispose();
			}
		});
	}

}
